#include "Tractor.h"

Tractor::Tractor(int id, const string& nume, const string& tip, int nrRoti)
{
	this->id = id;
	this->nume = nume;
	this->tip = tip;
	this->nrRoti = nrRoti;
}

int Tractor::getId() const
{
	return id;
}

const string& Tractor::getNume() const
{
	return nume;
}

const string& Tractor::getTip() const
{
	return tip;
}

int Tractor::getNrRoti() const
{
	return nrRoti;
}

void Tractor::setId(int id)
{
	this->id = id;
}

void Tractor::setNume(const string& nume)
{
	this->nume = nume;
}

void Tractor::setTip(const string& tip)
{
	this->tip = tip;
}

void Tractor::setNrRoti(int nrRoti)
{
	this->nrRoti = nrRoti;
}