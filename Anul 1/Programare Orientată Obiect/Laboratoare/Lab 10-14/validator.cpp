#include "validator.h"
#include <vector>

#include "exceptii.h"

using std::find_if;
using std::vector;

void Validator::verificaFilm(const string& titlu, const string& regizor, int an, const string& actor) const
{
	/* Verifica daca un film este valid
	 * parameter titlu: titlul filmului
	 * parameter regizor: regizorul filmului
	 * parameter an: anul filmului
	 * parameter actor: actorul filmului
	 * throws: exceptie daca filmul nu este valid
	 */
	vector<string> erori;
	if (titlu.empty())
	{
		erori.emplace_back("Titlul nu poate fi gol!");
	}
	if (regizor.empty())
	{
		erori.emplace_back("Regizorul nu poate fi gol!");
	}
	if (an < 1888 || an > 2023)
	{
		erori.emplace_back("Anul nu este valid!");
	}
	if (actor.empty())
	{
		erori.emplace_back("Actorul nu poate fi gol!");
	}
	if (!erori.empty())
	{
		string mesaj;
		for (const auto& eroare : erori)
		{
			mesaj += eroare + ", ";
		}
		mesaj = mesaj.substr(0, mesaj.size() - 2);
		throw ValidareException(mesaj);
	}
}

void Validator::verificaDuplicat(Film film, const vector<Film>& lista) const
{
	/* Verifica daca un film exista deja in lista
	 * parameter film: filmul de verificat
	 * parameter lista: lista de filme
	 * throws: exceptie daca filmul exista deja in lista
	 */
	auto it = find_if(lista.begin(), lista.end(), [&film](const Film& f) {
		return f == film;
		});
	if (it != lista.end())
	{
		throw DuplicatException("Film duplicat!");
	}
}

void Validator::verificaExista(Film film, const vector<Film>& lista) const
{
	/* Verifica daca un film exista in lista
	 * parameter film: filmul de verificat
	 * parameter lista: lista de filme
	 * throws: exceptie daca filmul nu exista in lista
	 */
	auto it = find_if(lista.begin(), lista.end(), [&film](const Film& f) {
		return f == film;
		});
	if (it == lista.end())
	{
		throw NotFoundException("Film inexistent!");
	}
}