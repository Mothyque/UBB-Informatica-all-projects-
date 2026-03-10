#pragma once
#include "repo.h"

class Service {
private:
	Repository& repo;

public:
	Service(Repository& repo) : repo(repo) {}
	~Service() = default;
	void adaugaJucator(const string& nume, const string& tara, int ranking) const;
	const vector<Jucator>& afiseazaJucatori() const;
	vector<Jucator> sorteazaJucatori() const;
};
