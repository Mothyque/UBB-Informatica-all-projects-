#include "Service.h"

#include <algorithm>
#include <stdexcept>

Service::Service(Repository& repo) : repo(repo) {}

vector<Tractor>& Service::afiseazaTractoare() const
{
	return repo.getAll();
}

void Service::addTractor(int id, const string& nume, const string& tip, int nrRoti) const
{
	string erori;
	for(const auto& tractor : repo.getAll()) {
		if (tractor.getId() == id) {
			erori += "ID-ul trebuie sa fie unic!\n";
			break;
		}
	}
	if(nume.empty()) {
		erori += "Numele nu poate fi gol!\n";
	}
	if(tip.empty()) {
		erori += "Tipul nu poate fi gol!\n";
	}
	if (nrRoti % 2 != 0 || nrRoti < 2 || nrRoti > 16)
	{
		erori += "Numarul de roti trebuie sa fie par si intre 2 si 16!\n";
	}
	if (!erori.empty()) {
		throw std::runtime_error(erori);
	}
	repo.add(Tractor(id, nume, tip, nrRoti));
}

void Service::stergeTractor(int id) const
{
	for(const auto& tractor : repo.getAll()) {
		if (tractor.getId() == id) {
			repo.remove(tractor);
			return;
		}
	}
}

void Service::decrementeazaNrRoti(int id) const
{
	for (auto& tractor : repo.getAll())
	{
		if (tractor.getId() == id)
		{
			tractor.setNrRoti(tractor.getNrRoti() - 2);
		}
	}
}

vector<Tractor> Service::sorteazaTractoare() const
{
	vector<Tractor> sortat, aux = repo.getAll();
	for(const auto& tractor : aux) 
	{
		sortat.push_back(tractor);
	}
	std::sort(sortat.begin(),sortat.end(),[](const Tractor& a, const Tractor& b) {
		return a.getNume() < b.getNume();
		});
	return sortat;
}