#ifndef  REPO_H
#define  REPO_H

#include "film.h"
#include <vector>
#include <string>

using std::vector;
using std::string;

class Repository
{
private:
	vector<Film> filme;
	string filename;

public:
	Repository(const string& file) : filme(), filename(file) { load_from_file(); }
	~Repository() = default;
	int findFilm(const Film& f) const;
	void addFilm(const Film& f);
	void deleteFilm(const Film& f);
	void updateFilm(const Film& f, const Film& f_nou);
	void load_from_file();
	void save_to_file() const;
	const vector<Film>& getFilme() const;

	friend class CosInchiriere;
};

#endif