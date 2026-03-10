#include "joc.h"

Joc::Joc(const string& titlu, int pret, const string& platforma, int age)
{
	this->titlu = titlu;
	this->pret = pret;
	this->platforma = platforma;
	this->age = age;
}

int Joc::getAge() const
{
	return age;
}

const string& Joc::getPlatforma() const
{
	return platforma;
}

int Joc::getPret() const
{
	return pret;
}

const string& Joc::getTitlu() const
{
	return titlu;
}

void Joc::setAge(int ageNou)
{
	this->age = ageNou;
}

void Joc::setPlatforma(const string& platformaNoua)
{
	this->platforma = platformaNoua;
}

void Joc::setPret(int pretNou)
{
	this->pret = pretNou;
}

void Joc::setTitlu(const string& titluNou)
{
	this->titlu = titluNou;
}