#pragma once
#include "Repository.h"	
#include "Observable.h"	
class Service : public Observable
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Melodie>& afiseazaMelodii() const;
	void adaugaMelodie(int id, const string& titlu, const string& artist, const string& gen) const;
	void stergeMelodie(int id) const;
	vector<Melodie> sorteazaMelodii() const;
};

