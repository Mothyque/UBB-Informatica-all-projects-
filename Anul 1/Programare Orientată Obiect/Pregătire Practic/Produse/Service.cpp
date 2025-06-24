#include "Service.h"
#include <stdexcept>
#include <algorithm>

using std::sort;

Service::Service(Repository& repo) : repo(repo) {}

void Service::addProdus(int id, const string& nume, const string& tip, double pret) const
{
	string erori;
	if (nume.empty())
	{
		erori += "Numele nu poate fi vid!\n";
	}
	if(pret < 1.0 || pret > 100.0)
	{
		erori += "Pretul trebuie sa fie intre 1 si 100!\n";
	}
	for(const auto& produs : repo.getAll())
	{
		if (produs.getId() == id)
		{
			erori += "Id-ul trebuie sa fie unic!\n";
			break;
		}
	}
	if(!erori.empty())
	{
		throw std::runtime_error(erori);
	}
	Produs produs(id, nume, tip, pret);
	repo.add(produs);
	notifyObservers();
}

vector<Produs> Service::sortProduse() const
{
	vector<Produs> sortat, aux = repo.getAll();
	for(const auto& produs : aux)
	{
		sortat.push_back(produs);
	}
	sort(sortat.begin(), sortat.end(), [](const Produs& a, const Produs& b) {
		return a.getPret() < b.getPret();
		});
	return sortat;
}

const vector<Produs>& Service::afiseazaProduse() const
{
	return repo.getAll();
}

void Service::removeProdus(int id) const
{
	for(const auto& produs : repo.getAll())
	{
		if (produs.getId() == id)
		{
			repo.remove(produs);
			return;
		}
	}
}