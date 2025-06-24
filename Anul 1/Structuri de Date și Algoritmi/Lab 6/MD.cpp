#include "MD.h"
#include "IteratorMD.h"
#include <iostream>

using namespace std;


MD::MD() {
	/* Initializeaza MultiDictionarul
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Postconditii: Tabela MD-ului este initializata
	 */
	tabela.capacitate = 10;
	tabela.dim = 0;
	tabela.elems = new TElem[tabela.capacitate];
	tabela.urm = new int[tabela.capacitate];
	for (int i = 0; i < tabela.capacitate; i++) {
		tabela.urm[i] = -1;
		tabela.elems[i] = NULL_TELEMENT;
	}
	prim_liber = 0;
}

int MD::hash(TCheie cheie) const {
	/* Functie de hash
	 * Preconditii: cheie -  de tip TCheie
	 * Complexitate: θ(1) (theta)
	 * Parametrii: cheie - cheia pentru care se calculeaza hash-ul
	 * Returneaza: un index in intervalul [0, capacitate - 1]
	 */
	return abs(cheie) % tabela.capacitate;
}
void MD::redimensionare()
{
	/* Redimensioneaza MD-ul
	 * Preconditii: MD este initializat
	 * Complexitate: θ(n) (theta)
	 * Postconditii: MD-ul are capacitate dubla
	 */
	int capacitate_veche = tabela.capacitate;
	tabela.capacitate *= 2;
	TElem* elems_nou = new TElem[tabela.capacitate];
	int* urm_nou = new int[tabela.capacitate];
	vector<TElem> elems_vechi(tabela.elems, tabela.elems + capacitate_veche);
	delete[] tabela.elems;
	delete[] tabela.urm;
	tabela.elems = elems_nou;
	tabela.urm = urm_nou;
	tabela.dim = 0;
	prim_liber = 0;
	for (int i = 0; i < tabela.capacitate; i++)
	{
		urm_nou[i] = -1;
		elems_nou[i] = NULL_TELEMENT;
	}
	for (const auto& element: elems_vechi )
	{
		if (element != NULL_TELEMENT)
		{
			adauga(element.first, element.second);
		}
	}
}


void MD::adauga(TCheie c, TValoare v) {
	/* Adauga o pereche (cheie, valoare) in MD
	 * Preconditii: MD este initializat
	 * Complexitate: O(n)
	 * Parametrii: c - cheia, v - valoarea
	 * Postconditii: MD-ul contine perechele (c, v)
	 */
	if (prim_liber == tabela.capacitate)
	{
		redimensionare();
	}
	int index = hash(c);
	if (tabela.elems[index] == NULL_TELEMENT)
	{
		tabela.elems[index] = make_pair(c, v);
		if (index == prim_liber)
		{
			while (prim_liber < tabela.capacitate && tabela.elems[prim_liber] != NULL_TELEMENT)
			{
				prim_liber++;
			}
		}
	}
	else
	{
		while (tabela.urm[index] != -1)
		{
			index = tabela.urm[index];
		}
		tabela.urm[index] = prim_liber;
		tabela.elems[prim_liber] = make_pair(c, v);
		while (prim_liber < tabela.capacitate && tabela.elems[prim_liber] != NULL_TELEMENT)
		{
			prim_liber++;
		}
	}
	tabela.dim++;
}


bool MD::sterge(TCheie c, TValoare v) {
	/* Sterge o cheie si o valoare
	 * Preconditii: MD este initializat
	 * Complexitate: O(n)
	 * Parametrii: c - cheia, v - valoarea
	 * Postconditii: MD-ul nu contine perechele (c, v)
	 * Returneaza: true - daca s-a gasit cheia si valoarea de sters, false - altfel
	 */
	int i = hash(c); 
	int j = -1;      
	int k = 0;
	while (k < tabela.capacitate && j == -1) {
		if (tabela.urm[k] == i) {
			j = k;
		}
		else {
			k++;
		}
	}

	while (i != -1 && !(tabela.elems[i].first == c && tabela.elems[i].second == v)) {
		j = i;
		i = tabela.urm[i];
	}

	if (i == -1) {
		return false;
	}

	bool gata = false;
	do {
		int p = tabela.urm[i];  
		int pp = i;             

		while (p != -1 && hash(tabela.elems[p].first) != i) {
			pp = p;
			p = tabela.urm[p];
		}

		if (p == -1) {
			gata = true;
		}
		else {
			tabela.elems[i] = tabela.elems[p];
			j = pp;  
			i = p;  
		}
	} while (!gata);

	if (j != -1) {
		tabela.urm[j] = tabela.urm[i];
	}

	tabela.elems[i] = NULL_TELEMENT;
	tabela.urm[i] = -1;

	prim_liber = min(prim_liber, i);

	tabela.dim--;
	return true;
}



vector<TValoare> MD::cauta(TCheie c) const {
	/* Cauta o cheie si returneaza vectorul de valori asociate
	 * Preconditii: MD este initializat
	 * Complexitate: O(n)
	 * Parametrii: c - cheia cautata
	 * Returneaza: un vector cu valorile asociate cheii c
	 */
	vector <TValoare> elemente;
	int index = hash(c);
	while (index != -1) 
	{
		if (tabela.elems[index].first == c) 
		{
			elemente.push_back(tabela.elems[index].second);
		}
		index = tabela.urm[index];
	}
	return elemente;
}


int MD::dim() const {
	/* Returneaza numarul de perechi (cheie, valoare) din MD
	 * Preconditii: MD este initializat
	 * Complexitate: θ(1) (theta)
	 * Returneaza: numarul de perechi (cheie, valoare) din MD
	 */
	return tabela.dim;
}


bool MD::vid() const {
	/* Verifica daca MultiDictionarul e vid
	 * Preconditii: MD este initializat
	 * Complexitate: θ(1) (theta)
	 * Returneaza: true - daca MD-ul e vid, false - altfel
	 */
	return tabela.dim == 0;
}

int MD::diferentaValoareMaxMin() const {
	/* Returneaza diferenta dintre valoarea maxima si minima
	 * Preconditii: m:MD 
	 * Complexitate: caz favorabil = caz mediu = caz defavorabil = θ(n) (theta)
	 * Postconditii: -
	 * Returneaza: -1 - daca m e vid
					  - diferenta dintre valoarea maxima si minima
	 */

	 /* Subprogram diferentaValoareMaxMin(md)
	  * daca md.tabela.dim = 0
	  *		returneaza -1
	  *	sf_daca
	  *	it <- iterator()
	  *	val_min <- element(it).second
	  *	val_max <- element(it).second
	  *	cat timp valid(it)
	  *		daca element(it).second < val_min
	  *			val_min <- element(it).second
	  *		sf_daca
	  *		daca element(it).second > val_max
	  *			val_max <- element(it).second
	  *		sf_daca
	  *	sf_cat timp
	  *	returneaza val_max - val_min
	  */

	if (tabela.dim == 0)
	{
		return -1;
	}
	IteratorMD it = iterator();
	int val_min = it.element().second;
	int val_max = it.element().second;
	while (it.valid())
	{
		val_min = min(val_min, it.element().second);
		val_max = max(val_max, it.element().second);
		it.urmator();
	}
	return val_max - val_min;
}
IteratorMD MD::iterator() const {
	return IteratorMD(*this);
}


MD::~MD() {
	/* Destructorul MultiDictionarului
	 * Preconditii: MD este initializat
	 * Complexitate: θ(1) (theta)
	 * Postconditii: MD-ul este distrus
	 */
	delete[] tabela.elems;
	delete[] tabela.urm;
}

