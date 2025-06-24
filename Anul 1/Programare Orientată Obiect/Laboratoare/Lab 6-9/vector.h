#ifndef VECTOR_H
#define VECTOR_H

#include <algorithm>
#include <stdexcept>

using std::copy;

template <typename T>
class Vector
{
private:
    T* elements;
    int size;
    int capacity;

    void resize()
    {
        /* Redimensioneaza vectorul */
        int newCapacity = capacity * 2;
        T* newElements = new T[newCapacity];
        for (int i = 0; i < size; i++)
        {
            newElements[i] = elements[i];
        }
        delete[] elements;
        elements = newElements;
        capacity = newCapacity;
    }

public:
    Vector()
    {
        /* Initializeaza un vector gol */
        this->elements = new T[10];
        this->size = 0;
        this->capacity = 10;
    }

    ~Vector()
    {
        /* Distruge vectorul */
        delete[] elements;
    }

    Vector(const Vector& other)
    {
        /* Constructor de copiere */
        this->size = other.size;
        this->capacity = other.capacity;
        this->elements = new T[this->capacity];
        for (int i = 0; i < this->size; i++)
        {
            this->elements[i] = other.elements[i];
        }
    }


    Vector& operator=(const Vector& other)
    {
        /* Operator de atribuire */
        if (this != &other)
        {
            delete[] this->elements;

            this->size = other.size;
            this->capacity = other.capacity;
            this->elements = new T[this->capacity];

            for (int i = 0; i < this->size; i++)
            {
                this->elements[i] = other.elements[i];
            }
        }
        return *this;
    }

    void add(const T& element)
    {
        /* Adauga un element in vector */
        if (size == capacity)
        {
            resize();
        }
        elements[size] = element;
        size++;
    }

    void remove(int index)
    {
        /* Sterge un element din vector */
        if (index < 0 || index >= size)
        {
            throw std::out_of_range("Index out of bounds");
        }
        for (int i = index; i < size - 1; i++)
        {
            elements[i] = elements[i + 1];
        }
        size--;
    }

    T& get(int index) const
    {
        /* Returneaza un element din vector */
        if (index < 0 || index >= size)
        {
            throw std::out_of_range("Index out of bounds");
        }
        return elements[index];
    }

    int getSize() const
    {
        /* Returneaza dimensiunea vectorului */
        return size;
    }

    T& operator[](int index)
    {
        /* Returneaza un element din vector */
        if (index < 0 || index >= size)
        {
            throw std::out_of_range("Index out of bounds");
        }
        return elements[index];
    }

    const T& operator[](int index) const
    {
        /* Returneaza un element din vector */
        if (index < 0 || index >= size)
        {
            throw std::out_of_range("Index out of bounds");
        }
        return elements[index];
    }

    T* begin()
    {
        /* Returneza inceputul vectorului */
        return elements;
    }

    const T* begin() const
    {
        /* Returneaza inceputul vectorului */
        return elements;
    }

    T* end()
    {
        /* Returneaza finalul vectorului */
        return elements + size;
    }

    const T* end() const
    {
        /* Returneaza finalul vectorului */
        return elements + size;
    }

    bool empty() const
    {
        /* Verifica daca vectorul este gol */
        return size == 0;
    }
};

#endif
