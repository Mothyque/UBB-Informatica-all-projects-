#pragma once
#include <string>

using std::string;
class Produs
{
private:
	int id;
	string nume;
	string tip;
	double pret;

public:
	Produs(int id, const string& nume, const string& tip, double pret);
	Produs(const Produs& altProdus);
	~Produs() = default;
	int getId() const;
	const string& getNume() const;
	const string& getTip() const;
	double getPret() const;
	int getNrVocale() const;

	void setId(int idNou);
	void setNume(const string& numeNou);
	void setTip(const string& tipNou);
	void setPret(double pretNou);

};

