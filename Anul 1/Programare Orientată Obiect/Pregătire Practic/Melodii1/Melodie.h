#pragma once
#include <string>

using std::string;

class Melodie
{
private:
	int id;
	string titlu;
	string artist;
	int rank;

public:
	Melodie(int id, const string& titlu, const string& artist, int rank);
	~Melodie() = default;
	int getId() const;
	int getRank() const;
	const string& getTitlu() const;
	const string& getArtist() const;
	void setId(int idNou);
	void setRank(int rankNou);
	void setTitlu(const string& titluNou);
	void setArtist(const string& artistNou);
	bool operator==(const Melodie& m1) const;
};

