#include "doctor.h"

Doctor::Doctor(const string& cnp, const string& prenume, const string& sectie, bool concediu)
{
	this->cnp = cnp;
	this->prenume = prenume;
	this->sectie = sectie;
	this->concediu = concediu;
}

const string& Doctor::getCnp() const
{
	return cnp;
}

const string& Doctor::getPrenume() const
{
	return prenume;
}

bool Doctor::getConcediu() const
{
	return concediu;
}

const string& Doctor::getSectie() const
{
	return sectie;
}

void Doctor::setCnp(const string& cnp_nou)
{
	this->cnp = cnp_nou;
}

void Doctor::setConcediu(bool concediu_nou)
{
	this->concediu = concediu_nou;
}

void Doctor::setPrenume(const string& prenume_nou)
{
	this->prenume = prenume_nou;
}

void Doctor::setSectie(const string& sectie_nou)
{
	this->sectie = sectie_nou;
}








