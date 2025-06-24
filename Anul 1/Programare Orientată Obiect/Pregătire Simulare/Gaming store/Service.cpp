#include "Service.h"

#include <algorithm>

using std::sort;

Service::Service(Repository& repo) : repo(repo) {}

const vector<Joc>& Service::afiseazaJocuri() const
{
	return repo.getAll();
}

vector<Joc> Service::filtreazaJocuri() const
{
	vector<Joc> filtrat;
	for (const auto& joc : repo.getAll())
	{
		if (joc.getAge() < 12)
		{
			filtrat.push_back(joc);
		}
	}
	return filtrat;
}

vector<Joc> Service::sorteazaJocuri() const
{
	vector<Joc> sortat;
	for (const auto& joc : repo.getAll())
	{
		sortat.push_back(joc);
	}
	sort(sortat.begin(), sortat.end(), [&](const Joc& j1, const Joc& j2)
		{
			return j1.getPret() < j2.getPret();
		});
	return sortat;
}
