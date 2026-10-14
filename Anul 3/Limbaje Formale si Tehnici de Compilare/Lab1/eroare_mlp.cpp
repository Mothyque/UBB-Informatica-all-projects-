#include <iostream>

/*
    PROGRAM VALID IN C++, DAR INVALID IN MLP

    Functia foloseste doua constructii care nu sunt definite in MLP:

    1. instructiunea for;
    2. operatorul de incrementare ++.

    Echivalentul intentionat in MLP ar trebui scris cu while si atribuiri
    obisnuite, deoarece MLP nu contine for si ++.

    Forma care produce eroarea MLP este:
    index++;
*/

int sumaPanaLa(int limita) {
    int suma = 0;

    // Eroare MLP 1: MLP nu are instructiunea for.
    for (int index = 0; index <= limita; index++) {
        // Eroare MLP 2: operatorul ++ nu exista in MLP.
        suma = suma + index;
    }

    return suma;
}

int main() {
    int limita;
    std::cin >> limita;
    std::cout << sumaPanaLa(limita) << '\n';
    return 0;
}
