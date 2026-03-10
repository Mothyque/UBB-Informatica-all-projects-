#ifndef  REPO_H
#define  REPO_H

#include <random>

#include "film.h"
#include <vector>
#include <string>
#include <unordered_map>

using std::vector;
using std::string;
using std::unordered_map;

class Repo
{
public:
	virtual ~Repo() = default;
	virtual void addFilm(const Film& f) = 0;
	virtual void deleteFilm(const Film& f) = 0;
	virtual void updateFilm(const Film& f, const Film& f_nou) = 0;
	virtual int findFilm(const Film& f) const = 0;
	virtual vector<Film> getFilme() const = 0;
};

class Repository : public Repo
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
	vector<Film> getFilme() const;

	friend class CosInchiriere;
};

class RepoProbabilistic : public Repo
{
private:
	unordered_map<string, Film> filme;
	double probabilitate;
	void aruncaEroare() const;
public:
	RepoProbabilistic(double probabilitate) : probabilitate(probabilitate) {}
	~RepoProbabilistic() override = default;
	void addFilm(const Film& f) override;
	void deleteFilm(const Film& f) override;
	void updateFilm(const Film& f, const Film& f_nou) override;
	vector<Film> getFilme() const;
	int findFilm(const Film& f) const override;
};

#endif