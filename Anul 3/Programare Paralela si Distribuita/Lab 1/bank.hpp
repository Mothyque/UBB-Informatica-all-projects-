#ifndef BANK_HPP
#define BANK_HPP

#include "account.hpp"
#include <vector>
#include <memory>
#include <mutex>
#include <algorithm>
#include <cassert>
#include <iostream>

using std::mutex;
using std::lock_guard;
using std::unique_lock;
using std::vector;
using std::unique_ptr;
using std::make_unique;
using std::min;
using std::max;

enum class LockGranularity {
    FINE_GRAINED,
    COARSE_GRAINED
};

class Bank {
private:
    vector<unique_ptr<Account>> accounts;
    long long initial_total_money;
    LockGranularity granularity;
    mutex global_mtx;

public:
    Bank(int num_accounts, long long initial_balance_per_account, LockGranularity gran)
            : granularity(gran) {
            accounts.reserve(num_accounts);
            for (int i = 0; i < num_accounts; ++i) {
                accounts.push_back(make_unique<Account>(i, initial_balance_per_account));
            }
            initial_total_money = static_cast<long long>(num_accounts) * initial_balance_per_account;
        }
    
    int size() const {
        return static_cast<int>(accounts.size());
    }

    bool transfer(int from_id, int to_id, long long amount) {
        if (from_id == to_id || amount <= 0) {
            return false;
        }

        if (granularity == LockGranularity::COARSE_GRAINED) {
            lock_guard<mutex> lock(global_mtx);
            if (accounts[from_id]->balance >= amount) {
                accounts[from_id]->balance -= amount;
                accounts[to_id]->balance += amount;
                return true;
            }
            return false;
        } 
        else {
            int first_id = min(from_id, to_id);
            int second_id = max(from_id, to_id);

            unique_lock<mutex> lock1(accounts[first_id]->mtx);
            unique_lock<mutex> lock2(accounts[second_id]->mtx);

            if (accounts[from_id]->balance >= amount) {
                accounts[from_id]->balance -= amount;
                accounts[to_id]->balance += amount;
                return true;
            }
            return false;
        }
    }

    bool check_consistency() {
        if (granularity == LockGranularity::COARSE_GRAINED) {
            lock_guard<mutex> lock(global_mtx);
            long long current_total = 0;
            for (const auto& account : accounts) {
                current_total += account->balance;
            }
            return current_total == initial_total_money;
        }
        else {
            vector<unique_lock<mutex>> locks;
            locks.reserve(accounts.size());
            for (const auto& account : accounts) {
                locks.emplace_back(account->mtx);
            }
            long long current_total = 0;
            for (const auto& account : accounts) {
                current_total += account->balance;
            }
            return current_total == initial_total_money;
        }
    }

    long long get_initial_total() const {
        return initial_total_money;
    }
};

#endif // BANK_HPP