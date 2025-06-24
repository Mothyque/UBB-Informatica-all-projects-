#pragma once
#include <string>
#include <vector>

using std::string;
using std::vector;

class Task
{
private:
	int id;
	string descriere;
	vector<string> programatori;
	string stare;

public:
	Task(int id, const string& descriere, const vector<string>& programatori, const string& stare);
	~Task() = default;
	int getId() const;
	const string& getDescriere() const;
	int getNrProgramatori() const;
	const vector<string>& getProgramatori() const;
	const string& getStare() const;
	void setId(int idNou);
	void setDescriere(const string& descriereNoua);
	void adaugaProgramator(const string& programatorNou);
	void setStare(const string& stareNoua);
};

