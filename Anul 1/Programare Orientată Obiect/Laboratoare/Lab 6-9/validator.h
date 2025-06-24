#ifndef VALIDATOR_H
#define VALIDATOR_H

#include <string>
#include <vector>
#include "film.h"

using std::string;
using std::vector;

class Validator
{
private:
public:
	void verificaFilm(const string& titlu, const string& regizor, int an, const string& actor) const;
	void verificaDuplicat(Film film, const vector<Film>& lista) const;
	void verificaExista(Film film, const vector<Film>& lista) const;
};

#endif