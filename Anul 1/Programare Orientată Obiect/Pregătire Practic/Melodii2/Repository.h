#pragma once
#include <vector>
#include "Melodie.h"
using std::vector;

class Repository
{
private:
	vector<Melodie> melodii;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<Melodie>& getAll() const;
	void add(const Melodie& melodie);
	void remove(const Melodie& melodie);
	void loadFromFile();
	void saveToFile() const;
};

