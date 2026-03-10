#include "Matrice.h"

#include <exception>

using namespace std;

Matrice::Matrice(int m, int n) {
	/* preconditii: m, n > 0
	 * Complexitate: Θ(1) (theta)
	 */
	if (m <= 0 || n<= 0)
	{
		throw exception("Matricea nu poate avea dimensiuni negative sau nule");
	}
	this->nrLiniiMatrice = m;
	this->nrColoaneMatrice = n;
	this->dimensiune = 0;
	this->capacitate = 10;

	this->elemente = new Triplet[this->capacitate];}
int Matrice::nrLinii() const{
	/* preconditii: -
	 * Complexitate: Θ(1) (theta)
	 * returns: numarul de linii ale matricei
	 */
	return this->nrLiniiMatrice;}

int Matrice::nrColoane() const{
	/* preconditii: -
	 * Complexitate: Θ(1) (theta)
	 * returns: numarul de coloane ale matricei
	 */
	return this->nrColoaneMatrice;}

TElem Matrice::element(int i, int j) const{
	/* preconditii: i, j >= 0
	 * Complexitate: O(log n) (logaritmica)
	 * returns: elementul de pe linia i si coloana j
	 * exceptie: daca i sau j nu sunt pozitii valide in matrice
	 */
	if (i < 0 || i >= this->nrLiniiMatrice || j < 0 || j >= this->nrColoaneMatrice)
	{
		throw exception("Pozitia nu este valida");
	}
	int st = 0, dr = this->dimensiune - 1;
	while (st <= dr)
	{
		int mid = (st + dr) / 2;
		if (this->elemente[mid].linie < i || (this->elemente[mid].linie == i && this->elemente[mid].coloana < j))
		{
			st = mid + 1;
		}
		else if (this->elemente[mid].linie > i || (this->elemente[mid].linie == i && this->elemente[mid].coloana > j))
		{
			dr = mid - 1;
		}
		else
		{
			return this->elemente[mid].valoare;
		}
	}	return NULL_TELEMENT;
}

void Matrice::redimensionare()
{
	/* preconditii: -
	 * Complexitate: O(n) (liniara)
	 * redimensioneaza matricea
	 */
	int nouaCapacitate = this->capacitate * 2;
	Triplet* elementeNoi = new Triplet[nouaCapacitate];
	for (int i = 0; i < this->dimensiune; i++)
	{
		elementeNoi[i] = this->elemente[i];
	}
	delete[] this->elemente;
	this->elemente = elementeNoi;
	this->capacitate = nouaCapacitate;
}

TElem Matrice::modifica(int i, int j, TElem e) {
	/* preconditii: i, j >= 0
	 * Complexitate: O(n) (liniara)
	 * returns: valoarea anterioara de pe linia i si coloana j
	 * exceptie: daca i sau j nu sunt pozitii valide in matrice
	 */
	if (i < 0 || i >= this->nrLiniiMatrice || j < 0 || j >= this->nrColoaneMatrice)
	{
		throw exception("Pozitia nu este valida");
	}
	int st = 0, dr = this->dimensiune - 1, pozitie = -1;
	while (st <= dr)
	{
		int mid = (st + dr) / 2;
		if (this->elemente[mid].linie < i || (this->elemente[mid].linie == i && elemente[mid].coloana < j))
		{
			st = mid + 1;
		}
		else
		{
			dr = mid - 1;
		}
	}
	pozitie = st;
	if (pozitie < this->dimensiune && this->elemente[pozitie].linie == i && this->elemente[pozitie].coloana == j)
	{
		TElem valoareVeche = this->elemente[pozitie].valoare;

		if (e == NULL_TELEMENT)
		{
			for (int k = pozitie; k < this->dimensiune - 1; k++)
			{
				this->elemente[k] = this->elemente[k + 1];
			}
			this->dimensiune--;
		}
		else
		{
			this->elemente[pozitie].valoare = e;
		}
		return valoareVeche;
	}
	else
	{
		if (e != NULL_TELEMENT)
		{
			if (this->dimensiune == this->capacitate)
			{
				this->redimensionare();
			}
			for (int k = this->dimensiune - 1; k >= pozitie; k--)
			{
				this->elemente[k + 1] = this->elemente[k];
			}
			this->elemente[pozitie].linie = i;
			this->elemente[pozitie].coloana = j;
			this->elemente[pozitie].valoare = e;
			this->dimensiune++;
		}
	}
	return NULL_TELEMENT;
}

IteratorMatrice Matrice::iterator() const {
	/* preconditii: -
	 * Complexitate: Θ(1) (theta)
	 * returns: un iterator pentru matrice
	 */
	return IteratorMatrice(*this);
}