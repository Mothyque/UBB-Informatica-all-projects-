#pragma once

#define NULL_TELEM -1
typedef int TElem;

struct Nod {
	TElem elem;
	int frecventa;
	Nod* urm;

	Nod(TElem e, int frecventa, Nod* urm) : elem(e), frecventa(frecventa), urm(urm) {}
};

class IteratorColectie;

class Colectie
{
	friend class IteratorColectie;

private:
	Nod* primul;
	/* aici e reprezentarea */
public:
	//constructorul implicit
	Colectie();

	//adauga un element in colectie
	void adauga(TElem e);

	//sterge o aparitie a unui element din colectie
	//returneaza adevarat daca s-a putut sterge
	bool sterge(TElem e);

	//verifica daca un element se afla in colectie
	bool cauta(TElem elem) const;

	//returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem elem) const;


	//intoarce numarul de elemente din colectie;
	int dim() const;

	//verifica daca colectia e vida;
	bool vida() const;

	//returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();

	// elimina toate aparitiile unui element din colectie
	int eliminaAparitii(int nr, TElem elem);

};

	