#include "repo.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::stringstream;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

void Repository::addJucator(const Jucator& jucator)
{
	jucatori.push_back(jucator);
}

const vector<Jucator>& Repository::getAll() const
{
	return jucatori;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin,linie))
	{
		stringstream ss(linie);
		string nume;
		string tara;
		string puncteS;
		string rankingS;
		getline(ss, nume, ',');
		getline(ss, tara, ',');
		getline(ss, puncteS, ',');
		getline(ss, rankingS, ',');
		int puncte = stoi(puncteS);
		int ranking = stoi(rankingS);
		Jucator j(nume, tara, puncte, ranking);
		addJucator(j);
	}
	fin.close();
}


