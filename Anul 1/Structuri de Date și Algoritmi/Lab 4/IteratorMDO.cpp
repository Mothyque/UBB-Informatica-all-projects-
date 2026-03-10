#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	/* Constructorul de initializare al iteratorului
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Parametrii: d - dictionarul pe care il itereaza
	 */
	prim();
}

void IteratorMDO::prim() {
	curent_cheie = dict.prim_cheie;
	if (curent_cheie != -1)
	{
		curent_valoare = dict.head_valori[curent_cheie];
	}
	else
	{
		curent_valoare = -1;
	}
}

void IteratorMDO::urmator() {
	/* Muta iteratorul la urmatorul element
	 * Preconditii: iteratorul e valid
	 * Complexitate: θ(1) (theta)
	 */
	if (!valid()) 
	{
		throw exception();
	}
	curent_valoare = dict.urm_valoare[curent_valoare];
	while (curent_cheie != -1 && curent_valoare == -1)
	{
		curent_cheie = dict.urm_cheie[curent_cheie];
		if (curent_cheie != -1)
		{
			curent_valoare = dict.head_valori[curent_cheie];
		}
		else
		{
			curent_valoare = -1;
		}
	}
}

bool IteratorMDO::valid() const {
	/* Verifica daca iteratorul e valid
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Returneaza: true daca iteratorul e valid, false altfel
	 */
	return curent_cheie != -1 && curent_valoare != -1;
}

TElem IteratorMDO::element() const {
	/* Accesam elementul curent
	 * Preconditii: iteratorul e valid
	 * Complexitate: θ(1) (theta)
	 * Returneaza: elementul curent
	 */
	if (!valid())
	{
		throw exception();
	}
	else
	{
		return make_pair(dict.chei[curent_cheie], dict.valori[curent_valoare]);
	}
}


