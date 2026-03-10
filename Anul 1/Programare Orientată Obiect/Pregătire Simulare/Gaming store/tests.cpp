#include "tests.h"

#include <assert.h>

#include "joc.h"
#include "Repository.h"
#include "Service.h"

void testDomain()
{
	Joc j("CSGO", 13, "PC", 12);
	assert(j.getAge() == 12);
	assert(j.getPret() == 13);
	assert(j.getPlatforma() == "PC");
	assert(j.getTitlu() == "CSGO");

	j.setAge(18);
	j.setPlatforma("XBOX");
	j.setPret(50);
	j.setTitlu("Elden Ring");

	assert(j.getAge() == 18);
	assert(j.getPret() == 50);
	assert(j.getPlatforma() == "XBOX");
	assert(j.getTitlu() == "Elden Ring");
}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaJocuri().size() == 10);
	vector<Joc> filtrat = service.filtreazaJocuri();
	for (const auto& joc : filtrat)
	{
		assert(joc.getAge() < 12);
	}
	vector<Joc> sortat = service.sorteazaJocuri();
	for (int i = 0; i < static_cast<int>(sortat.size() - 1); i++)
	{
		assert(sortat[i].getPret() <= sortat[i + 1].getPret());
	}
}



void testAll()
{
	testDomain();
	testRepository();
	testService();
}

