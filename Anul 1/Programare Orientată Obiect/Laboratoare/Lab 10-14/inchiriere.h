#ifndef INCHIRIERE_H
#define INCHIRIERE_H
#include "film.h"
#include <vector>

#include "repo.h"
#include "validator.h"
#include "observable.h"

using std::vector;

class CosInchiriere : public Observable
{
private:
	vector<Film> filme;
	Validator& validator;
	Repository& repo;
public:
	CosInchiriere(Validator& validator, Repository& repo) : validator{ validator }, repo{ repo } {}
	~CosInchiriere() = default;
	void adaugaFilm(const string& titlu, const string& regizor);
	void golesteCos();
	const vector<Film>& getFilme() const;
	int getNrFilme() const;
	void genereazaCos(int nr);
	void exporta(const string& numeFisier) const;
	int nrFilmeRepo() const;
};
#endif
