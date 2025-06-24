#pragma once
#include "domain.h"
#include <vector>

using std::vector;

class Repository 
{
private:
	vector<Procesor> procesoare;
	vector<PlacaDeBaza> placiDeBaza;
	string filenameProcesoare;
	string filenamePlaciDeBaza;

public:
	Repository(const string& filenameProcesoare, const string& filenamePlaciDeBaza);
	~Repository() = default;
	void loadFromFile();
	const vector<Procesor>& getProcesoare() const;
	const vector<PlacaDeBaza>& getPlaciDeBaza() const;
	void addPlacaDeBaza(const PlacaDeBaza& pb);
};
