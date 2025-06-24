#include "Service.h"
#include <algorithm>
#include <stdexcept>
// Constructorul clasei service
// parametrii: repo - referinta la repository
Service::Service(Repository& repo) : repo(repo) {}

vector<Student>& Service::afiseazaStudenti() 
{
	// Afiseaza studentii
	// returns: vectorul de studenti
	return repo.getAll();
}

void Service::addStudent(int nrMatricol, const string& nume, int varsta, const string& facultate) const
{
	// Adauga un student
	// paremetrii: nrMatricol - numarul matricol al studentului
	//			   nume- numele al studentului
	//			   varsta - varsta studentului
	//			   facultate - facultatea studentului
	repo.add(Student(nrMatricol, nume, varsta, facultate));
}

void Service::removeStudent(int nrMatricol) 
{
	// Sterge un student
	// parametrii: nrMatricol - numarul matricol al studentului pe care vrem sa il stergem
	for (const auto& student : repo.getAll())
	{
		if (student.getNrMatricol() == nrMatricol)
		{
			vector<Student> copie;
			for (auto student : repo.getAll())
			{
				copie.push_back(student);
			}
			actiuniUndo.push(copie);
			repo.remove(student);
			return;
		}
	}
}

vector<Student> Service::sorteaza() const
{
	// Sorteaza studentii dupa varsta
	// returneaza: vector de studenti sortat dupa varsta
	vector<Student> sortat, aux = repo.getAll();
	for(const auto& student : aux)
	{
		sortat.push_back(student);
	}
	sort(sortat.begin(), sortat.end(), [&](const Student& s1, const Student& s2)
		{
			return s1.getVarsta() < s2.getVarsta();
		});
	return sortat;
}

void Service::modifyAge(int step)
{
	// Modifica varsta studentilor
	// parametrii: step - cu cat vrem sa modificam varsta (-1 pentru imbatranire, 1 pentru intinerire)
	vector<Student> copie;
	for (auto student : repo.getAll())
	{
		copie.push_back(student);
	}
	actiuniUndo.push(copie);
	for (auto& student : repo.getAll())
	{
		student.setVarsta(student.getVarsta() + step);
	}
	repo.saveToFile();
}

void Service::undo()
{
	// Anuleaza efectul ultimei operatii efectuate
	if (actiuniUndo.empty())
	{
		throw std::runtime_error("Nu se mai poate face undo");
	}
	actiuniRedo.push(repo.getAll());
	repo.getAll().clear();
	for (const auto& student : actiuniUndo.top())
	{
		repo.add(student);
	}
	actiuniUndo.pop();
}
void Service::redo()
{
	// Reface operatia anulata de undo
	if (actiuniRedo.empty())
	{
		throw std::runtime_error("Nu se mai poate face redo");
	}
	actiuniUndo.push(repo.getAll());
	repo.getAll().clear();
	for (const auto& student : actiuniRedo.top())
	{
		repo.add(student);
	}
	actiuniRedo.pop();
}