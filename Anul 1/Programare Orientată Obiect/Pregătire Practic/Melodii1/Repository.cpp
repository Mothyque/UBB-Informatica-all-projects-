#include "Repository.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::ofstream;
using std::getline;
using std::stoi;
using std::stringstream;

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
		string idS, titlu, artist, rankS;
		getline(ss, idS, ',');
		getline(ss, titlu, ',');
		getline(ss, artist, ',');
		getline(ss, rankS, ',');
		int id = stoi(idS);
		int rank = stoi(rankS);
		Melodie m(id, titlu, artist, rank);
		addMelodie(m);
	}
	fin.close();
}

void Repository::storeToFile()
{
	ofstream fout(filename);
	for (const auto& melodie : melodii)
	{
		fout << melodie.getId() << "," << melodie.getTitlu() << "," << melodie.getArtist() << "," << melodie.getRank() << "\n";
	}
}

void Repository::deleteMelodie(const Melodie& m)
{
	for (int i = 0; i < melodii.size(); i++)
	{
		if (melodii[i] == m)
		{
			melodii.erase(melodii.begin() + i);
			break;
		}
	}
	storeToFile();
}

void Repository::modifyMelodie(const Melodie& m1, const Melodie& m2)
{
	for (auto& melodie : melodii)
	{
		if (melodie == m1)
		{
			melodie.setRank(m2.getRank());
			break;
		}
	}
	storeToFile();
}

void Repository::addMelodie(const Melodie& m)
{
	melodii.push_back(m);
	storeToFile();
}

const vector<Melodie>& Repository::getAll() const
{
	return melodii;
}