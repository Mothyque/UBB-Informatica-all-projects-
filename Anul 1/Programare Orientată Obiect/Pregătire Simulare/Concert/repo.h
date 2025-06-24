#pragma once
#include "concert1.h"
#include <vector>

using std::vector;

class Repository
{
private:
	vector<ConcertD> concerte;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<ConcertD>& getAll() const;
	void loadFromFile();
	void modificaConcert(const ConcertD& concert, int nrBileteNou);
};
