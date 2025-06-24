
#include "concert1.h"

ConcertD::ConcertD(const string& numeFormatie, const string& data, int nrBilete)
{
	this->numeFormatie = numeFormatie;
	this->data = data;
	this->nrBilete = nrBilete;
}

const string& ConcertD::getData() const
{
	return data;
}

int ConcertD::getNrBilete() const
{
	return nrBilete;
}

const string& ConcertD::getNume() const
{
	return numeFormatie;
}

void ConcertD::setData(const string& dataNoua)
{
	this->data = dataNoua;
}

void ConcertD::setNrBilete(int nrBileteNou)
{
	this->nrBilete = nrBileteNou;
}

void ConcertD::setNume(const string& numeNou)
{
	this->numeFormatie = numeNou;
}

bool ConcertD::operator==(const ConcertD& concert) const
{
	return numeFormatie == concert.getNume() && data == concert.getData();
}
