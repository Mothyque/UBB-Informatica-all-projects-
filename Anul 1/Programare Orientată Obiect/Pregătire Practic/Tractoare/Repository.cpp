#include "Repository.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::ofstream;
using std::stringstream;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

void Repository::add(const Tractor& tractor)
{
	tractoare.push_back(tractor);
	saveToFile();
}

void Repository::remove(const Tractor& tractor)
{
	for(int i = 0; i < static_cast<int>(tractoare.size()); ++i)
	{
		if (tractoare[i].getId() == tractor.getId())
		{
			tractoare.erase(tractoare.begin() + i);
			saveToFile();
			return;
		}
	}
}

vector<Tractor>& Repository::getAll()
{
	return tractoare;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string idS, nume, tip, numarRotiS;
		getline(ss, idS, ',');
		getline(ss, nume, ',');
		getline(ss, tip, ',');
		getline(ss, numarRotiS, ',');
		int id = stoi(idS);
		int numarRoti = stoi(numarRotiS);
		Tractor tractor(id, nume, tip, numarRoti);
		tractoare.push_back(tractor);
	}
	fin.close();
}

void Repository::saveToFile() const
{
	ofstream fout(filename);
	for (const auto& tractor : tractoare)
	{
		fout << tractor.getId() << ","
			 << tractor.getNume() << ","
			 << tractor.getTip() << ","
			 << tractor.getNrRoti() << "\n";
	}
	fout.close();
}