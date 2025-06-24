#include "service.h"
#include <algorithm>

using std::sort;

Service::Service(Repository& repo) : repo(repo) {}

const vector<Device>& Service::afiseazaDevices() const
{
	return repo.getAll();
}

vector<Device> Service::sorteazaDevices(const string& optiune) const
{
	vector<Device> aux = repo.getAll();
	vector<Device> sortat;
	for (const auto& device : aux)
	{
		sortat.push_back(device);
	}
	if (optiune == "model")
	{
		sort(sortat.begin(), sortat.end(), [&](const Device& d1, const Device& d2)
			{
				return d1.getModel() < d2.getModel();
			});
	}
	else if (optiune == "pret")
	{
		sort(sortat.begin(), sortat.end(), [&](const Device& d1, const Device& d2)
			{
				return d1.getPret() < d2.getPret();
			});
	}
	return sortat;
}


