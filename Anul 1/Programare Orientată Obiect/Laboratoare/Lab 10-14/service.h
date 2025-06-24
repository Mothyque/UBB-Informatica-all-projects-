#ifndef SERVICE_H  
#define SERVICE_H  

#include <iostream> 
#include <map>
#include <unordered_map>  
#include <vector>  
#include "repo.h"  
#include "validator.h"  

using std::vector;
using std::string;
using std::unordered_map;
using std::map;
using std::pair;
using std::unique_ptr;

class Actiune_Undo
{
public:
	virtual void do_Undo() = 0;
	virtual ~Actiune_Undo() = default;
};

class Undo_Adauga : public Actiune_Undo
{
private:
	Repository& repo;
	Film film_de_sters;
public:
	Undo_Adauga(Repository& r, const Film& film) : repo(r), film_de_sters(film) {}
	void do_Undo() override
	{
		repo.deleteFilm(film_de_sters);
	}
};

class Undo_Sterge : public Actiune_Undo
{
private:
	Repository& repo;
	Film film_de_adaugat;
public:
	Undo_Sterge(Repository& r, const Film& film) : repo(r), film_de_adaugat(film) {}
	void do_Undo() override
	{
		repo.addFilm(film_de_adaugat);
	}
};

class Undo_Modifica : public Actiune_Undo
{
private:
	Repository& repo;
	Film film_original;
	Film film_modificat;
public:
	Undo_Modifica(Repository& r, const Film& film1, const Film& film2) : repo(r), film_original(film1), film_modificat(film2) {}
	void do_Undo() override
	{
		repo.updateFilm(film_modificat, film_original);
	}
};




class StatDTO
{
private:
	char litera;
	int an_minim;
	int an_maxim;
public:
	StatDTO(char litera, int an_minim, int an_maxim) : litera(litera), an_minim(an_minim), an_maxim(an_maxim) {}
	char get_litera() const { return litera; }
	int get_an_minim() const { return an_minim; }
	int get_an_maxim() const { return an_maxim; }
	friend std::ostream& operator << (std::ostream& os, const StatDTO& stat)
	{
		os << "Litera: " << stat.litera << ", An minim: " << stat.an_minim << ", An maxim: " << stat.an_maxim << "\n";
		return os;
	}
};

class Service
{
private:
	Repository& repo;
	Validator& validator;
	vector<unique_ptr<Actiune_Undo>> actiuni_undo;
public:
	Service(Repository& repo, Validator& validator) : repo(repo), validator(validator) {}
	Service(const Service&) = delete;
	Service& operator=(const Service&) = delete;
	~Service() = default;
	vector<Film> afiseazaFilme() const;
	void adaugaFilm(const string& titlu, const string& regizor, int an, const string& actor);
	void stergeFilm(const string& titlu, const string& regizor);
	void modificaFilm(const string& titlu, const string& regizor, const string& titlu_nou, const string& regizor_nou, int an_nou, const string& actor_nou);
	vector<Film> filtreazaFilme(const string& optiune, const string& valoare) const;
	vector<Film> sorteazaFilme(const string& optiune, const string& ordine) const;
	vector<StatDTO> raportFilme() const;
	map<char, int> grupareLitera() const;
	void undo();
	void empty_undo();
};

#endif