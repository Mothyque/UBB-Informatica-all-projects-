#ifndef ITERATORMATRICE_H
#define ITERATORMATRICE_H

#include "../TAD Matrice/Matrice.h"

class Matrice;

class IteratorMatrice
{
private:
	const Matrice& matrice;
	int curent;

public:
	IteratorMatrice(const Matrice& m);

	void prim();
	bool valid() const;
	void urmator();
};

#endif // !ITERATORMATRICE_H