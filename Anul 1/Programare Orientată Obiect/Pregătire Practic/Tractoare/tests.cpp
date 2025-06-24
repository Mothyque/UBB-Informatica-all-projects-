#include "tests.h"
#include "Service.h"
#include <cassert>
void testDomain()
{
	Tractor t1(1, "Tractor1", "Tip1", 6);
	assert(t1.getId() == 1);
	assert(t1.getNume() == "Tractor1");
	assert(t1.getTip() == "Tip1");
	assert(t1.getNrRoti() == 6);

	t1.setId(2);
	assert(t1.getId() == 2);
	t1.setNume("Tractor2");
	assert(t1.getNume() == "Tractor2");
	t1.setTip("Tip2");
	assert(t1.getTip() == "Tip2");
	t1.setNrRoti(8);
	assert(t1.getNrRoti() == 8);
}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
	repo.add(Tractor(11, "Tractor11", "Tip11", 4));
	assert(repo.getAll().size() == 11);
	repo.remove(Tractor(11, "Tractor11", "Tip11", 4));
	assert(repo.getAll().size() == 10);
}

void testService()
{
		Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaTractoare().size() == 10);
	service.addTractor(12, "Tractor12", "Tip12", 4);
	assert(service.afiseazaTractoare().size() == 11);
	assert(service.afiseazaTractoare()[10].getNrRoti() == 4);
	service.decrementeazaNrRoti(12);
	assert(service.afiseazaTractoare()[10].getNrRoti() == 2);
	service.stergeTractor(12);
	assert(service.afiseazaTractoare().size() == 10);
	try
	{
		service.addTractor(1, "", "", 0);
		assert(false);
	}
	catch (const std::exception& e)
	{
		(void)e; 
		assert(true);
	}
	vector<Tractor> sortat = service.sorteazaTractoare();
	for(int i = 0; i < sortat.size() - 1; i++)
	{
		assert(sortat[i].getNume() <= sortat[i + 1].getNume());
	}
}

void testAll()
{
	testDomain();
	testRepository();
	testService();
}