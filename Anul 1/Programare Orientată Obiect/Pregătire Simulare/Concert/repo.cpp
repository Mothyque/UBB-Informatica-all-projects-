#include "repo.h"
#include <fstream>
#include <sstream>

using std::getline;
using std::ifstream;
using std::stringstream;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

const vector<ConcertD>& Repository::getAll() const
{
	return concerte;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		string nume;
		string data;
		string nrString;
		stringstream ss(linie);
		getline(ss, nume, ',');
		getline(ss, data, ',');
		getline(ss, nrString, ',');
		int nr = stoi(nrString);
		concerte.push_back(ConcertD(nume, data, nr));
	}
	fin.close();
}

void Repository::modificaConcert(const ConcertD& concert, int nrBileteNou)
{
	for (auto& concertCautat : concerte)
	{
		if (concertCautat == concert)
		{
			concertCautat.setNrBilete(nrBileteNou);
			break;
		}
	}
}


