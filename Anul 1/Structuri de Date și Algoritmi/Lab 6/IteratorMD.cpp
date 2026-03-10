#include "IteratorMD.h"

#include <iostream>
#include <ostream>

#include "MD.h"

using namespace std;

IteratorMD::IteratorMD(const MD& _md) : md(_md) {
	/* Constructorul primeste o referinta catre MD
	 * Preconditii: MD este initializat
	 * Complexitate: θ(1) (theta)
	 * Parametrii: _md - MD-ul pe care il itereaza
	 * Postconditii: iteratorul va referi primul element din MD
	 */
	prim();
}

TElem IteratorMD::element() const {
	/* returneaza valoarea elementului din container referit de iterator
	 * Preconditii: iteratorul este valid
	 * Complexitate: O(n)
	 * Throws exception() - daca iteratorul nu e valid
	 * Returneaza: elementul curent
	 */
	if (valid() == false) 
	{
		throw exception();
	}
	else
	{
		return md.tabela.elems[curent];
	}
}

bool IteratorMD::valid() const {
	/* Verifica daca iteratorul e valid (indica un element al containerului)
	 * Preconditii: iteratorul este valid
	 * Complexitate: θ(1) (theta)
	 * Returneaza: true - daca iteratorul e valid, false - altfel
	 */
	return curent < md.tabela.capacitate && md.tabela.elems[curent] != make_pair(-100000, -100000);
}

void IteratorMD::urmator() {
	/* Muta iteratorul in container
	 * Preconditii: iteratorul este valid
	 * Complexitate: O(n)
	 * Throws exception() - daca iteratorul nu e valid
	 * Postconditii: iteratorul va referi urmatorul element din MD
	 */
	if (valid() == false)
	{
		throw exception();
	}
	else
	{
		curent++;
		while (curent < md.tabela.capacitate && md.tabela.elems[curent] == make_pair(-100000, -100000))
		{
			curent++;
		}
	}

}

void IteratorMD::prim() {
	/* Reseteaza pozitia iteratorului la inceputul containerului
	 * Preconditii: iteratorul este valid
	 * Complexitate: θ(1) (theta)
	 * Postconditii: iteratorul va referi primul element din MD
	 */
	curent = 0;
	while (curent < md.tabela.capacitate && md.tabela.elems[curent] == make_pair(-100000, -100000))
	{
		curent++;
	}
}

