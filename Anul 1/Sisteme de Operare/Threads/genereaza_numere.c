#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    FILE* f = fopen("numere.txt", "w");
    if (!f) {
        perror("Eroare la crearea fisierului");
        return 1;
    }

    srand(time(NULL)); // Seed pentru randomizare

    for (int i = 0; i < 100000; i++) {
        int nr = rand() % 99 + 1; // nr între 1 și 99
        fprintf(f, "%d\n", nr);
    }

    fclose(f);
    printf("Fisierul 'numere.txt' a fost generat cu succes.\n");

    return 0;
}

