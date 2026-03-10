#include "IteratorColectie.h"

#include <exception>

#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	/* Initializeaza iteratorul la inceputul containerului
	 * Preconditii: -
	 * Parametrii: c - referinta la containerul pe care il itereaza
	 * Complexitate: θ(1) (theta)
	 */
	this->prim();
}

void IteratorColectie::prim() {
	/* Reseteaza pozitia iteratorului la inceputul containerului
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 */
	nodCurent = col.primul;
	pozitieFrecventa = 0;
}


void IteratorColectie::urmator() {
	/* Trece la urmatorul element din container
	 * Preconditii: iteratorul este valid
	 * Complexitate: θ(1) (theta)
	 * Throws: exceptie daca iteratorul nu e valid
	 */
	if (!this->valid()) {
		throw std::exception();
	}
	pozitieFrecventa++;
	if (pozitieFrecventa >= nodCurent->frecventa)
	{
		nodCurent = nodCurent->urm;
		pozitieFrecventa = 0;
	}
}


bool IteratorColectie::valid() const {
	/* Verifica daca iteratorul este valid (indica un element al containerului)
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Returns: true daca iteratorul este valid, false altfel
	 */
	if (this->nodCurent != nullptr) {
		return true;
	}
	return false;
}



TElem IteratorColectie::element() const {
	/* Returneaza valoarea elementului din container referit de iterator
	 * Preconditii: iteratorul este valid
	 * Complexitate: θ(1) (theta)
	 * Returns: valoarea elementului din container referit de iterator
	 * Throws: exceptie daca iteratorul nu e valid
	 */
	if (!this->valid()) {
		throw std::exception();
	}
	return this->nodCurent->elem;
}

