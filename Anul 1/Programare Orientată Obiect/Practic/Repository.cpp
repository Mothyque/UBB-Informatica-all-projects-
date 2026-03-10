#include "Repository.h"
#include <fstream>
#include <sstream>

using std::stringstream;
using std::ifstream;
using std::ofstream;
using std::getline;

Repository::Repository(const string& filename)
{
	// Constructorul clasei Repository
	// parametrii: filename - numele fisierului din care incarcam datele
	this->filename = filename;
	loadFromFile();
}

vector<Student>& Repository::getAll() 
{
	// Returenaza studenti
	// returns: vectorul cu studenti
	return studenti;
}

void Repository::add(const Student& student)
{
	// Adauga un student 
	// parametrii : student - studentul pe care dorim sa il adaugam
	studenti.push_back(student);
	saveToFile();
}

void Repository::remove(const Student& student)
{
	// Sterge un student
	// parametrii: student - studentul pe care dorim sa il stergem
	for (int i = 0; i < studenti.size(); i++)
	{
		if (studenti[i].getNrMatricol() == student.getNrMatricol())
		{
			studenti.erase(studenti.begin()  + i);
			saveToFile();
			return;
		}
	}
}

void Repository::loadFromFile()
{
	// Incarca datele din fisier
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string nrMatricolS, nume, varstaS, facultate;
		getline(ss, nrMatricolS, ',');
		getline(ss, nume, ',');
		getline(ss, varstaS, ',');
		getline(ss, facultate, ',');
		int nrMatricol = stoi(nrMatricolS);
		int varsta = stoi(varstaS);
		studenti.push_back(Student(nrMatricol, nume, varsta, facultate));
	}
	fin.close();
}

void Repository::saveToFile() const
{
	// Salveaza datele in fisier
	ofstream fout(filename);
	for (const auto& student : studenti)
	{
		fout << student.getNrMatricol() << "," << student.getNume() << "," << student.getVarsta() << "," << student.getFacultate() << "\n";
	}
	fout.close();
}