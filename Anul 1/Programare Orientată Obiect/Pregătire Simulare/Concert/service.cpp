#include "service.h"

#include <algorithm>
#include <sstream>
using std::sort;

Service::Service(Repository& repo) : repo(repo) {}

const vector<ConcertD>& Service::afiseazaConcerte() const
{
	return repo.getAll();
}

void Service::modificaConcert(const string& nume, const string& data, int nrBileteNou) const
{
	ConcertD concert(nume, data, 0);
	repo.modificaConcert(concert, nrBileteNou);
}

vector<ConcertD> Service::sorteazaConcerte() const
{
	vector<ConcertD> vectorSortat;
	const vector<ConcertD>& repo_aux = repo.getAll();
	for (const auto& concert : repo_aux)
	{
		vectorSortat.push_back(concert);
	}
	sort(vectorSortat.begin(), vectorSortat.end(), [&](const ConcertD& concert1, const ConcertD& concert2)
		{
			return concert1.getData() < concert2.getData();
		});
	return vectorSortat;
}





