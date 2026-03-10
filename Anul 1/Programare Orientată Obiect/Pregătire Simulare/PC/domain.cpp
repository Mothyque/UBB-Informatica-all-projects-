#include "domain.h"

Componenta::Componenta(const string& nume, const string& socluProcesor, int pret)
{
	this->nume = nume;
	this->socluProcesor = socluProcesor;
	this->pret = pret;
}

const string& Componenta::getNume() const
{
	return nume;
}

int Componenta::getPret() const
{
	return pret;
}

const string& Componenta::getSoclu() const
{
	return socluProcesor;
}

void Componenta::setNume(const string& numeNou)
{
	this->nume = numeNou;
}

void Componenta::setPret(int pretNou)
{
	this->pret = pretNou;
}

void Componenta::setSoclu(const string& socluNou)
{
	this->socluProcesor = socluNou;
}

Procesor::Procesor(const string& nume, int numarThreaduri, const string& socluProcesor, int pret) : Componenta(nume, socluProcesor, pret), numarThreaduri(numarThreaduri) {}

int Procesor::getThreaduri() const
{
	return numarThreaduri;
}

void Procesor::setThreaduri(int threaduriNoi)
{
	this->numarThreaduri = threaduriNoi;
}

PlacaDeBaza::PlacaDeBaza(const string& nume, const string& socluProcesor, int pret) : Componenta(nume, socluProcesor, pret) {}