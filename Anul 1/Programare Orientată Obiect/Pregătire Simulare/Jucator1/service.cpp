#include "service.h"

#include <random>

using std::exception;
using std::sort;

void Service::adaugaJucator(const string& nume, const string& tara, int ranking) const
{
    vector<Jucator> jucatori = repo.getAll();
    for (const auto& jucator : jucatori)
    {
        if (jucator.getRanking() == ranking)
        {
            throw exception("Rank-ul exista deja");
        }
    }

    sort(jucatori.begin(), jucatori.end(), [](const Jucator& a, const Jucator& b) {
        return a.getRanking() < b.getRanking();
        });

    int punctMin = 0, punctMax = 5000;

	int puncteSuperior = -1;
    for (const auto& j : jucatori) {
        if (j.getRanking() < ranking) {
            puncteSuperior = j.getPuncte(); 
        }
        else {
            break;
        }
    }

    int puncteInferior = -1;
    for (const auto& j : jucatori) {
        if (j.getRanking() > ranking) {
            puncteInferior = j.getPuncte();
            break;
        }
    }

    if (puncteSuperior != -1) {
        punctMax = puncteSuperior - 1;
    }
    if (puncteInferior != -1) {
        punctMin = puncteInferior + 1;
    }

    if (punctMin > punctMax) {
        punctMin = punctMax; 
    }

    std::mt19937 mt{ std::random_device{}() };
    std::uniform_int_distribution<> dist(punctMin, punctMax);
    int puncte = dist(mt);

    Jucator j(nume, tara, puncte, ranking);
    repo.addJucator(j);
}


const vector<Jucator>& Service::afiseazaJucatori() const
{
	return repo.getAll();
}

vector<Jucator> Service::sorteazaJucatori() const
{
	vector<Jucator> aux = afiseazaJucatori();
	vector<Jucator> sortat;
	for (const auto& jucator : aux)
	{
		sortat.push_back(jucator);
	}
	sort(sortat.begin(), sortat.end(), [&](const Jucator& j1, const Jucator& j2)
		{
			return j1.getRanking() < j2.getRanking();
		});
	return sortat;
}




