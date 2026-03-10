#include "Student.h"

Student::Student(int nrMatricol, const string& nume, int varsta, const string& facultate)
{
	// Constructorul clasei Student
	// paremetrii: nrMatricol - numarul matricol al studentului
	//			   nume- numele al studentului
	//			   varsta - varsta studentului
	//			   facultate - facultatea studentului
	this->nrMatricol = nrMatricol;
	this->nume = nume;
	this->varsta = varsta;
	this->facultate = facultate;
}

int Student::getNrMatricol() const
{
	// Returneaza numarul matricol al studentului
	// returns: numarul matricol al studentului
	return nrMatricol;
}

int Student::getVarsta() const
{
	//Returneaza varsta studentului
	// returns: varsta studentului
	return varsta;
}

const string& Student::getNume() const
{
	// Returneaza numele studentului
	// returns: numele studentului
	return nume;
}

const string& Student::getFacultate() const
{
	// Returneaza facultatea studentului
	// returns: facultatea studentului
	return facultate;
}


void Student::setNrMatricol(int nrMatricolNou)
{
	// Seteaza numarul matricol
	// parametrii: nrMatricolNou - noul numar matricol al studentului
	this->nrMatricol = nrMatricolNou;
}

void Student::setVarsta(int varstaNoua)
{
	// Seteaza varsta
	// parametrii: varstaNoua- noua varsta a studentului
	this->varsta = varstaNoua;
}

void Student::setNume(const string& numeNou)
{
	// Seteaza numele
	// parametrii: numeNou - noul nume al studentului
	this->nume = numeNou;
}

void Student::setFacultate(const string& facultateNoua)
{
	// Seteaza facultatea
	// parametrii: facultateNoua - noua facultate a studentului
	this->facultate = facultateNoua;
}