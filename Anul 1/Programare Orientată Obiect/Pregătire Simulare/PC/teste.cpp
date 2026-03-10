#include "teste.h"

#include <cassert>

#include "domain.h"
#include "repo.h"
#include "service.h"

void testDomain()
{
	Procesor p("Intel Core i7-12700K", 20, "LGA1700", 1800);
	PlacaDeBaza pb("ASUS ROG STRIX Z690-F", "LGA1700", 900);
	assert(p.getNume() == "Intel Core i7-12700K");
	assert(p.getThreaduri() == 20);
	assert(p.getSoclu() == "LGA1700");
	assert(p.getPret() == 1800);

	assert(pb.getNume() == "ASUS ROG STRIX Z690-F");
	assert(pb.getSoclu() == "LGA1700");
	assert(pb.getPret() == 900);

	p.setNume("AMD Ryzen 5 5600X");
	p.setThreaduri(12);
	p.setSoclu("AM4");
	p.setPret(1000);

	pb.setNume("MSI B550 TOMAHAWK");
	pb.setSoclu("AM4");
	pb.setPret(700);

	assert(p.getNume() == "AMD Ryzen 5 5600X");
	assert(p.getSoclu() == "AM4");
	assert(p.getThreaduri() == 12);
	assert(p.getPret() == 1000);

	assert(pb.getNume() == "MSI B550 TOMAHAWK");
	assert(pb.getSoclu() == "AM4");
	assert(pb.getPret() == 700);
}

void testRepository()
{
	Repository repo("test_procesoare.txt", "test_placi_de_baza.txt");
	assert(repo.getPlaciDeBaza().size() == 5);
	assert(repo.getProcesoare().size() == 5);
}

void testService()
{
	Repository repo("test_procesoare.txt", "test_placi_de_baza.txt");
	Service service(repo);
	assert(service.afiseazaPlaciDeBaza().size() == 5);
	assert(service.afiseazaProcesoare().size() == 5);
	for (const auto& placa_de_baza : service.filtreazaPlaciDeBaza("AM4"))
	{
		assert(placa_de_baza.getSoclu() == "AM4");
	}
	for (const auto& placa_de_baza : service.filtreazaPlaciDeBaza("LGA1700"))
	{
		assert(placa_de_baza.getSoclu() == "LGA1700");
	}
}


void testAll()
{
	testDomain();
	testRepository();
}