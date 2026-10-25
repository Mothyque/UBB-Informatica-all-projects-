#ifndef BOUNDED_QUEUE_HPP
#define BOUNDED_QUEUE_HPP

#include <queue>
#include <mutex>
#include <condition_variable>
#include <optional>

template <typename T>
class BoundedQueue {
private:
    std::queue<T> queue;
    const size_t capacity;
    bool finished = false;

    std::mutex mtx;
    std::condition_variable not_full;
    std::condition_variable not_empty;

public:
    explicit BoundedQueue(size_t capacity) : capacity(capacity) {}

    void push(const T& item) {
        std::unique_lock<std::mutex> lock(mtx);
        not_full.wait(lock, [this]() { return queue.size() < capacity;});
        queue.push(std::move(item));
        not_empty.notify_one();
    }

    void finish() {
        std::unique_lock<std::mutex> lock(mtx);
        finished = true;
        not_empty.notify_all();
    }

    std::optional<T> pop() {
        std::unique_lock<std::mutex> lock(mtx);
        not_empty.wait(lock, [this]() { return !queue.empty() || finished; });

        if (queue.empty() && finished) {
            return std::nullopt; 
        }

        T item = std::move(queue.front());
        queue.pop();
        not_full.notify_one();
        return item;
    }
};

#endif // BOUNDED_QUEUE_HPP