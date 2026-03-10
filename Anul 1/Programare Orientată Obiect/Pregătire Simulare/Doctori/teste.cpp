#include "teste.h"

#include <cassert>

#include "service.h"

void testeDomain()
{
	Doctor d1("5050921060019", "Andrei", "Oncologie", true);
	assert(d1.getCnp() == "5050921060019");
	assert(d1.getPrenume() == "Andrei");
	assert(d1.getSectie() == "Oncologie");
	assert(d1.getConcediu() == true);

}

void testeRepo()
{
	Repository repo("test.txt");
	assert(repo.getDoctori().size() == 4);
}

void testeService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaDoctori().size() == 4);
	assert(service.filtreaza("Sectie", "Oncologie", "Cosmin").size() == 2);
	assert(service.filtreaza("Prenume", "Oncologie", "Cosmin").size() == 2);
	assert(service.filtreaza("Toate", "Oncologie", "Cosmin").size() == 1);

}

void testAll()
{
	testeDomain();
	testeRepo();
	testeService();
}