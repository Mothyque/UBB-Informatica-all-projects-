#pragma once

#include <string>

using std::string;

class Componenta
{
private:
	string nume;
	string socluProcesor;
	int pret;

public:
	Componenta(const string& nume, const string& socluProcesor, int pret);
	~Componenta() = default;
	const string& getNume() const;
	const string& getSoclu() const;
	int getPret() const;

	void setNume(const string& numeNou);
	void setSoclu(const string& socluNou);
	void setPret(int pretNou);
};

class Procesor : public Componenta
{
private:
	int numarThreaduri;

public:
	Procesor(const string& nume, int numarThreaduri, const string& socluProcesor, int pret);
	~Procesor() = default;
	int getThreaduri() const;
	void setThreaduri(int threaduriNoi);
};

class PlacaDeBaza : public Componenta
{
public:
	PlacaDeBaza(const string& nume, const string& socluProcesor, int pret);
	~PlacaDeBaza() = default;
};