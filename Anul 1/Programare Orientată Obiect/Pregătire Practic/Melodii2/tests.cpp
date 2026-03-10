#include "tests.h"
#include "Service.h"
#include <cassert>

void testDomain()
{
	Melodie m1(1, "Titlu1", "Artist1", "Pop");
	assert(m1.getId() == 1);
	assert(m1.getTitlu() == "Titlu1");
	assert(m1.getArtist() == "Artist1");
	assert(m1.getGen() == "Pop");
	m1.setId(2);
	assert(m1.getId() == 2);
	m1.setTitlu("Titlu2");
	assert(m1.getTitlu() == "Titlu2");
	m1.setArtist("Artist2");
	assert(m1.getArtist() == "Artist2");
	m1.setGen("Rock");
	assert(m1.getGen() == "Rock");
}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
	Melodie m1(11, "Titlu11", "Artist11", "Jazz");
	repo.add(m1);
	assert(repo.getAll().size() == 11);
	repo.remove(m1);
	assert(repo.getAll().size() == 10);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaMelodii().size() == 10);
	service.adaugaMelodie(12, "Titlu12", "Artist12", "Classical");
	assert(service.afiseazaMelodii().size() == 11);
	service.stergeMelodie(12);
	assert(service.afiseazaMelodii().size() == 10);
	vector<Melodie> sortat = service.sorteazaMelodii();
	for(int i = 0; i < service.afiseazaMelodii().size() - 1; i++) 
	{
		assert(sortat[i].getArtist() <= sortat[i + 1].getArtist());
	}
}

void testAll()
{
	testDomain();
	testRepository();
	testService();
}