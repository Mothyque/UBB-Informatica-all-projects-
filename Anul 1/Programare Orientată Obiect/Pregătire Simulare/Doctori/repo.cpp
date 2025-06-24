#include "repo.h"
#include <fstream>
#include <sstream>
using std::ifstream;
using std::stringstream;
using std::getline;


Repository::Repository(const string& filename)
{
	this->filename = filename;
	load_from_file();
}

const vector<Doctor>& Repository::getDoctori() const
{
	return doctori;
}

void Repository::load_from_file()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		string cnp, prenume, sectie;
		string booleana;
		stringstream ss(linie);
		getline(ss, cnp, ',');
		getline(ss, prenume, ',');
		getline(ss, sectie, ',');
		getline(ss, booleana, ',');
		bool concediu = (booleana == "true");
		Doctor d(cnp, prenume, sectie, concediu);
		doctori.push_back(d);
	}
	fin.close();
}



