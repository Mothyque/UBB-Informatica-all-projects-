#include <iostream>

/*
MLP:

int cmmdc(int a, int b) {
    while (b != 0) {
        int rest = a % b;
        a = b;
        b = rest;
    }
    afiseaza a;
    return a;
}

Programul C++ de mai jos transpune algoritmul MLP de mai sus.
*/

int cmmdc(int a, int b) {
    while (b != 0) {
        int rest = a % b;
        a = b;
        b = rest;
    }
    return a;
}

int main() {
    int a;
    int b;
    std::cin >> a >> b;
    std::cout << "CMMDC: " << cmmdc(a, b) << '\n';
    return 0;
}