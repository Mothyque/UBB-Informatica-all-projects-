# Lab Report: Bank Accounts Concurrency Benchmark

## 1. Problem Overview
The objective is to manage concurrent money transfers between shared bank accounts using multi-threading and mutexes, verifying that the program enforces data invariants and remains free of deadlocks.

We evaluate two synchronization granularities:
* **Coarse-Grained Locking:** One global mutex protects the entire bank.
* **Fine-Grained Locking:** Each account has its own independent mutex.

---

## 2. Invariants & Mutex Rules

### System Invariants
1. **Total Money Conservation (Global Invariant):**
   $$\sum_{i=0}^{N-1} \text{balance}_i = \text{Initial Total Money}$$
   The sum of all balances must remain constant before, during, and after all concurrent operations.
2. **Atomic Transfers (Local Invariant):** Money cannot be created or destroyed mid-transfer. Balance deduction from Account $A$ and balance addition to Account $B$ occur as a single isolated unit.

### Mutex Protection Rules
* **Coarse-Grained:**
  * Protected by: `Bank::global_mtx`.
  * Rule: Any thread performing a transfer or consistency check must acquire this single lock.

* **Fine-Grained:**
  * Protected by: `Account::mtx` (one mutex per account).
  * **Deadlock Prevention Rule:** Every transfer involves two accounts ($A$ and $B$). A thread **must** acquire the lock with the smaller ID first:
    $$\text{lock}(\min(A, B)) \longrightarrow \text{lock}(\max(A, B))$$
    Enforcing this global lock hierarchy eliminates circular wait conditions, making deadlocks mathematically impossible.
  * **Consistency Check Rule:** The auditor thread acquires all account locks sequentially from $0$ to $N-1$ to capture a torn-read-free snapshot.

---

## 3. Experimental Setup & Hardware

* **Hardware Platform:** Ryzen 5 7520U 4-core / 8-thread CPU, 16 GB RAM
* **OS:** Windows
* **Compiler:** g++ 13.2.0 (MinGW)
* **Data Size:** 100 Accounts, Initial balance: $1,000 per account
* **Workload:** 200,000 transfer operations per thread

---

## 4. Benchmark Results

| Threads | Total Operations | Coarse-Grained (ms) | Fine-Grained (ms) | Speedup (Fine vs. Coarse) | Status |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **1** | 200,000 | **64** | 86 | **0.74x** | Passed |
| **2** | 400,000 | 168 | **101** | **1.66x** | Passed |
| **4** | 800,000 | 235 | **79** | **2.97x** | Passed |
| **8** | 1,600,000 | 656 | **131** | **5.00x** | Passed |

---

## 5. Performance Analysis

1. **Single-Thread Overhead (1 Thread):**
   * Coarse-grained is faster (64 ms vs. 86 ms).
   * With zero contention, the locking overhead of fine-grained mode (calculating `min`/`max` IDs and locking two separate mutexes instead of one) adds slight unnecessary work.

2. **Lock Contention Under Load (2–8 Threads):**
   * **Coarse-Grained degrades rapidly:** Going from 1 to 8 threads causes execution time to increase by more than 10x (64 ms $\to$ 656 ms). Because all threads serialize on a single lock, CPU cores spend most of their cycles on OS context switches and cache bouncing rather than productive work.
   * **Fine-Grained scales efficiently:** At 4 threads, runtime is only 79 ms despite doing 4x the total work of the 1-thread baseline. At 8 threads, it achieves a **5x speedup** over coarse-grained locking. Because the 100 accounts are selected randomly, collisions are rare ($\approx 2\%$), allowing threads to execute disjoint transfers truly in parallel across CPU cores.