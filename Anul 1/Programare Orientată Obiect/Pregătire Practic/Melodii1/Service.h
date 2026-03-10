#pragma once
#include "Repository.h"

class Service : public Observable
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Melodie>& afiseazaMelodii() const;
	void adaugaMelodie(int id, const string& titlu, const string& artist, int rank) const;
	void stergeMelodie(const string& titlu, const string& artist) const;
	void modificaMelodie(const string& titlu, const string& artist, int rank) const;
	vector<Melodie> sorteazaMelodii() const;
	int getNrPieseRank(int rank) const;
};

