#include <cassert>
#include <fstream>
#include <vector>
#include "film.h"
#include "repo.h"
#include "service.h"
#include "inchiriere.h"
#include "exceptii.h"

using std::vector;
using std::ifstream;
using std::string;

void testeDomain()
{
	/* Testeaza functiile din clasa Film */
	Film f1{ "Titanic", "James Cameron", 1997, "Leonardo DiCaprio" };
	assert(f1.getTitlu() == "Titanic");
	assert(f1.getRegizor() == "James Cameron");
	assert(f1.getAn() == 1997);
	assert(f1.getActor() == "Leonardo DiCaprio");

	Film f2{ "Inception", "Christopher Nolan", 2010, "Leonardo DiCaprio" };
	assert(f2.getTitlu() == "Inception");
	assert(f2.getRegizor() == "Christopher Nolan");
	assert(f2.getAn() == 2010);
	assert(f2.getActor() == "Leonardo DiCaprio");

	f2.setTitlu("Titanic");
	assert(f2.getTitlu() == "Titanic");
	f2.setRegizor("James Cameron");
	assert(f2.getRegizor() == "James Cameron");
	f2.setAn(1997);
	assert(f2.getAn() == 1997);
	f2.setActor("Leonardo DiCaprio");
	assert(f2.getActor() == "Leonardo DiCaprio");

	assert(f1 == f2);
}

void testeRepo()
{
	/* Testeaza functiile din clasa Repository */
	Repository repo("test.txt");
	assert(repo.getFilme().size() == 1);
	repo.deleteFilm(Film("Titanic", "James Cameron", 1997, "Leonardo DiCaprio"));
	assert(repo.getFilme().size() == 0);
	Film f1{ "Titanic", "James Cameron", 1997, "Leonardo DiCaprio" };
	Film f2{ "Inception", "Christopher Nolan", 2010, "Leonardo DiCaprio" };
	repo.addFilm(f1);
	assert(repo.getFilme().size() == 1);
	assert(repo.getFilme()[0] == f1);

	repo.addFilm(f2);
	assert(repo.getFilme().size() == 2);
	assert(repo.getFilme()[1] == f2);

	repo.deleteFilm(f1);
	assert(repo.getFilme().size() == 1);
	assert(repo.getFilme()[0] == f2);

	repo.updateFilm(f1, f2);
	assert(repo.getFilme()[0] == f2);

	remove("test.txt");
}

void testeService()
{
	/* Testeaza functiile din clasa Service */
	Repository repo("test.txt");
	Validator validator;
	Service service{ repo, validator };

	service.adaugaFilm("Titanic", "James Cameron", 1997, "Leonardo DiCaprio");
	assert(service.afiseazaFilme().size() == 1);
	assert(service.afiseazaFilme()[0].getTitlu() == "Titanic");
	assert(service.afiseazaFilme()[0].getRegizor() == "James Cameron");
	assert(service.afiseazaFilme()[0].getAn() == 1997);
	assert(service.afiseazaFilme()[0].getActor() == "Leonardo DiCaprio");

	try
	{
		service.adaugaFilm("", "James Cameron", 1997, "Leonardo DiCaprio");
		assert(false);
	}
	catch (const ValidareException&)
	{
		assert(true);
	}

	try
	{
		service.adaugaFilm("Titanic", "", 1997, "Leonardo DiCaprio");
		assert(false);
	}
	catch (const ValidareException&)
	{
		assert(true);
	}

	try
	{
		service.adaugaFilm("Titanic", "James Cameron", 1000, "Leonardo DiCaprio");
		assert(false);
	}
	catch (const ValidareException&)
	{
		assert(true);
	}

	try
	{
		service.adaugaFilm("Titanic", "James Cameron", 1997, "");
		assert(false);
	}
	catch (const ValidareException&)
	{
		assert(true);
	}

	try
	{
		service.adaugaFilm("Titanic", "James Cameron", 1997, "Leonardo DiCaprio");
		assert(false);
	}
	catch (const DuplicatException&)
	{
		assert(true);
	}

	try
	{
		service.stergeFilm("Skibidi", "skibidi");
		assert(false);
	}
	catch (const NotFoundException&)
	{
		assert(true);
	}

	service.adaugaFilm("Inception", "Christopher Nolan", 2010, "Leonardo DiCaprio");
	assert(service.afiseazaFilme().size() == 2);

	service.stergeFilm("Titanic", "James Cameron");
	assert(service.afiseazaFilme().size() == 1);

	service.modificaFilm("Inception", "Christopher Nolan", "Titanic", "James Cameron", 1997, "Leonardo DiCaprio");
	assert(service.afiseazaFilme()[0].getTitlu() == "Titanic");
	assert(service.afiseazaFilme()[0].getRegizor() == "James Cameron");
	assert(service.afiseazaFilme()[0].getAn() == 1997);
	assert(service.afiseazaFilme()[0].getActor() == "Leonardo DiCaprio");

	try
	{
		service.modificaFilm("Skibidi toilet", "Eu", "Skibidi2", "Toteu", 2023, "Toteu");
		assert(false);
	}
	catch (const NotFoundException&)
	{
		assert(true);
	}

	service.adaugaFilm("Avatar", "James Cameron", 1997, "Sam Worthington");
	assert(service.filtreazaFilme("Titlu", "Avatar").size() == 1);
	assert(service.filtreazaFilme("Titlu", "Avatar")[0].getTitlu() == "Avatar");
	assert(service.filtreazaFilme("An", "1997").size() == 2);
	assert(service.filtreazaFilme("An", "1997")[0].getAn() == 1997);
	assert(service.filtreazaFilme("Regizor", "James Cameron").size() == 2);
	assert(service.filtreazaFilme("Regizor", "James Cameron")[0].getRegizor() == "James Cameron");
	assert(service.filtreazaFilme("Actor", "Leonardo DiCaprio").size() == 1);
	assert(service.filtreazaFilme("Actor", "Leonardo DiCaprio")[0].getActor() == "Leonardo DiCaprio");

	service.adaugaFilm("Se7en", "David Fincher", 1995, "Morgan Freeman");
	vector<Film> lista_sortata = service.sorteazaFilme("Titlu", "Crescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getTitlu() == "Avatar");
	assert(lista_sortata[1].getTitlu() == "Se7en");
	assert(lista_sortata[2].getTitlu() == "Titanic");

	lista_sortata = service.sorteazaFilme("Titlu", "Descrescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getTitlu() == "Titanic");
	assert(lista_sortata[1].getTitlu() == "Se7en");
	assert(lista_sortata[2].getTitlu() == "Avatar");

	lista_sortata = service.sorteazaFilme("Regizor", "Crescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getRegizor() == "David Fincher");
	assert(lista_sortata[1].getRegizor() == "James Cameron");
	assert(lista_sortata[2].getRegizor() == "James Cameron");

	lista_sortata = service.sorteazaFilme("Regizor", "Descrescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getRegizor() == "James Cameron");
	assert(lista_sortata[1].getRegizor() == "James Cameron");
	assert(lista_sortata[2].getRegizor() == "David Fincher");

	lista_sortata = service.sorteazaFilme("An", "Crescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getAn() == 1995);
	assert(lista_sortata[1].getAn() == 1997);
	assert(lista_sortata[2].getAn() == 1997);

	lista_sortata = service.sorteazaFilme("An", "Descrescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getAn() == 1997);
	assert(lista_sortata[1].getAn() == 1997);
	assert(lista_sortata[2].getAn() == 1995);

	lista_sortata = service.sorteazaFilme("Actor", "Crescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getActor() == "Leonardo DiCaprio");
	assert(lista_sortata[1].getActor() == "Morgan Freeman");
	assert(lista_sortata[2].getActor() == "Sam Worthington");

	lista_sortata = service.sorteazaFilme("Actor", "Descrescator");
	assert(lista_sortata.size() == 3);
	assert(lista_sortata[0].getActor() == "Sam Worthington");
	assert(lista_sortata[1].getActor() == "Morgan Freeman");
	assert(lista_sortata[2].getActor() == "Leonardo DiCaprio");

	try
	{
		lista_sortata = service.sorteazaFilme("Titlu", "skibidi");
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	try
	{
		lista_sortata = service.sorteazaFilme("Regizor", "skibidi");
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	try
	{
		lista_sortata = service.sorteazaFilme("An", "skibidi");
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	try
	{
		lista_sortata = service.sorteazaFilme("Actor", "skibidi");
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	try
	{
		lista_sortata = service.sorteazaFilme("Skibidi", "Descrescator");
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	service.adaugaFilm("Amadeus", "Milos Forman", 1984, "F. Murray Abraham");
	service.adaugaFilm("Arrival", "Denis Villeneuve", 2016, "Amy Adams");

	vector<StatDTO> raport = service.raportFilme();
	assert(raport.size() == 3);
	assert(raport[0].get_litera() == 'A');
	assert(raport[1].get_litera() == 'S');
	assert(raport[2].get_litera() == 'T');

	assert(raport[0].get_an_minim() == 1984);
	assert(raport[1].get_an_minim() == 1995);
	assert(raport[2].get_an_minim() == 1997);

	assert(raport[0].get_an_maxim() == 2016);
	assert(raport[1].get_an_maxim() == 1995);
	assert(raport[2].get_an_maxim() == 1997);

	service.undo();
	assert(service.afiseazaFilme().size() == 4);
	service.stergeFilm("Avatar", "James Cameron");
	assert(service.afiseazaFilme().size() == 3);
	service.undo();
	assert(service.afiseazaFilme().size() == 4);
	service.modificaFilm("Avatar", "James Cameron", "Avatar 2", "James Cameron", 2022, "Sam Worthington");
	assert(service.afiseazaFilme()[3].getTitlu() == "Avatar 2");
	assert(service.afiseazaFilme()[3].getRegizor() == "James Cameron");
	assert(service.afiseazaFilme()[3].getAn() == 2022);
	assert(service.afiseazaFilme()[3].getActor() == "Sam Worthington");
	service.undo();
	assert(service.afiseazaFilme()[3].getTitlu() == "Avatar");
	assert(service.afiseazaFilme()[3].getRegizor() == "James Cameron");
	assert(service.afiseazaFilme()[3].getAn() == 1997);
	assert(service.afiseazaFilme()[3].getActor() == "Sam Worthington");

	service.empty_undo();
	try
	{
		service.undo();
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	map <char, int> grupare = service.grupareLitera();
	assert(grupare.size() == 3);
	assert(grupare['A'] == 2);
	assert(grupare['S'] == 1);

	remove("test.txt");
}
void testeCosInchirieri()
{
	/* Testeaza functiile din clasa CosInchiriere */
	Validator validator;
	Repository repo("test.txt");
	repo.addFilm(Film("Titanic", "James Cameron", 1997, "Leonardo DiCaprio"));
	repo.addFilm(Film("Inception", "Christopher Nolan", 2010, "Leonardo DiCaprio"));
	repo.addFilm(Film("Avatar", "James Cameron", 1997, "Sam Worthington"));
	repo.addFilm(Film("Se7en", "David Fincher", 1995, "Morgan Freeman"));
	repo.addFilm(Film("The Dark Knight", "Christopher Nolan", 2008, "Christian Bale"));
	CosInchiriere cos(validator, repo);
	assert(cos.nrFilmeRepo() == 5);
	cos.adaugaFilm("Titanic", "James Cameron");
	assert(cos.getFilme().size() == 1);
	cos.adaugaFilm("Inception", "Christopher Nolan");
	assert(cos.getFilme().size() == 2);
	cos.adaugaFilm("Avatar", "James Cameron");
	assert(cos.getFilme().size() == 3);

	try
	{
		cos.adaugaFilm("Skibidi", "Skibidi toilet");
		assert(false);
	}
	catch (const NotFoundException&)
	{
		assert(true);
	}
	cos.golesteCos();
	assert(cos.getNrFilme() == 0);

	cos.genereazaCos(3);
	assert(cos.getNrFilme() == 3);

	cos.golesteCos();

	try
	{
		cos.genereazaCos(100);
		assert(false);
	}
	catch (const InvalidCommandException&)
	{
		assert(true);
	}

	cos.adaugaFilm("Titanic", "James Cameron");
	cos.adaugaFilm("Inception", "Christopher Nolan");
	cos.adaugaFilm("Avatar", "James Cameron");

	cos.exporta("test_cos.csv");
	ifstream in("test_cos.csv");
	string line;
	getline(in, line);
	assert(line == "Titanic,James Cameron,1997,Leonardo DiCaprio");
	getline(in, line);
	assert(line == "Inception,Christopher Nolan,2010,Leonardo DiCaprio");
	getline(in, line);
	assert(line == "Avatar,James Cameron,1997,Sam Worthington");
	getline(in, line);
	in.close();
	remove("test_cos.csv");
	for (const auto& film : repo.getFilme())
	{
		repo.deleteFilm(film);
	}
	for (const auto& film : repo.getFilme())
	{
		repo.deleteFilm(film);
	}
	for (const auto& film : repo.getFilme())
	{
		repo.deleteFilm(film);
	}
	repo.addFilm(Film("Titanic", "James Cameron", 1997, "Leonardo DiCaprio"));
}

void testeExceptii()
{
	try
	{
		throw ValidareException("Validation error occurred");
	}
	catch (const ValidareException& ex)
	{
		assert(string(ex.what()) == "Validation error occurred");
	}

	try
	{
		throw DuplicatException("Duplicate entry found");
	}
	catch (const DuplicatException& ex)
	{
		assert(string(ex.what()) == "Duplicate entry found");
	}

	try
	{
		throw NotFoundException("Item not found");
	}
	catch (const NotFoundException& ex)
	{
		assert(string(ex.what()) == "Item not found");
	}

	try
	{
		throw InvalidCommandException("Invalid command issued");
	}
	catch (const InvalidCommandException& ex)
	{
		assert(string(ex.what()) == "Invalid command issued");
	}
}

void ruleazaTeste()
{
	/* Ruleaza toate testele */
	testeDomain();
	testeRepo();
	testeService();
	testeCosInchirieri();
	testeExceptii();
}