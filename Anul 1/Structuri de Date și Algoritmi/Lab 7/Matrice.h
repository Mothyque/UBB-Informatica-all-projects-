#pragma once

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
	int cap, dim;
	int* stanga;
	int* dreapta;
	int primLiber, radacina;

	void redimensionare();
	void actualizeazaPrimLiber();

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


	//destructor
	~Matrice() {};

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);

	void redimensioneaza(int numarNouLinii, int numarNouColoane);
};







