#include "repo.h"
#include <fstream>
#include <sstream>
#include <string>

using std::stringstream;
using std::ifstream;
using std::getline;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

const vector<Device>& Repository::getAll() const
{
	return devices;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin,linie))
	{
		stringstream ss(linie);
		string tip;
		string model;
		string anS;
		string culoare;
		string pretS;
		getline(ss, tip, ',');
		getline(ss, model, ',');
		getline(ss, anS, ',');
		getline(ss, culoare, ',');
		getline(ss, pretS, ',');
		int an = stoi(anS);
		int pret = stoi(pretS);
		Device d(tip, model, an, culoare, pret);
		devices.push_back(d);
	}
	fin.close();
}

