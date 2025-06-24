#pragma once
#include "Observable.h"
#include "Repository.h"

class Service : public Observable
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Produs>& afiseazaProduse() const;
	void addProdus(int id, const string& nume, const string& tip, double pret) const;
	void removeProdus(int id) const;
	vector<Produs> sortProduse() const;
};

