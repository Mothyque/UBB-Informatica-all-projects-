#include "Bounded_Queue.hpp"
#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
#include <random>
#include <numeric>

long long compute_sequential(const std::vector<int>& a, const std::vector<int>& b) {
    long long sum = 0;
    for (size_t i = 0; i < a.size(); ++i) {
        sum += static_cast<long long>(a[i]) * b[i];
    }
    return sum;
}

void producer_thread(const std::vector<int>& a, const std::vector<int>& b, BoundedQueue<long long>& queue) {
    for (size_t i = 0; i < a.size(); ++i) {
        long long product = static_cast<long long>(a[i]) * b[i];
        queue.push(product);
    }
    queue.finish();
}

void consumer_thread(BoundedQueue<long long>& queue, long long& result) {
    long long sum = 0;
    while (auto item = queue.pop()) {
        sum += *item;
    }
    result = sum;
}

long long compute_producer_consumer(const std::vector<int>& a, const std::vector<int>& b, size_t queue_capacity) {
    BoundedQueue<long long> queue(queue_capacity);
    long long result = 0;

    std::thread producer(producer_thread, std::cref(a), std::cref(b), std::ref(queue));
    std::thread consumer(consumer_thread, std::ref(queue), std::ref(result));

    producer.join();
    consumer.join();

    return result;
}

int main() {
    const size_t N = 10000000;
    std::vector<int> a(N), b(N);
    std::mt19937 rng(42);
    std::uniform_int_distribution<int> dist(1, 100);
    for (size_t i = 0; i < N; ++i) {
        a[i] = dist(rng);
        b[i] = dist(rng);
    }

    std::cout << "\n === Sequential Computation ===\n";
    auto seq_start = std::chrono::high_resolution_clock::now();
    long long sequential_result = compute_sequential(a, b);
    auto seq_end = std::chrono::high_resolution_clock::now();
    auto seq_time = std::chrono::duration_cast<std::chrono::milliseconds>(seq_end - seq_start);
    std::cout << "Result: " << sequential_result << "\n";
    std::cout << "Elapsed time: " << seq_time.count() << " ms\n";

    std::vector <size_t> queue_sizes = {1, 10, 100, 1000, 10000};
    for (size_t queue_size : queue_sizes) {
        std::cout << "\n === Producer-Consumer Computation with Queue Size " << queue_size << " ===\n";
        auto pc_start = std::chrono::high_resolution_clock::now();
        long long pc_result = compute_producer_consumer(a, b, queue_size);
        auto pc_end = std::chrono::high_resolution_clock::now();
        auto pc_time = std::chrono::duration_cast<std::chrono::milliseconds>(pc_end - pc_start);

        bool is_correct = (pc_result == sequential_result);
        std::cout << "Correctness: " << (is_correct ? "PASS" : "FAIL") << "\n";
        std::cout << "Result: " << pc_result << "\n";
        std::cout << "Elapsed time: " << pc_time.count() << " ms\n";
    }
    return 0;
}
