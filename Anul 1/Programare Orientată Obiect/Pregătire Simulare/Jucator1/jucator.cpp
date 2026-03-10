#include "jucator.h"
Jucator::Jucator(const string& nume, const string& tara, int puncte, int ranking)
{
	this->nume = nume;
	this->tara = tara;
	this->puncte = puncte;
	this->ranking = ranking;
}

const string& Jucator::getNume() const
{
	return nume;
}

int Jucator::getPuncte() const
{
	return puncte;
}

int Jucator::getRanking() const
{
	return ranking;
}

const string& Jucator::getTara() const
{
	return tara;
}

bool Jucator::operator==(const Jucator& jucator) const
{
	return this->nume == jucator.nume && this->tara == jucator.tara;
}

void Jucator::setNume(const string& numeNou)
{
	this->nume = numeNou;
}

void Jucator::setPuncte(int puncteNoi)
{
	this->puncte = puncteNoi;
}

void Jucator::setRanking(int rankingNou)
{
	this->ranking = rankingNou;
}

void Jucator::setTara(const string& taraNoua)
{
	this->tara = taraNoua;
}