#include "Service.h"
#include <stdexcept>
#include <algorithm>

Service::Service(Repository& repo) : repo(repo) {}

const vector<Task>& Service::afiseazaTasks() const 
{
	return repo.getAll();
}

void Service::adaugaTask(int id, const string& descriere, vector<string> programatori, const string& stare) const
{
	string erori;
	for (const auto& task : repo.getAll())
	{
		if(task.getId() == id) {
			erori += "ID-ul trebuie sa fie unic!\n";
			break;
		}
	}
	if(descriere.empty()) {
		erori += "Descrierea nu poate fi vida!\n";
	}
	string stari[3] = { "open", "inprogress", "closed" }; 
	bool ok = false;
	for (auto stareC : stari)
	{
		if (stareC == stare)
		{
			ok = true;
			break;
		}
	}
	if (!ok) {
		erori += "Starea trebuie sa fie una dintre: open, inprogress, closed!\n";
	}
	if(programatori.size() < 1 || programatori.size() > 4) {
		erori += "Numarul de programatori trebuie sa fie intre 1 si 4!\n";
	}
	if (!erori.empty())
	{
		throw std::runtime_error("Eroare: " + erori);
	}
	Task task(id, descriere, programatori, stare);
	repo.add(task);
}

void Service::stergeTask(int id) const
{
	for(const auto& task : repo.getAll())
	{
		if(task.getId() == id) {
			repo.remove(task);
			return;
		}
	}
}

vector<Task> Service::sorteaza() const
{
	vector<Task> tasks = repo.getAll();
	sort(tasks.begin(), tasks.end(), [](const Task& a, const Task& b) {
		return a.getStare() < b.getStare();
	});
	return tasks;
}

vector<Task> Service::cautaTask(const string& programator) const
{
	return repo.search(programator);
}