#include "tests.h"

#include <cassert>

#include "Melodie.h"
#include <Repository.h>

#include "Service.h"

void testeDomain()
{
	Melodie m1(1, "Melodie1", "Artist1", 1);
	assert(m1.getId() == 1);
	assert(m1.getRank() == 1);
	assert(m1.getTitlu() == "Melodie1");
	assert(m1.getArtist() == "Artist1");
	m1.setId(2);
	m1.setRank(2);
	m1.setTitlu("Melodie2");
	m1.setArtist("Artist2");
	assert(m1.getId() == 2);
	assert(m1.getRank() == 2);
	assert(m1.getTitlu() == "Melodie2");
	assert(m1.getArtist() == "Artist2");

	Melodie m2(2, "Melodie2", "Artist2", 2);
	assert(m1 == m2);

}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
	Melodie mCautata(1, "Bohemian Rhapsody", "Queen", 9);
	Melodie mModificata(1, "Bohemian Rhapsody", "Queen", 1);
	for (const auto& melodie : repo.getAll())
	{
		if (mCautata == melodie)
		{
			assert(melodie.getRank() == 9);
		}
	}
	repo.modifyMelodie(mCautata, mModificata);
	for (const auto& melodie : repo.getAll())
	{
		if (mCautata == melodie)
		{
			assert(melodie.getRank() == 1);
		}
	}
	repo.deleteMelodie(mCautata);
	assert(repo.getAll().size() == 9);
	repo.addMelodie(mCautata);
	assert(repo.getAll().size() == 10);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaMelodii().size() == 10);
	vector<Melodie> sortat = service.sorteazaMelodii();
	for (int i = 0; i < static_cast<int>(service.afiseazaMelodii().size()) - 1; i++)
	{
		assert(sortat[i].getRank() <= sortat[i + 1].getRank());
	}
	service.adaugaMelodie(11, "Skibidi", "Michael Jackson", 1);
	assert(service.afiseazaMelodii().size() == 11);
	for (const auto& melodie : service.afiseazaMelodii())
	{
		if (melodie.getArtist() == "Michael Jackson" && melodie.getTitlu() == "Skibidi")
		{
			assert(melodie.getRank() == 1);
			break;
		}
	}
	service.modificaMelodie("Skibidi", "Michael Jackson", 2);
	for (const auto& melodie : service.afiseazaMelodii())
	{
		if (melodie.getArtist() == "Michael Jackson" && melodie.getTitlu() == "Skibidi")
		{
			assert(melodie.getRank() == 2);
			break;
		}
	}
	service.stergeMelodie("Skibidi", "Michael Jackson");
	assert(service.afiseazaMelodii().size() == 10);
	try
	{
		service.stergeMelodie("Imagine", "John Lennon");
		assert(false);
	}
	catch(const std::exception& e)
	{
		assert(true);
	}
}

void testAll()
{
	testeDomain();
	testRepository();
	testService();
}