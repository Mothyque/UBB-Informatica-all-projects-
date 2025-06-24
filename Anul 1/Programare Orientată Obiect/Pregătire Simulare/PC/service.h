#pragma once
#include "repo.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Procesor>& afiseazaProcesoare() const;
	const vector<PlacaDeBaza>& afiseazaPlaciDeBaza() const;
	void adaugaPlacaDeBaza(const string& nume, const string& socluProcesor, int pret) const;
	vector<PlacaDeBaza> filtreazaPlaciDeBaza(const string& socluProcesor) const;

};
