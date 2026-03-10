#include "Service.h"
#include <exception>
#include <algorithm>

using std::exception;
using std::sort;

Service::Service(Repository& repo) : repo(repo) {}

const vector<Melodie>& Service::afiseazaMelodii() const
{
	return repo.getAll();
}

void Service::adaugaMelodie(int id, const string& titlu, const string& artist, int rank) const
{
	Melodie m(id, titlu, artist, rank);
	repo.addMelodie(m); 
}

void Service::stergeMelodie(const string& titlu, const string& artist) const
{
	int cnt = 0;
	for (const auto& melodie : repo.getAll())
	{
		if (melodie.getArtist() == artist)
		{
			cnt++;
			if (cnt == 2)
			{
				break;
			}
		}
	}
	if (cnt <= 1)
	{
		throw exception("Nu se poate sterge aceasta melodie");
	}
	Melodie m(0, titlu, artist, 0);
	repo.deleteMelodie(m);
}

void Service::modificaMelodie(const string& titlu, const string& artist, int rank) const
{
	Melodie mCautata(0, titlu, artist, 0);
	Melodie mModificata(0, "", "", rank);
	repo.modifyMelodie(mCautata, mModificata);
	notifyObservers();
}

vector<Melodie> Service::sorteazaMelodii() const
{
	vector<Melodie> sortat, aux = repo.getAll();
	for (const auto& melodie : aux)
	{
		sortat.push_back(melodie);
	}
	sort(sortat.begin(), sortat.end(), [&](const Melodie& m1, const Melodie& m2) {
		return m1.getRank() < m2.getRank();
		});
	return sortat;
}

int Service::getNrPieseRank(int rank) const
{
	int cnt = 0;
	for (const auto& melodie : repo.getAll())
	{
		if (melodie.getRank() == rank)
		{
			cnt++;
		}
	}
	return cnt;
}