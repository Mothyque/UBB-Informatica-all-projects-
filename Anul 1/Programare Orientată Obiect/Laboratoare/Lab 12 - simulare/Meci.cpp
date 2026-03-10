#include "Meci.h"

Meci::Meci(const string& echipa1, const string& echipa2)
{
	this->echipa1 = echipa1;
	this->echipa2 = echipa2;
}

const string& Meci::getEchipa1() const
{
	return echipa1;
}

const string& Meci::getEchipa2() const
{
	return echipa2;
}

void Meci::setEchipa1(const string& echipa1Noua)
{
	this->echipa1 = echipa1Noua;
}

void Meci::setEchipa2(const string& echipa2Noua)
{
	this->echipa2 = echipa2Noua;
}