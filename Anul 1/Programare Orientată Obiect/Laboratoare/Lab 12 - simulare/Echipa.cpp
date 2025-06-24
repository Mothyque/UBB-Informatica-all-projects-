#include "Echipa.h"

Echipa::Echipa(int id, const string& nume)
{
	this->id = id;
	this->nume = nume;
}

int Echipa::getId() const
{
	return id;
}

const string& Echipa::getNume() const
{
	return nume;
}

void Echipa::setId(int idNou)
{
	this->id = idNou;
}

void Echipa::setNume(const string& numeNou)
{
	this->nume = numeNou;
}



