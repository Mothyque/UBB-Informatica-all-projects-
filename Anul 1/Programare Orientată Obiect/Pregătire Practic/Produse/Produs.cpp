#include "Produs.h"

Produs::Produs(int id, const string& nume, const string& tip, double pret)
{
	this->id = id;
	this->nume = nume;
	this->tip = tip;
	this->pret = pret;
}

Produs::Produs(const Produs& p)
{
	this->id = p.id;
	this->nume = p.nume;
	this->tip = p.tip;
	this->pret = p.pret;
}

int Produs::getId() const
{
	return id;
}

const string& Produs::getNume() const
{
	return nume;
}

const string& Produs::getTip() const
{
	return tip;
}

double Produs::getPret() const
{
	return pret;
}

int Produs::getNrVocale() const
{
	int nrVoc = 0;
	for (char c : nume)
	{
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
			c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
		{
			nrVoc++;
		}
	}
	return nrVoc;
}

void Produs::setId(int idNou)
{
	this->id = idNou;
}

void Produs::setNume(const string& numeNou)
{
	this->nume = numeNou;
}

void Produs::setTip(const string& tipNou)
{
	this->tip = tipNou;
}

void Produs::setPret(double pretNou)
{
	this->pret = pretNou;
}