#include <iostream>

/*
MLP:

int sumaNumere(int n) {
    int suma = 0;
    int index = 0;
    while (index < n) {
        int valoare;
        citeste valoare;
        suma = suma + valoare;
        index = index + 1;
    }
    afiseaza suma;
    return suma;
}

Programul C++ de mai jos transpune algoritmul MLP de mai sus.
*/

int sumaNumere(int n) {
    int suma = 0;
    int index = 0;

    while (index < n) {
        int valoare;
        std::cin >> valoare;
        suma = suma + valoare;
        index = index + 1;
    }

    return suma;
}

int main() {
    int n;
    std::cin >> n;
    std::cout << "Suma: " << sumaNumere(n) << '\n';
    return 0;
}