#pragma once
#include <vector>
#include "Produs.h"	

using std::vector;
class Repository
{
private:
	vector<Produs> produse;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<Produs>& getAll() const;
	void add(const Produs& produs);
	void remove(const Produs& produs);
	void loadFromFile();
	void saveToFile() const;
};

