# Lab Report: Producer-Consumer Vector Scalar Product

## 1. Problem Overview
The objective is to compute the scalar (dot) product of two vectors $A$ and $B$:
$$\text{Result} = \sum_{i=0}^{N-1} (a_i \times b_i)$$

The workload is split between two concurrent threads synchronized using a bounded queue:
* **Producer Thread:** Computes products of vector elements ($a_i \times b_i$) and pushes them to the queue.
* **Consumer Thread:** Pops products from the queue and accumulates them into a running sum.

---

## 2. Invariants & Synchronization Rules

* **Correctness Invariant:** The sum computed by the concurrent pipeline must strictly equal the sequential baseline:
  $$\text{Sum}_{\text{Producer-Consumer}} = \text{Sum}_{\text{Sequential}}$$
* **`std::mutex`:** Protects access to the shared queue and the completion flag.
* **`not_full`:** The producer waits on this condition variable when the queue reaches its maximum capacity.
* **`not_empty`:** The consumer waits on this condition variable when the queue is empty and work remains.
* **Termination Rule:** Once all elements are processed, the producer marks the queue as finished and broadcasts to wake the consumer. The consumer exits only when the queue is completely empty.

---

## 3. Benchmark Results

| Mode | Queue Capacity | Elapsed Time (ms) | Correctness |
|:---|:---:|:---:|:---:|
| **Sequential (Single-Threaded)** | N/A | **4 ms** | PASS |
| **Producer-Consumer** | 1 | **26,145 ms** | PASS |
| **Producer-Consumer** | 10 | **2,499 ms** | PASS |
| **Producer-Consumer** | 100 | **668 ms** | PASS |
| **Producer-Consumer** | 1,000 | **630 ms** | PASS |
| **Producer-Consumer** | 10,000 | **622 ms** | PASS |

---

## 4. Performance Analysis

1. **Queue Size 1 (Extreme Overhead):**
   * Runtime is **26,145 ms**, compared to **4 ms** for sequential computation.
   * With a capacity of 1, the producer and consumer are forced into strict lockstep. Every single element incurs two mutex locks/unlocks, two condition variable waits/signals, and continuous thread suspension/wakeups. Synchronization overhead completely dominates computation.

2. **Queue Sizes 10 & 100 (Contention Relief):**
   * Increasing the capacity to 10 reduces runtime by over $10\times$ (**2,499 ms**).
   * Increasing the capacity to 100 cuts runtime further to **668 ms**.
   * A larger buffer decouples the producer and consumer, allowing both threads to process batches without constantly stalling and waiting on empty/full conditions.

3. **Queue Sizes 1,000 & 10,000 (Saturation):**
   * Runtime stabilizes around **622–630 ms**.
   * Beyond a capacity of 100, buffer capacity is no longer the bottleneck. Performance is bounded by the intrinsic overhead of locking, queue allocations, and cross-thread memory transfers.