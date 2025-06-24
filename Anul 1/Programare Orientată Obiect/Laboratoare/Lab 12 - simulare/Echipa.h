#pragma once
#include <string>

using std::string;
class Echipa
{
private:
	int id;
	string nume;

public:
	Echipa(int id, const string& nume);
	~Echipa() = default;
	int getId() const;
	const string& getNume() const;
	void setId(int idNou);
	void setNume(const string& numeNou);
};

