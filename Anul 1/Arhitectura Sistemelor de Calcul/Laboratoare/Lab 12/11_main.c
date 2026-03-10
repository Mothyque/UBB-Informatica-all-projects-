#include <stdio.h>


void citeste_numar(char sir[]);

int transforma_in_numere(char sir_sursa[], int sir_destinatie[]);

int main()
{
	char sir1[101];
	int sir2[101];
	citeste_numar(sir1);
	int numar_de_numere = transforma_in_numere(sir1, sir2);
	for (int i = 0; i < numar_de_numere + 1; i++)
	{
		printf("%xH ", sir2[i]);
	}

}

void citeste_numar(char sir[])
{
	printf("Introduceti numere in baza 2: ");
	gets(sir);
}

