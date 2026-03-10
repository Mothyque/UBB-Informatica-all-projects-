#pragma once
#include <vector>

#include "Tractor.h"

using std::vector;

class Repository
{
private:
	vector<Tractor> tractoare;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	vector<Tractor>& getAll();
	void add(const Tractor& tractor);
	void remove(const Tractor& tractor);
	void loadFromFile();
	void saveToFile() const;
};

