#pragma once
#include <string>
#include <vector>

using std::vector;
using std::string;

class Student
{
private:
	int nrMatricol;
	string nume;
	int varsta;
	string facultate;

public:
	Student(int nrMatricolm, const string& nume, int varsta, const string& facultate);
	~Student() = default;
	int getNrMatricol() const;
	int getVarsta() const;
	const string& getNume() const;
	const string& getFacultate() const;
	void setNrMatricol(int nrMatricolNou);
	void setVarsta(int varstaNoua);
	void setNume(const string& numeNou);
	void setFacultate(const string& facultateNoua);
};

