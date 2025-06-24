#pragma once
#include <string>

using std::string;

class Melodie
{
private:
	int id;
	string titlu;
	string artist;
	string gen;

public:
	Melodie(int id, const string& titlu, const string& artist, const string& gen);
	~Melodie() = default;
	int getId() const;
	const string& getTitlu() const;
	const string& getArtist() const;
	const string& getGen() const;
	void setId(int idNou);
	void setTitlu(const string& titluNou);
	void setArtist(const string& artistNou);
	void setGen(const string& genNou);
};

