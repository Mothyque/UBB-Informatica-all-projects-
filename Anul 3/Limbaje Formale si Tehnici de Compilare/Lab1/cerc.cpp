#include <iostream>

/*
MLP:

float cerc(float raza) {
    float pi = 3.14;
    float perimetru = 2 * pi * raza;
    float arie = pi * raza * raza;
    afiseaza perimetru;
    afiseaza arie;
    return arie;
}

Programul C++ de mai jos transpune algoritmul MLP de mai sus.
*/

void cerc(float raza) {
    float pi = 3.14f;
    float perimetru = 2 * pi * raza;
    float arie = pi * raza * raza;

    std::cout << "Perimetru: " << perimetru << '\n';
    std::cout << "Arie: " << arie << '\n';
}

int main() {
    float raza;
    std::cin >> raza;
    cerc(raza);
    return 0;
}