#include "repo.h"
#include <vector>
#include <sstream>
#include <fstream>

using std::vector;
using std::find_if;
using std::distance;
using std::ifstream;
using std::ofstream;
using std::getline;
using std::istringstream;
using std::stoi;


int Repository::findFilm(const Film& f) const
{
	/* Cauta un film in lista de filme
	 * parameter f: filmul de cautat
	 * returns: pozitia filmului in lista sau -1 daca filmul nu exista
	 */
	auto it = find_if(filme.begin(), filme.end(), [&f](const Film& film) {
		return film == f;
		});
	if (it != filme.end())
	{
		return static_cast<int>(distance(filme.begin(), it));
	}
	return -1;
}

void Repository::addFilm(const Film& f)
{
	/* Adauga un film in lista de filme
	 * parameter f: filmul de adaugat
	 */
	filme.push_back(f);
	save_to_file();
}

void Repository::deleteFilm(const Film& f)
{
	/* Sterge un film din lista de filme
	 * parameter f: filmul de sters
	 */
	int poz = findFilm(f);
	if (poz != -1)
		filme.erase(filme.begin() + poz);
	save_to_file();
}

void Repository::updateFilm(const Film& f, const Film& f_nou)
{
	/* Modifica un film din lista de filme
	 * parameter f: filmul de modificat
	 * parameter f_nou: filmul nou
	 */
	int poz = findFilm(f);
	if (poz != -1)
	{
		filme[poz].setTitlu(f_nou.getTitlu());
		filme[poz].setRegizor(f_nou.getRegizor());
		filme[poz].setAn(f_nou.getAn());
		filme[poz].setActor(f_nou.getActor());
	}
	save_to_file();
}

const vector<Film>& Repository::getFilme() const
{
	/* Returneaza lista de filme
	 * returns: lista de filme
	 */
	return filme;
}

void Repository::load_from_file()
{
	ifstream f(filename);
	string linie;
	while (getline(f, linie))
	{
		istringstream linestream(linie);
		string titlu, regizor, actor, an_str;
		int an;

		if (!getline(linestream, titlu, ',')) continue;
		if (!getline(linestream, regizor, ',')) continue;
		if (!getline(linestream, an_str, ',')) continue;
		if (!getline(linestream, actor)) continue;
		an = stoi(an_str);

		Film film{ titlu, regizor, an, actor };
		filme.push_back(film);
	}
	f.close();
}

void Repository::save_to_file() const
{
	ofstream f(filename);
	for (const auto& film : filme)
	{
		f << film.getTitlu() << "," << film.getRegizor() << "," << film.getAn() << "," << film.getActor() << "\n";
	}
	f.close();
}

