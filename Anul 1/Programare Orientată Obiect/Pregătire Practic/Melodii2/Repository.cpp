#include "Repository.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::ofstream;
using std::stringstream;
using std::getline;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

const vector<Melodie>& Repository::getAll() const
{
	return melodii;
}

void Repository::add(const Melodie& melodie)
{
	melodii.push_back(melodie);
	saveToFile();
}

void Repository::remove(const Melodie& melodie)
{
	for(int i = 0; i < melodii.size(); ++i)
	{
		if (melodii[i].getId() == melodie.getId())
		{
			melodii.erase(melodii.begin() + i);
			saveToFile();
			return;
		}
	}
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string idS, titlu, artist, gen;
		getline(ss, idS, ',');
		getline(ss, titlu, ',');
		getline(ss, artist, ',');
		getline(ss, gen, ',');
		int id = stoi(idS);
		Melodie melodie(id, titlu, artist, gen);
		melodii.push_back(melodie);
	}
	fin.close();
}

void Repository::saveToFile() const
{
	ofstream fout(filename);
	for (const Melodie& melodie : melodii)
	{
		fout << melodie.getId() << ","
			 << melodie.getTitlu() << ","
			 << melodie.getArtist() << ","
			 << melodie.getGen() << "\n";
	}
	fout.close();
}
