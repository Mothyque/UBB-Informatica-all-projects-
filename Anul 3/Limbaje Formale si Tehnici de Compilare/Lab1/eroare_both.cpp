#include <iostream>

/*
    PROGRAM CU DOUA ERORI IN C++ SI IN MLP

    Eroare comuna 1: declaratia float de mai jos nu se termina cu ';'.
    Eroare comuna 2: tipul boolean nu exista nici in C++, nici in MLP.
*/

int exemplu(int valoare) {
    float rezultat
    rezultat = valoare * 2.0f;

    boolean pozitiv = rezultat > 0;
    return rezultat;
}

int main() {
    std::cout << exemplu(5) << '\n';
    return 0;
}
