#include "Melodie.h"

Melodie::Melodie(int id, const string& titlu, const string& artist, int rank)
{
	this->id = id;
	this->titlu = titlu;
	this->artist = artist;
	this->rank = rank;
}

int Melodie::getId() const
{
	return id;
}

int Melodie::getRank() const
{
	return rank;
}

const string& Melodie::getTitlu() const
{
	return titlu;
}

const string& Melodie::getArtist() const
{
	return artist;
}

void Melodie::setId(int idNou)
{
	this->id = idNou;
}

void Melodie::setRank(int rankNou)
{
	this->rank = rankNou;
}

void Melodie::setTitlu(const string& titluNou)
{
	this->titlu = titluNou;
}

void Melodie::setArtist(const string& artistNou)
{
	this->artist = artistNou;
}

bool Melodie::operator==(const Melodie& m1) const
{
	return this->titlu == m1.getTitlu() && this->artist == m1.getArtist();
}
