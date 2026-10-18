#include "bank.hpp"
#include <iostream>
#include <thread>
#include <random>
#include <vector>  
#include <chrono>
#include <atomic>

using std::mt19937;
using std::uniform_int_distribution;
using std::atomic;
using std::thread;
using std::string;
using std::cerr;
using std::vector;
using std::ref;
using std::cout;

void worker_thread(Bank& bank, int operations_per_thread, int seed) {
    mt19937 rng(seed);
    uniform_int_distribution<int> account_dist(0, bank.size() - 1);
    uniform_int_distribution<long long> amount_dist(1, 100);

    for (int i = 0; i < operations_per_thread; ++i) {
        int from_id = account_dist(rng);
        int to_id = account_dist(rng);
        while (to_id == from_id) {
            to_id = account_dist(rng);
        }
        long long amount = amount_dist(rng);
        bank.transfer(from_id, to_id, amount);
    }
}

void periodic_checker(Bank& bank, atomic<bool>& done, int interval_ms) {
    while (!done.load()) {
        std::this_thread::sleep_for(std::chrono::milliseconds(interval_ms));
        if (done.load()) {
            break;
        }
        bool ok = bank.check_consistency();
        if (!ok) {
            cerr << "Inconsistency detected!" << "\n";
            exit(1);
        }
    }
}

void run_benchmark(int num_accounts, int num_threads, int ops_per_thread, LockGranularity granularity) {
    const string mode_str = (granularity == LockGranularity::COARSE_GRAINED) ? "Coarse-grained" : "Fine-grained";
    
    cout << "\n ================================ \n";
    cout << "Running mode: " << mode_str << "\n";
    cout << "Accounts: " << num_accounts << ", Threads: " << num_threads << ", Operations per thread: " << ops_per_thread << "\n";

    Bank bank(num_accounts, 1000, granularity);

    atomic<bool> done(false);
    thread checker_thread(periodic_checker, ref(bank), ref(done), 50);

    auto start_time = std::chrono::high_resolution_clock::now();

    vector<thread> threads;
    threads.reserve(num_threads);
    for (int i = 0; i < num_threads; ++i) {
        threads.emplace_back(worker_thread, ref(bank), ops_per_thread, i);
    }

    for (auto& t : threads) {
        t.join();
    }

    auto end_time = std::chrono::high_resolution_clock::now();
    done.store(true);
    checker_thread.join();

    bool final_check = bank.check_consistency();
    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - start_time).count();

    cout << "Final consistency check: " << (final_check ? "Passed" : "Failed") << "\n";
    cout << "Elapsed time: " << elapsed << " ms\n";
}

int main() {
    const int accounts = 100;
    const int operations_per_thread = 200000;

    vector<int> thread_counts = {1, 2, 4, 8};

    for (int num_threads : thread_counts) {
        run_benchmark(accounts, num_threads, operations_per_thread, LockGranularity::COARSE_GRAINED);
        run_benchmark(accounts, num_threads, operations_per_thread, LockGranularity::FINE_GRAINED);
    }

    return 0;
}