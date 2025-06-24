#pragma once
#include "Repository.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Task>& afiseazaTasks() const;
	void adaugaTask(int id, const string& descriere, vector<string> programatori, const string& stare) const;
	void stergeTask(int id) const;
	vector<Task> cautaTask(const string& programator) const;
	vector<Task> sorteaza() const;
};

