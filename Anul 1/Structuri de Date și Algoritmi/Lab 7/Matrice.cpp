#include "Matrice.h"

#include <algorithm>
#include <algorithm>
#include <exception>

using namespace std;

void Matrice::redimensionare()
{
	/* Redimensioneaza vectorul de elemente la dublul dimensiunii curente
	 * Preconditii: dimensiunea curenta a vectorului de elemente este egala cu capacitatea
	 * Complexitate: θ(n) (theta)
	 * Postconditii: dimensiunea curenta a vectorului de elemente s-a dublat
	 */
	int capNou = 2 * cap;
	Triplet* elementeNoi = new Triplet[capNou];
	int* stangaNoi = new int[capNou];
	int* dreaptaNoi = new int[capNou];
	for (int i = 0; i < cap; i++)
	{
		elementeNoi[i] = elemente[i];
		stangaNoi[i] = stanga[i];
		dreaptaNoi[i] = dreapta[i];
	}
	delete[] elemente;
	delete[] stanga;
	delete[] dreapta;

	elemente = elementeNoi;
	stanga = stangaNoi;
	dreapta = dreaptaNoi;
	cap = capNou;

	for (int i = dim; i < cap; i++)
	{
		elemente[i] = { -1, -1, NULL_TELEMENT };
		stanga[i] = -1;
		dreapta[i] = -1;
	}
	actualizeazaPrimLiber();
}

void Matrice::actualizeazaPrimLiber()
{
	/* Actualizeaza pozitia primului element liber
	 * Preconditii: matricea este initializata
	 * Complexitate: O(n) (theta)
	 * Postconditii: primLiber contine pozitia primului element liber
	 */
	for (int i = 0; i < cap; i++)
	{
		if (elemente[i].linie == -1 && elemente[i].coloana == -1 && elemente[i].valoare == NULL_TELEMENT && stanga[i] == -1 && dreapta[i] == -1)
		{
			primLiber = i;
			return;
		}
	}
}

Matrice::Matrice(int m, int n) {
	/* Constructor pentru o matrice cu m linii si n coloane
	 * Preconditii: m si n sunt numere naturale nenule
	 * Complexitate: θ(n) (theta)
	 * Postconditii: matricea este initializata cu dimensiunile date
	 * Throws: exceptie daca m sau n sunt 0
	 */
	if (m <= 0 || n <= 0)
	{
		throw exception("Matricea nu poate avea dimensiuni nule");
	}
	this->nrColoaneMatrice = n;
	this->nrLiniiMatrice = m;
	this->cap = 10;
	this->dim = 0;
	this->elemente = new Triplet[cap];
	this->stanga = new int[cap];
	this->dreapta = new int[cap];
	for (int i = 0; i < cap; i++)
	{
		elemente[i] = { -1, -1, NULL_TELEMENT };
		stanga[i] = -1;
		dreapta[i] = -1;
	}
	this->primLiber = 0;
	this->radacina = -1;
}



int Matrice::nrLinii() const {
	/* Returneaza numarul de linii al matricei
	 * Complexitate: θ(1) (theta)
	 * Returneaza: numarul de linii al matricei
	 */
	return nrLiniiMatrice;
}


int Matrice::nrColoane() const {
	/* Returneaza numarul de coloane al matricei
	 * Complexitate: θ(1) (theta)
	 * Returneaza: numarul de coloane al matricei
	 */
	return nrColoaneMatrice;
}


TElem Matrice::element(int i, int j) const {
	/* Cauta elementul de pe linia i si coloana j
	 * Preconditii: (i,j) este o pozitie valida in matrice
	 * Complexitate: O(n)
	 * Postconditii: elementul de pe linia i si coloana j a fost gasit
	 * Returneaza: valoarea elementului de pe linia i si coloana j sau -1 daca nu exista
	 * Throws: exceptie daca (i,j) nu este o pozitie valida in matrice
	 */
	if (i < 0 || j < 0 || i >= nrLiniiMatrice || j >= nrColoaneMatrice)
	{
		throw exception("Pozitia nu este valida");
	}
	int curent = radacina;
	while (curent != -1)
	{
		if (elemente[curent].linie == i && elemente[curent].coloana == j)
		{
			return elemente[curent].valoare;
		}
		else if (elemente[curent].linie < i || (elemente[curent].linie == i  && elemente[curent].coloana < j))
		{
			curent = dreapta[curent];
		}
		else
		{
			curent = stanga[curent];
		}
	}
	return NULL_TELEMENT;
}

TElem Matrice::modifica(int i, int j, TElem e) {
	/* Modifica elementul de pe linia i si coloana j
	 * Preconditii: (i,j) este o pozitie valida in matrice
	 * Complexitate: O(n) 
	 * Postconditii: elementul de pe linia i si coloana j a fost modificat
	 * Returneaza: vechea valoare a elementului de pe linia i si coloana j
	 * Throws: exceptie daca (i,j) nu este o pozitie valida in matrice
	 */
	if (i < 0 || j < 0 || i >= nrLiniiMatrice || j >= nrColoaneMatrice)
	{
		throw exception("Pozitia nu este valida");
	}
	int curent = radacina;
	int parinte = -1;
	bool eStanga = false;
	while (curent != -1)
	{
		if (elemente[curent].linie == i && elemente[curent].coloana == j)
		{
			TElem vechi = elemente[curent].valoare;
			if (e != NULL_TELEMENT)
			{
				elemente[curent].valoare = e;
				return vechi;
			}
			if (stanga[curent] == -1 && dreapta[curent] == -1)
			{
				if (parinte == -1)
				{
					radacina = -1;
				}
				else
				{
					if (eStanga)
					{
						stanga[parinte] = -1;
					}
					else
					{
						dreapta[parinte] = -1;
					}
				}
			}
			else if (stanga[curent] == -1 || dreapta[curent] == -1)
			{
				int fiu;
				if (stanga[curent] == -1)
				{
					fiu = dreapta[curent];
				}
				else
				{
					fiu = stanga[curent];
				}
				if (parinte == -1)
				{
					radacina = fiu;
				}
				else
				{
					if (eStanga)
					{
						stanga[parinte] = fiu;
					}
					else
					{
						dreapta[parinte] = fiu;
					}
				}
			}
			else
			{
				int succesor = dreapta[curent];
				int parinteSuccesor = curent;
				while (stanga[succesor] != -1)
				{
					parinteSuccesor = succesor;
					succesor = stanga[succesor];
				}
				elemente[curent] = elemente[succesor];
				if (parinteSuccesor == curent)
				{
					dreapta[curent] = dreapta[succesor];
				}
				else
				{
					stanga[parinteSuccesor] = dreapta[succesor];
				}
			}
			elemente[curent].linie = -1;
			elemente[curent].coloana = -1;
			elemente[curent].valoare = NULL_TELEMENT;
			stanga[curent] = -1;
			dreapta[curent] = -1;
			actualizeazaPrimLiber();
			dim--;
			return vechi;
		}
		parinte = curent;
		if (i < elemente[curent].linie || (i == elemente[curent].linie && j < elemente[curent].coloana))
		{
			eStanga = true;
			curent = stanga[curent];
		}
		else
		{
			eStanga = false;
			curent = dreapta[curent];
		}
	}
	if (e != NULL_TELEMENT)
	{
		if (dim == cap)
		{
			redimensionare();
		}
		dim++;	
		elemente[primLiber].linie = i;
		elemente[primLiber].coloana = j;
		elemente[primLiber].valoare = e;
		stanga[primLiber] = -1;
		dreapta[primLiber] = -1;

		if (radacina == -1)
		{
			radacina = primLiber;
			actualizeazaPrimLiber();
			return NULL_TELEMENT;
		}
		curent = radacina;
		parinte = -1;
		while (curent != -1)
		{
			parinte = curent;
			if (i < elemente[curent].linie || (i == elemente[curent].linie && j < elemente[curent].coloana))
			{
				curent = stanga[curent];
			}
			else
			{
				curent = dreapta[curent];
			}
		}
		if (i < elemente[parinte].linie || (i == elemente[parinte].linie && j < elemente[parinte].coloana))
		{
			stanga[parinte] = primLiber;
		}
		else
		{
			dreapta[parinte] = primLiber;
		}

		actualizeazaPrimLiber();
		return NULL_TELEMENT;
	}
	return NULL_TELEMENT;
}


void Matrice::redimensioneaza(int numarNouLinii, int numarNouColoane)
{
	/* Redimensioneaza matricea la numarul de linii si coloane date
	 * Preconditii: numarul de linii si coloane este un numar natural nenul
	 * Complexitate: Complexitate caz favorabil = Complexitate caz mediu = Complexitate caz defavorabil = O(n + m)
	 * Postconditii: matricea are dimensiunile date, elementele care depasesc noile dimensiuni sunt sterse
	 * Throws: exceptie daca numarul de linii sau coloane este negativ
	 */

	/* Subalgoritm redimensioneaza(m,numarNouLinii, numarNouColoane)
	 * daca numarNouLinii < 0 sau numarNouColoane < 0 atunci
	 * 		@arunca exceptie
	 * sf_daca
	 * daca numarNouLinii >= nrLiniiMatrice si numarNouColoane >= nrColoaneMatrice atunci
	 *		m.nrLiniiMatrice <- numarNouLinii
	 *		m.nrColoaneMatrice <- numarNouColoane
	 * sf_daca
	 * altfel
	 * pentru i <- numarNouLinii, nrLiniiMatrice - 1 executa
	 *		pentru j <- numarNouColoane, nrColoaneMatrice - 1 executa
	 *			daca element(m,i,j) != NULL_TELEMENT atunci
	 *				modifica(m,i, j, NULL_TELEMENT)
	 *			sf_daca
	 *		sf_pentru
	 *	sf_pentru
	 *	m.nrLiniiMatrice <- numarNouLinii
	 *	m.nrColoaneMatrice <- numarNouColoane 
	 */
	if (numarNouColoane < 0 || numarNouLinii < 0)
	{
		throw exception("Dimensiunile matricei nu pot fi negative");
	}
	if (numarNouLinii >= nrLiniiMatrice && numarNouColoane >= nrColoaneMatrice)
	{
		nrLiniiMatrice = numarNouLinii;
		nrColoaneMatrice = numarNouColoane;
		return;
	}
	else
	{
		for (int i = numarNouLinii; i < nrLiniiMatrice; i++)
		{
			for (int j = numarNouColoane; j < nrColoaneMatrice; j++)
			{
				if (element(i,j) != NULL_TELEMENT)
				{
					modifica(i, j, NULL_TELEMENT);
				}
			}
		}
		nrLiniiMatrice = numarNouLinii;
		nrColoaneMatrice = numarNouColoane;
	}
}
