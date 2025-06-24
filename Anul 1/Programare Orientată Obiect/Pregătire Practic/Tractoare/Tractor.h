#pragma once
#include <string>

using std::string;

class Tractor
{
private:
	int id;
	string nume;
	string tip;
	int nrRoti;

public:
	Tractor(int id, const string& nume, const string& tip, int nrRoti);
	~Tractor() = default;
	int getId() const;
	const string& getNume() const;
	const string& getTip() const;
	int getNrRoti() const;
	void setId(int id);
	void setNume(const string& nume);
	void setTip(const string& tip);
	void setNrRoti(int nrRoti);

};

