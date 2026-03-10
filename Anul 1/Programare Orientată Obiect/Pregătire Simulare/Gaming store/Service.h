#pragma once
#include "Repository.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Joc>& afiseazaJocuri() const;
	vector<Joc> sorteazaJocuri() const;
	vector<Joc> filtreazaJocuri() const;
};

