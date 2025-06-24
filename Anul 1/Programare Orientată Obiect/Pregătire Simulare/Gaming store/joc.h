#pragma once
#include <string>

using std::string;

class Joc
{
private:
	string titlu;
	int pret;
	string platforma;
	int age;

public:
	Joc(const string& titlu, int pret, const string& platforma, int age);
	~Joc() = default;
	const string& getTitlu() const;
	const string& getPlatforma() const;
	int getPret() const;
	int getAge() const;

	void setTitlu(const string& titluNou);
	void setPlatforma(const string& platformaNoua);
	void setPret(int pretNou);
	void setAge(int ageNou);

};

