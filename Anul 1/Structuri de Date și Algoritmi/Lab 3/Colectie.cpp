#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;


Colectie::Colectie() {
	/* Initializeaza colectia vida
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 */

	primul = nullptr;
}


void Colectie::adauga(TElem elem) {
    /* Adauga un element in colectie
	 * Preconditii: elem TElem
     * Complexitate: O(n)
     * Parametrii: elem - elementul de adaugat
     */
    Nod* curent = primul;

    while (curent != nullptr) {
        if (curent->elem == elem) {
            curent->frecventa++;
            return;
        }
        curent = curent->urm;
    }

    Nod* nou = new Nod(elem, 1, primul);
    primul = nou;
}


bool Colectie::sterge(TElem elem) {  
   /* Sterge o aparitie a unui element din colectie  
    * Precoditii: elementul exista  
    * Complexitate: O(n)  
    * Parametrii: elem - elementul de sters  
    * Returns: true daca s-a putut sterge, false altfel  
    */  
   if (primul == nullptr) {  
       return false;  
   }  

   if (primul->elem == elem) 
   {  
       if (primul->frecventa > 1) 
	   {  
           primul->frecventa--;  
           return true;  
       }
   	   else 
	   {  
           Nod* deSters = primul;  
           primul = primul->urm;  
           delete deSters;  
           return true;  
       }  
   }  

   Nod* curent = primul;  
   while (curent->urm != nullptr && curent->urm->elem != elem) 
   {
       curent = curent->urm;  
   }  

   if (curent->urm == nullptr) 
   {  
       return false;  
   }  

   if (curent->urm->frecventa > 1) 
   {  
       curent->urm->frecventa--;  
   } 
   else 
   {  
       Nod* deSters = curent->urm;  
       curent->urm = curent->urm->urm;  
       delete deSters;  
   }  

   return true;  
}


bool Colectie::cauta(TElem elem) const {
	/* Verifica daca un element se afla in colectie
	 * Preconditii: elementul exista
	 * Complexitate: O(n)
	 * Parametrii: elem - elementul cautat
	 * Returns: true daca elementul se afla in colectie, false altfel
	 */
	Nod* curent = primul;
	if (curent == nullptr)
	{
		return false;
	}
	while (curent != nullptr)
	{
		if (curent->elem == elem)
		{
			return true;
		}
		curent = curent->urm;
	}
	return false;
}

int Colectie::nrAparitii(TElem elem) const {
	/* Returneaza numar de aparitii ale unui element in colectie
	 * Preconditii: elementul exista
	 * Complexitate: O(n)
	 * Parametrii: elem - elementul cautat
	 * Returns: numarul de aparitii ale elementului in colectie
	 */
	Nod* curent = primul;
	if (curent == nullptr)
	{
		return 0;
	}
	while (curent != nullptr)
	{
		if (curent->elem == elem)
		{
			return curent->frecventa;
		}
		curent = curent->urm;
	}
	return 0;
}


int Colectie::dim() const {
	/* Intoarce numarul de elemente din colectie
	 * Preconditii: -
	 * Complexitate: θ(n) (theta)
	 * Returns: numarul de elemente din colectie
	 */
	Nod* curent = primul;
	int count = 0;
	while (curent != nullptr)
	{
		count += curent->frecventa;
		curent = curent->urm;
	}
	return count;
}


bool Colectie::vida() const {
	/* Verifica daca colectia e vida
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Returns: true daca colectia e vida, false altfel
	 */
	if (primul == nullptr)
	{
		return true;
	}
	else
	{
		return false;
	}
}

IteratorColectie Colectie::iterator() const {
	/* Returneaza un iterator pe colectie
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Returns: un iterator pe colectie
	 */
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	/* Destructorul colectiei
	 * Preconditii: -
	 * Complexitate: θ(n) (theta)
	 */
	Nod* curent = primul;
	while (curent != nullptr)
	{
		Nod* deSters = curent;
		curent = curent->urm;
		delete deSters;
	}
}

int Colectie::eliminaAparitii(int nr, TElem elem) {
	/* Sterge un numar de aparitii ale unui element din colectie
	 * Preconditii: elem de tip TElem
	 * Complexitate caz favorabil: Ω(1) (omega) - nu avem niciun element
	 * Complexitate caz nefavorabil: θ(nr) (theta) - avem cel putin nr elemente
	 * Complexitate caz mediu: O(nr) - avem cel mult nr elemente
	 * Complexitate totala: O(nr)
	 * Parametrii: nr - numarul de aparitii de sters
	 *             elem - elementul de sters
	 * Returns: numarul de aparitii efectiv sterse
	 */

	 /* Subalgoritm eliminaAparitii(c, nr, elem)
	  *	daca nr <= 0
	  *		@arunca exceptie
	  *	sf_daca
	  *	count <- 0
	  *	daca nrAparitii(elem) = 0
	  *		@returneaza 0
	  *	sf_daca
	  *	cat timp count < nr si sterge(elem)
	  *		count <- count + 1
	  *	sf_catimp
	  *	@returneaza count
	  *	sf_Subalgoritm
	  */

	if (nr <= 0)
	{
		throw std::exception();
	}
	int count = 0;
	if (nrAparitii(elem) == 0)
	{
		return 0;
	}
	while (count < nr && sterge(elem))
	{
		count++;
	}
	return count;
}