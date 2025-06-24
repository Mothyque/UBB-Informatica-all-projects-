#include "ui.h"
#include <iostream>
#include <vector>

#include "exceptii.h"
#include "inchiriere.h"

using std::cout;
using std::cin;
using std::string;
using std::getline;
using std::vector;

void Console::runMeniu() const
{
	/* Ruleaza meniul aplicatiei */
	while (true)
	{
		cout << "Inchiriere filme" << "\n";
		cout << "A. Afiseaza filme" << "\n";
		cout << "U. Undo" << "\n";
		cout << "1. Adauga film" << "\n";
		cout << "2. Sterge film" << "\n";
		cout << "3. Modifica film" << "\n";
		cout << "4. Filtreaza filme" << "\n";
		cout << "5. Sorteaza filme" << "\n";
		cout << "6. Cos de inchiriere" << "\n";
		cout << "7. Raport filme" << "\n";
		cout << "E. Iesire" << "\n";
		cout << ">>>> ";
		string optiune;
		cin >> optiune;
		try
		{
			if (optiune == "A" || optiune == "a")
			{
				meniuA();
			}
			else if (optiune == "U" || optiune == "u")
			{
				meniuU();
				cout << "Undo efectuat!" << "\n";
				cout << "----------------" << "\n";
			}
			else if (optiune == "1")
			{
				meniu1();
			}
			else if (optiune == "2")
			{
				meniu2();
			}
			else if (optiune == "3")
			{
				meniu3();
			}
			else if (optiune == "4")
			{
				meniu4();
			}
			else if (optiune == "5")
			{
				meniu5();
			}
			else if (optiune == "6")
			{
				meniu6();
			}
			else if (optiune == "7")
			{
				meniu7();
			}
			else if (optiune == "E" || optiune == "e")
			{
				meniuE();
				break;
			}
			else
			{
				throw InvalidCommandException("Optiune invalida!");
			}
		}
		catch (const ValidareException& e)
		{
			cout << "Eroare: " << e.what() << "\n";
			cout << "----------------" << "\n";
		}
		catch (const DuplicatException& e)
		{
			cout << "Eroare: " << e.what() << "\n";
			cout << "----------------" << "\n";
		}
		catch (const NotFoundException& e)
		{
			cout << "Eroare: " << e.what() << "\n";
			cout << "----------------" << "\n";
		}
		catch (const InvalidCommandException& e)
		{
			cout << "Eroare: " <<  e.what() << "\n";
			cout << "----------------" << "\n";
		}
		catch (...)
		{
			cout << "Eroare necunoscuta!" << "\n";
			cout << "----------------" << "\n";
		}
	}
}

void Console::afisareFilme(const vector<Film>& filme) const
{
	/* Afiseaza filmele */
	for (const auto& film : filme)
	{
		cout << film.getTitlu() << " | " << film.getRegizor() << " | " << film.getAn() << " | " << film.getActor() << "\n";
	}
	cout << "----------------" << "\n";
}

void Console::meniuA() const
{
	/* Meniul de afisare a filmelor */
	vector<Film> filme = service.afiseazaFilme();
	if (filme.empty())
	{
		cout << "Nu exista filme!" << "\n";
	}
	else
	{
		afisareFilme(filme);
	}
}

void Console::meniuU() const
{
	/* Meniu pentru operatia de undo */
	service.undo();
}

void Console::meniu1() const
{
	/* Meniul de adaugare a unui film */
	string titlu, regizor, actor;
	int an;
	cout << "Titlu: ";
	cin.ignore();
	getline(cin, titlu);

	cout << "Regizor: ";
	getline(cin, regizor);

	cout << "An: ";
	cin >> an;

	cout << "Actor: ";
	cin.ignore();
	getline(cin, actor);

	service.adaugaFilm(titlu, regizor, an, actor);
	cout << "Film adaugat cu succes!" << "\n";
	cout << "----------------" << "\n";
}

void Console::meniu2() const
{
	/* Meniul de stergere a unui film */
	string titlu, regizor;
	cout << "Titlu: ";
	cin.ignore();
	getline(cin, titlu);

	cout << "Regizor: ";
	getline(cin, regizor);

	service.stergeFilm(titlu, regizor);
	cout << "Film sters cu succes!" << "\n";
	cout << "----------------" << "\n";
}


void Console::meniu3() const
{
	/* Meniul de modificare a unui film */
	string titlu, regizor, titlu_nou, regizor_nou, actor_nou;
	int an_nou;
	cout << "Titlul filmului cautat: ";
	cin.ignore();
	getline(cin, titlu);

	cout << "Regizorul filmului cautat: ";
	getline(cin, regizor);

	cout << "Noul titlu: ";
	//cin.ignore();
	getline(cin, titlu_nou);

	cout << "Noul regizor: ";
	getline(cin, regizor_nou);

	cout << "Noul an: ";
	cin >> an_nou;

	cout << "Noul actor: ";
	cin.ignore();
	getline(cin, actor_nou);

	service.modificaFilm(titlu, regizor, titlu_nou, regizor_nou, an_nou, actor_nou);
	cout << "Film modificat cu succes!" << "\n";
	cout << "----------------" << "\n";
}

void Console::meniu4() const
{
	/* Meniul de filtrare a unui film */
	string optiune_de_filtrat;
	cout << "Introduceti optiunea de filtrare (titlu, regizor, an, actor): ";
	cin.ignore();
	getline(cin, optiune_de_filtrat);
	if (optiune_de_filtrat != "titlu" && optiune_de_filtrat != "regizor" && optiune_de_filtrat != "an" && optiune_de_filtrat != "actor")
	{
		throw InvalidCommandException("Optiune de filtrare invalida!");
	}
	string valoare;
	cout << "Introduceti valoarea de filtrare: ";
	getline(cin, valoare);
	vector<Film> filme = service.filtreazaFilme(optiune_de_filtrat, valoare);
	if (filme.empty())
	{
		cout << "Nu exista filme care sa corespunda criteriilor de filtrare!" << "\n";
		return;
	}
	else
	{
		afisareFilme(filme);
	}
}

void Console::meniu5() const
{
	/* Meniul de sortare a filmelor */
	string optiune_de_sortare,ordine_de_sortare;
	cout << "Introduceti optiunea de sortare (Titlu, Regizor, An, Actor): ";
	cin.ignore();
	getline(cin, optiune_de_sortare);
	cout << "Introduceti ordinea de sortare (Crescator / Descrescator): ";
	getline(cin, ordine_de_sortare);
	cout << ordine_de_sortare << "\n";
	vector<Film> filme = service.sorteazaFilme(optiune_de_sortare, ordine_de_sortare);
	afisareFilme(filme);
}

void Console::meniu6() const
{
	/* Meniul de cos de inchiriere */
	string optiune;
	cout << "A. Afiseaza cos" << "\n";
	cout << "1. Adauga film in cos" << "\n";
	cout << "2. Goleste cos" << "\n";
	cout << "3. Genereaza cos aleator" << "\n";
	cout << "4. Exporta cos" << "\n";
	cout << "E. Iesire" << "\n";
	cout << ">>>> ";
	cin >> optiune;
	if (optiune == "1")
	{
		string titlu, regizor;
		cout << "Introduceti titlul filmului: ";
		cin.ignore();
		getline(cin, titlu);
		cout << "Introduceti regizorul filmului: ";
		getline(cin, regizor);
		cos.adaugaFilm(titlu, regizor);
		cout << "Film adaugat in cos!" << "\n";
	}
	else if (optiune == "2")
	{
		cos.golesteCos();
		cout << "Cos golit cu succes!" << "\n";
	}
	else if (optiune == "A" || optiune == "a")
	{
		for (const auto& film : cos.getFilme())
		{
			cout << film.getTitlu() << " | " << film.getRegizor() << " | " << film.getAn() << " | " << film.getActor() << "\n";
		}
	}
	else if (optiune == "3")
	{
		int nr;
		cout << "Introduceti numarul de filme: ";
		cin >> nr;
		cos.genereazaCos(nr);
		cout << "Cos generat cu succes!" << "\n";
	}
	else if (optiune == "4")
	{
		string numeFisier;
		cout << "Introduceti numele fisierului: ";
		cin.ignore();
		getline(cin, numeFisier);
		cos.exporta(numeFisier);
		cos.golesteCos();
		cout << "Cos exportat cu succes!" << "\n";
	}
	else if (optiune == "E" || optiune == "e")
	{
		return;
	}
	else
	{
		throw InvalidCommandException("Optiune invalida!");
	}
	cout << "----------------" << "\n";
}

void Console::meniu7() const
{
	/* Meniul de raportare a filmelor */
	vector<StatDTO> raport = service.raportFilme();
	for (const auto& stat : raport)
	{
		cout << stat;
	}
}


void Console::meniuE() const
{
	/* Meniul de iesire din aplicatie */
	cout << "Parasire aplicatie..." << "\n";
}