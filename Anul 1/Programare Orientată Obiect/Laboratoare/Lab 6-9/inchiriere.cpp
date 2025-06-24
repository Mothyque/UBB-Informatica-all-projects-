#include "inchiriere.h"

#include <random>
#include <fstream>

#include "exceptii.h"
#include "repo.h"


void CosInchiriere::adaugaFilm(const string& titlu, const string& regizor)
{
	/* Adauga un film in cos
	 * parameter film: filmul de adaugat
	 * throws: exceptie daca filmul nu este valid sau nu exista
	 */
	Film film{ titlu, regizor, 0, "" };
	const vector<Film>& lista_repo = repo.getFilme();
	validator.verificaExista(film, lista_repo);
	validator.verificaDuplicat(film, filme);
	film = lista_repo[repo.findFilm(film)];
	filme.push_back(film);
}

void CosInchiriere::golesteCos()
{
	/* Goleste cosul de filme */
	filme.clear();
}

const vector<Film>& CosInchiriere::getFilme() const
{
	/* Returneaza lista de filme din cos
	 * returns: lista de filme din cos
	 */
	return filme;
}

int CosInchiriere::getNrFilme() const
{
	/* Returneaza numarul de filme din cos
	 * returns: numarul de filme din cos
	 */
	return static_cast<int>(filme.size());
}

void CosInchiriere::genereazaCos(int nr)
{
	/* Genereaza un cos de filme aleator
	 * parameter nr: numarul de filme din cos
	 * returns: un cost de filme aleator
	 */
	if (nr > static_cast<int>(repo.getFilme().size()) || (nr + getNrFilme()) > static_cast<int>(repo.getFilme().size()))
	{
		throw InvalidCommandException("Numarul de filme din cos este mai mare decat numarul de filme disponibile!");
	}
	std::mt19937 mt{ std::random_device{}() };
	std::uniform_int_distribution<int> dist(0, static_cast<int>(repo.getFilme().size()) - 1);
	int i = 0;
	while (i < nr)
	{
		int index = dist(mt);
		Film film = repo.getFilme()[index];
		try
		{
			adaugaFilm(film.getTitlu(), film.getRegizor());
			i++;
		}
		catch (const DuplicatException&)
		{
			continue;
		}
	}
}

void CosInchiriere::exporta(const string& numeFisier) const
{
	/* Exporta cosul de filme intr-un fisier
	 * parameter numeFisier: numele fisierului in care se va exporta cosul
	 */
	std::ofstream out(numeFisier);
	for (const auto& film : filme)
	{
		out << film.getTitlu() << "," << film.getRegizor() << "," << film.getAn() << "," << film.getActor() << "\n";
	}
	out.close();
}
