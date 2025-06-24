#include "Repository.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::stringstream;
using std::getline;
using std::stoi;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string titlu, prets, platforma, ages;
		getline(ss, titlu, ',');
		getline(ss, prets, ',');
		getline(ss, platforma, ',');
		getline(ss, ages, ',');
		int pret = stoi(prets);
		int age = stoi(ages);
		Joc j(titlu, pret, platforma, age);
		jocuri.push_back(j);
	}
	fin.close();
}

const vector<Joc>& Repository::getAll() const
{
	return jocuri;
}


