#include "Repository.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::ofstream;
using std::getline;
using std::stringstream;
using std::stoi;
using std::stod;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

void Repository::add(const Produs& product)
{
	produse.push_back(product);
	saveToFile();
}

const vector<Produs>& Repository::getAll() const
{
	return produse;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string idS, nume, tip, pretS;
		getline(ss, idS, ',');
		getline(ss, nume, ',');
		getline(ss, tip, ',');
		getline(ss, pretS, ',');
		int id = stoi(idS);
		double pret = stod(pretS);
		Produs produs(id, nume, tip, pret);
		produse.push_back(produs);
	}
	fin.close();
}

void Repository::saveToFile() const
{
	ofstream fout(filename);
	for (const auto& produs : produse)
	{
		fout << produs.getId() << ","
			 << produs.getNume() << ","
			 << produs.getTip() << ","
			 << produs.getPret() << "\n";
	}
	fout.close();
}

void Repository::remove(const Produs& product)
{
	for(int i = 0; i < produse.size(); ++i)
	{
		if (produse[i].getId() == product.getId())
		{
			produse.erase(produse.begin() + i);
			saveToFile();
			return;
		}
	}
}