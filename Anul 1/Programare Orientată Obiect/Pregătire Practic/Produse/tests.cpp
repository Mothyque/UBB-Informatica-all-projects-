#include "tests.h"
#include "Service.h"
#include <cassert>
void testDomain()
{
	Produs p1(1, "Dell Vostro", "Laptop", 3500.0);
	assert(p1.getId() == 1);
	assert(p1.getNume() == "Dell Vostro");
	assert(p1.getTip() == "Laptop");
	assert(p1.getPret() == 3500.0);
	assert(p1.getNrVocale() == 3);

	p1.setId(2);
	assert(p1.getId() == 2);
	p1.setNume("HP Pavilion");
	assert(p1.getNume() == "HP Pavilion");
	p1.setTip("Laptop Gaming");
	assert(p1.getTip() == "Laptop Gaming");
	p1.setPret(4000.0);
	assert(p1.getPret() == 4000.0);
	assert(p1.getNrVocale() == 4);

}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
	repo.add(Produs(11, "Asus ZenBook", "Laptop", 5000.0));
	assert(repo.getAll().size() == 11);
	repo.remove(Produs(11, "Asus ZenBook", "Laptop", 5000.0));
	assert(repo.getAll().size() == 10);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaProduse().size() == 10);
	service.addProdus(11, "Asus ZenBook", "Laptop", 50.0);
	try
	{
		service.addProdus(11, "", "", 5000.0);
		assert(false);
	}
	catch (const std::exception& e)
	{
		assert(true);
    }
	assert(service.afiseazaProduse().size() == 11);
	service.removeProdus(11);
	assert(service.afiseazaProduse().size() == 10);
	vector<Produs> sortat = service.sortProduse();
	for(int i = 0 ; i < sortat.size() - 1; i++) {
		assert(sortat[i].getPret() <= sortat[i + 1].getPret());
	}
}

void testAll()
{
	testDomain();
	testRepository();
	testService();
}