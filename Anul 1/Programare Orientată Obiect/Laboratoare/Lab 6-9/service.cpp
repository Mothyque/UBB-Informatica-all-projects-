#include "service.h"
#include <algorithm>
#include <functional>
#include <iterator>
#include <vector>

#include "exceptii.h"

using std::vector;
using std::string;
using std::sort;
using std::copy_if;
using std::back_inserter;
using std::max;
using std::min;
using std::make_unique;
using std::find_if;

vector<Film> Service::afiseazaFilme() const
{
	/* Returneaza lista de filme
	 * returns: lista de filme
	 */
	return repo.getFilme();
}

void Service::adaugaFilm(const string& titlu, const string& regizor, const int an, const string& actor) 
{
	/* Adauga un film in lista de filme
	 * parameter titlu: titlul filmului
	 * parameter regizor: regizorul filmului
	 * parameter an: anul filmului
	 * parameter actor: actorul filmului
	 */
	validator.verificaFilm(titlu, regizor, an, actor);
	Film f{ titlu, regizor, an, actor };
	validator.verificaDuplicat(f, repo.getFilme());
	actiuni_undo.push_back(make_unique<Undo_Adauga>(repo, f));
	repo.addFilm(f);
}

void Service::stergeFilm(const string& titlu, const string& regizor) 
{
	/* Sterge un film din lista de filme
	 * parameter titlu: titlul filmului
	 * parameter regizor: regizorul filmului
	 */
	Film film_cautat{ titlu, regizor, 0, "" };
	validator.verificaExista(film_cautat, repo.getFilme());
	for (const auto& film: repo.getFilme())
	{
		if (film.getTitlu() == titlu && film.getRegizor() == regizor)
		{
			film_cautat.setAn(film.getAn());
			film_cautat.setActor(film.getActor());
			break;
		}
	}
	actiuni_undo.push_back(make_unique<Undo_Sterge>(repo, film_cautat));
	repo.deleteFilm(film_cautat);
}

void Service::modificaFilm(const string& titlu, const string& regizor, const string& titlu_nou, const string& regizor_nou, int an_nou, const string& actor_nou) 
{

	Film f1{ titlu, regizor, 0, "" };
	validator.verificaExista(f1, repo.getFilme());
	for (const auto& film : repo.getFilme())
	{
		if (film.getTitlu() == titlu && film.getRegizor() == regizor)
		{
			f1.setAn(film.getAn());
			f1.setActor(film.getActor());
			break;
		}
	}
	validator.verificaFilm(titlu_nou, regizor_nou, an_nou, actor_nou);
	Film f2 = Film{ titlu_nou, regizor_nou, an_nou, actor_nou };
	actiuni_undo.push_back(make_unique<Undo_Modifica>(repo, f1, f2));
	repo.updateFilm(f1, f2);
}

vector<Film> Service::filtreazaFilme(const string& optiune, const string& valoare) const
{
	/* Filtreaza filmele dupa o anumita optiune
	 * parameter optiune: optiunea de filtrare
	 * parameter valoare: valoarea de filtrare
	 * returns: lista de filme filtrate
	 */
	vector<Film> filme;
	const vector<Film>& repo_aux = repo.getFilme();
	if (optiune == "titlu")
	{
		copy_if(repo_aux.begin(), repo_aux.end(), back_inserter(filme), [&valoare](const Film& film) {return film.getTitlu() == valoare; });
	}
	else if (optiune == "regizor")
	{
		copy_if(repo_aux.begin(), repo_aux.end(), back_inserter(filme), [&valoare](const Film& film) {return film.getRegizor() == valoare; });
	}
	else if (optiune == "an")
	{
		copy_if(repo_aux.begin(), repo_aux.end(), back_inserter(filme), [&valoare](const Film& film) {return film.getAn() == stoi(valoare); });
	}
	else if (optiune == "actor")
	{
		copy_if(repo_aux.begin(), repo_aux.end(), back_inserter(filme), [&valoare](const Film& film) {return film.getActor() == valoare; });
	}
	return filme;
}

vector<Film> Service::sorteazaFilme(const string& optiune, const string& ordine) const
{
	/* Sorteaza filmele dupa o anumita optiune
	 * parameter optiune: optiunea de sortare
	 * returns: lista de filme sortate
	 */
	vector<Film> filme = repo.getFilme();
	if (optiune == "Titlu")
	{
		if (ordine == "Crescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getTitlu() < f2.getTitlu(); });
		}
		else if (ordine == "Descrescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getTitlu() > f2.getTitlu(); });
		}
		else
		{
			throw InvalidCommandException("Ordine de sortare incorecta");
		}
	}
	else if (optiune == "Regizor")
	{
		if (ordine == "Crescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getRegizor() < f2.getRegizor(); });
		}
		else if (ordine == "Descrescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getRegizor() > f2.getRegizor(); });
		}
		else
		{
			throw InvalidCommandException("Ordine de sortare incorecta");
		}
	}
	else if (optiune == "An")
	{
		if (ordine == "Crescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getAn() < f2.getAn(); });
		}
		else if (ordine == "Descrescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getAn() > f2.getAn(); });
		}
		else
		{
			throw InvalidCommandException("Ordine de sortare incorecta");
		}
	}
	else if (optiune == "Actor")
	{
		if (ordine == "Crescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getActor() < f2.getActor(); });
		}
		else if (ordine == "Descrescator")
		{
			sort(filme.begin(), filme.end(), [](const Film& f1, const Film& f2) {return f1.getActor() > f2.getActor(); });
		}
		else
		{
			throw InvalidCommandException("Ordine de sortare incorecta");
		}
	}
	else
	{
		throw InvalidCommandException("Optiune de sortare incorecta");
	}
	return filme;
}
vector<StatDTO> Service::raportFilme() const
{
	/* Genereaza un raport al filmelor
	 * returns: lista de rapoarte  
	 */

	unordered_map<char, pair<int, int>> raportMap;
	const vector<Film>& filme = repo.getFilme();
	for (const auto& film: filme)
	{
		char litera = film.getTitlu()[0];
		int an = film.getAn();
		if (raportMap.find(litera) == raportMap.end())
		{
			raportMap[litera] = { an, an };
		}
		else
		{
			raportMap[litera].first = min(raportMap[litera].first, an);
			raportMap[litera].second = max(raportMap[litera].second, an);
		}
	}
	vector<StatDTO> raporturi_finale;
	raporturi_finale.reserve(raportMap.size());
	for (const auto&  pair:raportMap)
	{
		raporturi_finale.emplace_back(StatDTO(pair.first, pair.second.first, pair.second.second));
	}
	sort(raporturi_finale.begin(), raporturi_finale.end(), [](const StatDTO& primul, const StatDTO& al_doilea) {return primul.get_litera() < al_doilea.get_litera(); });
	return raporturi_finale;
}

void Service::undo()
{
	if (actiuni_undo.empty())
	{
		throw InvalidCommandException("Nu exista operatii de undo");
	}
	actiuni_undo.back()->do_Undo();
	actiuni_undo.pop_back();
}

void Service::empty_undo()
{
	actiuni_undo.clear();
}