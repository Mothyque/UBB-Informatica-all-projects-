#pragma once
#include "Echipa.h"
#include <vector>

using std::vector;


class Repository
{
private:
	vector<Echipa> echipe;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<Echipa>& getAll() const;
	void loadFromFile();
};

