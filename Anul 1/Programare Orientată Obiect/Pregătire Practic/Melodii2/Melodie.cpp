#include "Melodie.h"

Melodie::Melodie(int id, const string& titlu, const string& artist, const string& gen)
{
	this->id = id;
	this->titlu = titlu;
	this->artist = artist;
	this->gen = gen;
}

int Melodie::getId() const
{
	return id;
}

const string& Melodie::getTitlu() const
{
	return titlu;
}

const string& Melodie::getArtist() const
{
	return artist;
}

const string& Melodie::getGen() const
{
	return gen;
}

void Melodie::setId(int idNou)
{
	this->id = idNou;
}

void Melodie::setTitlu(const string& titluNou)
{
	this->titlu = titluNou;
}

void Melodie::setArtist(const string& artistNou)
{
	this->artist = artistNou;
}

void Melodie::setGen(const string& genNou)
{
	this->gen = genNou;
}