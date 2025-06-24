#include "tests.h"
#include "Service.h"
#include <cassert>
#include <iostream>
using std::cout;

void testDomain()
{
	// Testeaza functiile din Domain
	Student s(1, "Nume1", 18, "info");
	assert(s.getNrMatricol() == 1);
	assert(s.getNume() == "Nume1");
	assert(s.getVarsta() == 18);
	assert(s.getFacultate() == "info");
	s.setNrMatricol(2);
	s.setNume("Nume2");
	s.setVarsta(19);
	s.setFacultate("mate");
	assert(s.getNrMatricol() == 2);
	assert(s.getNume() == "Nume2");
	assert(s.getVarsta() == 19);
	assert(s.getFacultate() == "mate");
}

void testRepository()
{
	// Testeaza functiile din Repository
	Repository repo("test.txt");
	assert(repo.getAll().size() == 10);
	Student s(11, "Nume", 19, "info");
	repo.add(s);
	assert(repo.getAll().size() == 11);
	repo.remove(s);
	assert(repo.getAll().size() == 10);
}

void testService()
{
	// Testeaza functiile din Service
	Repository repo("test.txt");
	Service service(repo);
	try
	{
		service.undo();
		assert(false);
	}
	catch (const std::exception& e)
	{
		assert(true);
	}
	try
	{
		service.redo();
		assert(false);
	}
	catch (const std::exception& e)
	{
		assert(true);
	}
	assert(service.afiseazaStudenti().size() == 10);
	service.addStudent(11, "Nume", 19, "info");
	assert(service.afiseazaStudenti().size() == 11);
	vector<int> copieVarsta;
	for (int i = 0; i < service.afiseazaStudenti().size(); i++)
	{
		copieVarsta.push_back(service.afiseazaStudenti()[i].getVarsta());
	}
	service.modifyAge(-1);
	for (int i = 0; i < service.afiseazaStudenti().size(); i++)
	{
		assert(service.afiseazaStudenti()[i].getVarsta() == copieVarsta[i] - 1);
	}
	copieVarsta.clear();
	for (const auto& student : service.afiseazaStudenti())
	{
		copieVarsta.push_back(student.getVarsta());
	}
	service.modifyAge(1);
	for (int i = 0; i < service.afiseazaStudenti().size(); i++)
	{
		assert(service.afiseazaStudenti()[i].getVarsta()== copieVarsta[i] + 1);
	}
	service.undo();
	for (int i = 0; i < service.afiseazaStudenti().size(); i++)
	{
		assert(service.afiseazaStudenti()[i].getVarsta() == copieVarsta[i]);
	}
	service.redo();
	for (int i = 0; i < service.afiseazaStudenti().size(); i++)
	{
		assert(service.afiseazaStudenti()[i].getVarsta() == copieVarsta[i] + 1);
	}
	service.removeStudent(11);
	assert(service.afiseazaStudenti().size() == 10);
	service.undo();
	assert(service.afiseazaStudenti().size() == 11);
	service.redo();
	assert(service.afiseazaStudenti().size() == 10);
	vector<Student> sortat = service.sorteaza();
	for (int i = 0; i < sortat.size() - 1; i++)
	{
		assert(sortat[i].getVarsta() <= sortat[i + 1].getVarsta());
	}
}

void testAll()
{
	// Apeleaza toate testele
	testDomain();
	testRepository();
	testService();
}