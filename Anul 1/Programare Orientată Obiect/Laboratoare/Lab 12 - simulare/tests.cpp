#include "tests.h"

#include <assert.h>
#include <qnamespace.h>

#include "Echipa.h"
#include "Meci.h"
#include "Repository.h"
#include "Service.h"

void testDomain()
{
	Echipa e(1, "Golden State Warriors");
	assert(e.getId() == 1);
	assert(e.getNume() == "Golden State Warriors");
	e.setId(2);
	e.setNume("Dallas Mavericks");
	assert(e.getId() == 2);
	assert(e.getNume() == "Dallas Mavericks");
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
	assert(service.afiseazaEchipe().size() == 10);
}

void testMeci()
{
	Meci meci("Echipa1", "Echipa2");
	assert(meci.getEchipa1() == "Echipa1");
	assert(meci.getEchipa2() == "Echipa2");
	meci.setEchipa1("Echipa3");
	meci.setEchipa2("Echipa4");
	assert(meci.getEchipa1() == "Echipa3");
	assert(meci.getEchipa2() == "Echipa4");
	Meci meci2("Echipa4", "Echipa3");
	assert(meci == meci2);
}


void testAll()
{
	testDomain();
	testRepository();
	testService();
	testMeci();
}
