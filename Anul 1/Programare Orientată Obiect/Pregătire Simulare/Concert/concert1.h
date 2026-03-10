#pragma once
#include <string>

using std::string;
class ConcertD
{
private:
	string numeFormatie;
	string data;
	int nrBilete;

public:
	ConcertD(const string& numeFormatie, const string& data, int nrBilete);
	~ConcertD() = default;

	const string& getNume() const;
	const string& getData() const;
	int getNrBilete() const;

	void setNume(const string& numeNou);
	void setData(const string& dataNoua);
	void setNrBilete(int nrBileteNou);

	bool operator==(const ConcertD& concert) const;
};