#include <assert.h>
#include <filesystem>

#include "jucator.h"
#include "repo.h"
#include "service.h"
#include "tests.h"
void testDomain()
{
	Jucator j("Andrei", "Romania", 1000, 1);
	assert(j.getPuncte() == 1000);
	assert(j.getRanking() == 1);
	assert(j.getNume() == "Andrei");
	assert(j.getTara() == "Romania");

	j.setNume("Cosmin");
	j.setTara("USA");
	j.setPuncte(1001);
	j.setRanking(10);

	assert(j.getPuncte() == 1001);
	assert(j.getRanking() == 10);
	assert(j.getNume() == "Cosmin");
	assert(j.getTara() == "USA");
}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().empty());
	repo.addJucator(Jucator("Cosmin", "USA", 150, 2));
	assert(repo.getAll().size() == 1);
	remove("test.txt");
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	service.adaugaJucator("Andrei", "Romania", 1);
	service.adaugaJucator("Andreea", "Romania", 5);
	service.adaugaJucator("Cosmin", "Romania", 3);
	service.adaugaJucator("Constantin", "Romania", 2);
	service.adaugaJucator("Alexandru", "Romania", 4);

	assert(service.afiseazaJucatori().size() == 5);

	assert(service.sorteazaJucatori()[0].getRanking() == 1);
	assert(service.sorteazaJucatori()[1].getRanking() == 2);
	assert(service.sorteazaJucatori()[2].getRanking() == 3);
	assert(service.sorteazaJucatori()[3].getRanking() == 4);
	assert(service.sorteazaJucatori()[4].getRanking() == 5);

	remove("test.txt");
}

void testAll()
{
	testDomain();
	testRepository();
	testService();
}