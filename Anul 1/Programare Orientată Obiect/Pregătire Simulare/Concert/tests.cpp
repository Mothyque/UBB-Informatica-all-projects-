#include <assert.h>

#include "concert1.h"
#include "tests.h"

#include "service.h"

void testDomain()
{
	ConcertD c1("Formatie", "22/04", 200);
	assert(c1.getNrBilete() == 200);
	assert(c1.getData() == "22/04");
	assert(c1.getNume() == "Formatie");

	c1.setNume("Formatie1");
	c1.setData("23/04");
	c1.setNrBilete(201);

	assert(c1.getNrBilete() == 201);
	assert(c1.getData() == "23/04");
	assert(c1.getNume() == "Formatie1");

	assert(ConcertD("Formatie", "22/04", 12000) == ConcertD("Formatie", "22/04", 10000));
}

void testRepo()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);

	repo.modificaConcert(repo.getAll()[0], 100);
	assert(repo.getAll()[0].getNrBilete() == 100);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaConcerte().size() == 10);

	service.modificaConcert("Formatie1", "01/02", 1000);
	assert(service.afiseazaConcerte()[0].getNrBilete() == 1000);

	vector<ConcertD> sortat = service.sorteazaConcerte();
	assert(sortat[0].getData() == "01/02");
	assert(sortat[1].getData() == "01/03");
	assert(sortat[2].getData() == "01/04");
	assert(sortat[3].getData() == "01/05");
	assert(sortat[4].getData() == "01/06");
	assert(sortat[5].getData() == "01/07");
	assert(sortat[6].getData() == "01/08");
	assert(sortat[7].getData() == "01/09");
	assert(sortat[8].getData() == "01/10");
}


void testAll()
{
	testDomain();
	testRepo();
	testService();
}