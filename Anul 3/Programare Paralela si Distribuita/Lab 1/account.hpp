#ifndef ACCOUNT_HPP
#define ACCOUNT_HPP

#include <mutex>

using std::mutex;

struct Account {
    int id;
    long long balance;
    mutable mutex mtx;

    Account(int id, long long balance) : id(id), balance(balance) {}

    Account(const Account&) = delete;
    Account& operator=(const Account&) = delete;
    Account(Account&&) = delete;
    Account& operator=(Account&&) = delete;
};

#endif // ACCOUNT_HPP