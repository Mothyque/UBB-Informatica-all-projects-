#ifndef MATRICE_H

#define MATRICE_H

#include "../Lab 1/IteratorMatrice.h"

typedef int TElem;


#define NULL_TELEMENT 0

struct Triplet
{
	int linie;
	int coloana;
	TElem valoare;
};

class Matrice {

private:
	int nrLiniiMatrice, nrColoaneMatrice;
	Triplet* elemente;
	int dimensiune;
	int capacitate;

	void redimensionare();

	friend class IteratorMatrice;

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLiniiMatrice, int nrColoaneMatrice);


	//destructor
	~Matrice(){};

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;

	// redimensionare matrice

	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);

	IteratorMatrice iterator() const;

};

#endif





