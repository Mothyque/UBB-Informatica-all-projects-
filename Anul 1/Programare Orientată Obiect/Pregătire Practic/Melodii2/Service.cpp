#include "Service.h"
#include <algorithm>

Service::Service(Repository& repo) : repo(repo) {}

const vector<Melodie>& Service::afiseazaMelodii() const 
{
	return repo.getAll();
}

void Service::adaugaMelodie(int id, const string& titlu, const string& artist, const string& gen) const
{
	repo.add(Melodie(id, titlu, artist, gen));
	notifyObservers();
}

void Service::stergeMelodie(int id) const
{
	for(const auto& melodie : repo.getAll()) 
	{
		if(melodie.getId() == id) 
		{
			repo.remove(melodie);
			return;
		}
	}
	notifyObservers();
}

vector<Melodie> Service::sorteazaMelodii() const
{
	vector<Melodie> sortat, aux = repo.getAll();
	for(const auto& melodie : aux) 
	{
		sortat.push_back(melodie);
	}
	sort(sortat.begin(), sortat.end(), [](const Melodie& a, const Melodie& b) 
	{
		return a.getArtist() < b.getArtist();
		});
	return sortat;
}
