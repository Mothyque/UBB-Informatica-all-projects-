#pragma once
#include "Repository.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	vector<Tractor>& afiseazaTractoare() const;
	void addTractor(int id, const string& nume, const string& tip, int nrRoti) const;
	void stergeTractor(int id) const;
	vector<Tractor> sorteazaTractoare() const;
	void decrementeazaNrRoti(int id) const;
};

