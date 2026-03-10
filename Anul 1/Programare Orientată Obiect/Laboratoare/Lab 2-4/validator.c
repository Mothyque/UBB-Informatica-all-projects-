#include "validator.h"

#include <string.h>

#include "oferta.h"
#include "repo.h"

int Valideaza_Tip(const char* tip)
{
	/* Valideaza tipul imobilului
	 * tip: tipul imobilului
	 * returns: 1 daca tipul este valid si nenul, 0 altfel
	 */
	const char* tipuri[4] = { "apartament", "casa", "teren" };
	int ok = 0;
	for (int i = 0; i < 3; i++)
	{
		if (strcmp(tip, tipuri[i]) == 0)
		{
			ok = 1;
			break;
		}
	}
	if (strlen(tip) == 0 || ok == 0)
	{
		return 0;
	}
	return 1;
}

int Valideaza_Suprafata(int suprafata)
{
	/* Valideaza suprafata imobilului
	 * suprafata: suprafata imobilului
	 * returns: 1 daca suprafata este pozitiva, 0 altfel
	 */
	if (suprafata <= 0)
	{
		return 0;
	}
	return 1;
}

int Valideaza_Adresa(const char* adresa)
{
	/* Valideaza adresa imobilului
	 * adresa: adresa imobilului
	 * returns: 1 daca adresa este nenula, 0 altfel
	 */
	if (strlen(adresa) == 0)
	{
		return 0;
	}
	return 1;
}

int Valideaza_Pret(int pret)
{
	/* Valideaza pretul imobilului
	 * pret: pretul imobilului
	 * returns: 1 daca pretul este pozitiv, 0 altfel
	 */
	if (pret <= 0)
	{
		return 0;
	}
	return 1;
}

int Valideaza_Exista_Oferta(const char* adresa, Repository* repo)
{
	/* Valideaza daca exista o oferta cu adresa data
	 * adresa: adresa imobilului
	 * repo: pointer la Repository
	 * returns: 1 daca exista oferta, 0 altfel	
	 */
	for (int i = 0; i < repo->lungime; i++)
	{
		if (strcmp(Get_Adresa(repo->oferte[i]), adresa) == 0)
		{
			return 1;
		}
	}
	return 0;
}
