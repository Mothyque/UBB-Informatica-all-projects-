#include "Repository.h"
#include <fstream>
#include <sstream>
using std::ifstream;
using std::stringstream;
using std::getline;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

const vector<Echipa>& Repository::getAll() const
{
	return echipe;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string ids, nume;
		getline(ss, ids, ',');
		getline(ss, nume, ',');
		int id = stoi(ids);
		Echipa e(id, nume);
		echipe.push_back(e);
	}
	fin.close();
}

