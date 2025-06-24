#include "repo.h"

#include <filesystem>
#include <string>
#include <fstream>
#include <sstream>

using std::stringstream;
using std::getline;
using std::ifstream;

Repository::Repository(const string& filenameProcesoare, const string& filenamePlaciDeBaza)
{
	this->filenameProcesoare = filenameProcesoare;
	this->filenamePlaciDeBaza = filenamePlaciDeBaza;
	loadFromFile();
}

void Repository::addPlacaDeBaza(const PlacaDeBaza& pb)
{
	placiDeBaza.push_back(pb);
}

const vector<PlacaDeBaza>& Repository::getPlaciDeBaza() const
{
	return placiDeBaza;
}

const vector<Procesor>& Repository::getProcesoare() const
{
	return procesoare;
}

void Repository::loadFromFile()
{
	ifstream fin(filenameProcesoare);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string nume;
		string soclu;
		string nrThreaduriS;
		string pretS;
		getline(ss, nume, ',');
		getline(ss, nrThreaduriS, ',');
		getline(ss, soclu, ',');
		getline(ss, pretS, ',');
		int nrThreaduri = stoi(nrThreaduriS);
		int pret = stoi(pretS);
		Procesor p(nume, nrThreaduri, soclu , pret);
		procesoare.push_back(p);
	}
	fin.close();
	ifstream fin2(filenamePlaciDeBaza);
	while (getline(fin2, linie))
	{
		stringstream ss(linie);
		string nume;
		string soclu;
		string pretS;
		getline(ss, nume, ',');
		getline(ss, soclu, ',');
		getline(ss, pretS, ',');
		int pret = stoi(pretS);
		PlacaDeBaza pb(nume, soclu, pret);
		addPlacaDeBaza(pb);
	}

}