#include <stdio.h>

void citire_sir(char s[]);

int nr_maxim(char s[]);

int main()
{
	char sir_sursa[100];
	citire_sir(sir_sursa);
	int numar_maxim = nr_maxim(sir_sursa);
	printf("Numarul maxim din sir este: %xH", numar_maxim);
	return 0;
}

void citire_sir(char s[])
{
	printf("Citeste sir: ");
	gets(s);
}

