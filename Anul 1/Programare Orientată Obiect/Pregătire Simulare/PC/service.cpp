#include "service.h"
Service::Service(Repository& repo) : repo(repo) {}

void Service::adaugaPlacaDeBaza(const string& nume, const string& socluProcesor, int pret) const
{
	PlacaDeBaza pb(nume, socluProcesor, pret);
	repo.addPlacaDeBaza(pb);
}

const vector<PlacaDeBaza>& Service::afiseazaPlaciDeBaza() const
{
	return repo.getPlaciDeBaza();
}

const vector<Procesor>& Service::afiseazaProcesoare() const
{
	return repo.getProcesoare();
}

vector<PlacaDeBaza> Service::filtreazaPlaciDeBaza(const string& socluProcesor) const
{
	vector<PlacaDeBaza> aux = repo.getPlaciDeBaza();
	vector<PlacaDeBaza> filtrat;
	for (const auto& placa_de_baza : aux)
	{
		if (placa_de_baza.getSoclu() == socluProcesor)
		{
			filtrat.push_back(placa_de_baza);
		}
	}
	return filtrat;
}


