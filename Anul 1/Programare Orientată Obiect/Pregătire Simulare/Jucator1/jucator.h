#pragma once
#include <string>

using std::string;

class Jucator
{
private:
	string nume;
	string tara;
	int puncte;
	int ranking;

public:
	Jucator(const string& nume, const string& tara, int puncte, int ranking);
	~Jucator() = default;
	const string& getNume() const;
	const string& getTara() const;
	int getPuncte() const;
	int getRanking() const;

	void setNume(const string& numeNou);
	void setTara(const string& taraNoua);
	void setPuncte(int puncteNoi);
	void setRanking(int rankingNou);

	bool operator==(const Jucator& jucator) const;

};