#include "service.h"

Service::Service(Repository& repo) : repo(repo) {}

const vector<Doctor>& Service::afiseazaDoctori() const
{
	return repo.getDoctori();
}

vector<Doctor> Service::filtreaza(const string& optiune, const string& sectie, const string& prenume) const
{
	vector<Doctor> filtrare;
	if (optiune == "Sectie")
	{
		for (const auto& doctor : repo.getDoctori())
		{
			if (doctor.getSectie() == sectie)
			{
				filtrare.push_back(doctor);
			}
		}
	}
	else if (optiune == "Prenume")
	{
		for (const auto& doctor : repo.getDoctori())
		{
			if (doctor.getPrenume() == prenume)
			{
				filtrare.push_back(doctor);
			}
		}
	}
	else
	{
		for (const auto& doctor : repo.getDoctori())
		{
			if (doctor.getPrenume() == prenume && doctor.getSectie() == sectie)
			{
				filtrare.push_back(doctor);
			}
		}
	}
	return filtrare;
}


