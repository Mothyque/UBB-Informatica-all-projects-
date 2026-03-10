#pragma once
#include "repo.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<ConcertD>& afiseazaConcerte() const;
	void modificaConcert(const string& nume, const string& data, int nrBileteNou) const;
	vector<ConcertD> sorteazaConcerte() const;

};
