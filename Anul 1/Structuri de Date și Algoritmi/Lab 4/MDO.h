#pragma once

#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
	friend class IteratorMDO;
private:
	Relatie rel;

	TCheie* chei;
	int* urm_cheie;
	int* prec_cheie;
	int* head_valori;
	int cap_chei;
	int prim_cheie;
	int prim_liber_cheie;

	TValoare* valori;
	int* urm_valoare;
	int* prec_valoare;

	int cap_valori;
	int prim_liber_valoare;

	int aloca_cheie();
	void dealoca_cheie(int pozitie);
	int creeaza_nod_cheie(TCheie c);
	void redimensioneaza_chei();

	int aloca_valoare();
	void dealoca_valoare(int pozitie);
	void redimensioneaza_valori();
	

	
public:

	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO 
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid 
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	IteratorMDO iterator() const;

	vector<TCheie> multimeaCheilor() const;

	// destructorul 	
	~MDO();

};
