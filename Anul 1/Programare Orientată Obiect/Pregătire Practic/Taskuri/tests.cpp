#include "tests.h"
#include "Service.h"
#include <iostream>
#include <cassert>
void testDomain()
{
	vector<string> programatori = { "programator1", "programator2" };
	Task task(1, "Descriere1", programatori, "open");
	assert(task.getId() == 1);
	assert(task.getDescriere() == "Descriere1");
	assert(task.getProgramatori() == programatori);
	assert(task.getStare() == "open");
	assert(task.getNrProgramatori() == 2);

	task.setId(2);
	assert(task.getId() == 2);
	task.setDescriere("Descriere2");
	assert(task.getDescriere() == "Descriere2");
	task.adaugaProgramator("programator3");
	assert(task.getProgramatori().size() == 3);
	task.setStare("closed");
	assert(task.getStare() == "closed");
}

void testRepository()
{
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
	repo.add(Task(11, "Descriere11", { "programator11" }, "open"));
	assert(repo.getAll().size() == 11);
	repo.remove(Task(11, "Descriere11", { "programator11" }, "open"));
	assert(repo.getAll().size() == 10);
	assert(repo.search("Maria").size() == 3);
}

void testService()
{
	Repository repo("test.txt");
	Service service(repo);
	assert(service.afiseazaTasks().size() == 10);
	service.adaugaTask(12, "Descriere12", { "programator12" }, "open");
	assert(service.afiseazaTasks().size() == 11);
	try
	{
		service.adaugaTask(12, "", {}, "skibidi");
		assert(false);
	}
	catch (const std::exception& e)
	{
		assert(true);
	}
	service.stergeTask(12);
	assert(service.afiseazaTasks().size() == 10);
	assert(service.cautaTask("Maria").size() == 3);
	vector<Task> sortat = service.sorteaza();
	for(int i = 0; i < sortat.size() - 1; i++) 
	{
		assert(sortat[i].getStare() <= sortat[i + 1].getStare());
	}
}

void testAll()
{
	testDomain();
	testRepository();
	testService();
}