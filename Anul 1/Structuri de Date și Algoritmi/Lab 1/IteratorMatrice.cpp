#include "IteratorMatrice.h"

#include <exception>

using namespace std;



void IteratorMatrice::prim()
{
	/* Subalgoritm prim
	 *		it.curent <- 0
	 * SfSubalgoritm
	 */

	 /* Seteaza iteratorul pe prima pozitie a matricei
	 * preconditii: -
	 * Complexitate defavorabila = Complexitate medie = Complexitate favorabila: Θ(1) (theta)
	 */
	this->curent = 0;
}

bool IteratorMatrice::valid() const
{
	/* Subalgoritm valid
	 *		Returneaza valorea de adevar a relatiei (it.curent < dimensiunea matricei)
	 * SfSubalgoritm
	 */

	/* preconditii: it iterator
	 * Complexitate defavorabila = Complexitate medie = Complexitate favorabila: Θ(1) (theta)
	 * returneaza : true daca iteratorul este valid, false altfel
	 */
	return this->curent < this->matrice.dimensiune;
}

void IteratorMatrice::urmator()
{
	/* Subalgoritm urmator(it)
	 *		Daca valid(it) atunci
	 *			@arunca eceptie
	 *		SfDaca
	 *		it.curent <- 0;
	 *		CatTimp valid(it) si it.curent == NULL_TELEMENT executa
	 *			it.curent <- it.curent + 1
	 *		SfCatTimp
	 *	SfSubalgoritm
	 */

	/* preconditii: it - iterator valid
	 * Complexitate favorabila: Ω(1) (omega)
	 * Complexitate defavorabila: Θ(n) (theta)
	 * Complexitate medie: O(n) 
	 * exceptie: daca iteratorul nu este valid
	 */
	if (!this->valid())
	{
		throw exception("Iteratorul nu este valid");
	}
	this->curent++;
	while (this->valid() && this->matrice.elemente[this->curent].valoare == NULL_TELEMENT)
	{
		this->curent++;
	}
}

IteratorMatrice::IteratorMatrice(const Matrice& m) : matrice(m)
{
	/* Subalgoritm constructor
	 *		matrice <- m
	 *		prim()
	 * SfSubalgoritm
	 */

	/* preconditii: m este o referinta la o matrice
	 * Complexitate defavorabila = Complexitate medie = Complexitate favorabila: Θ(1) (theta)
	 */
	this->prim();
}

