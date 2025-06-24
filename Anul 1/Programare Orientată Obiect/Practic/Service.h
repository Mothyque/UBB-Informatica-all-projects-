#pragma once
#include "Repository.h"
#include <stack>

using std::stack;

class Service
{
private:
	Repository& repo;
	stack<vector<Student>> actiuniUndo;
	stack<vector<Student>> actiuniRedo;

public:
	Service(Repository& repo);
	~Service() = default;
	vector<Student>& afiseazaStudenti();
	void addStudent(int nrMatricol, const string& nume, int varsta, const string& facultate) const;
	void removeStudent(int nrMatricol);
	vector<Student> sorteaza() const;
	void modifyAge(int step);
	void undo();
	void redo();
};

