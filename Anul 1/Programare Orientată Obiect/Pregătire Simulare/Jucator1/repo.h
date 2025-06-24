#pragma once

#include <vector>

#include "jucator.h"

using std::vector;

class Repository {
private:
	vector<Jucator> jucatori;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	void addJucator(const Jucator& jucator);
	const vector<Jucator>& getAll() const;
	void loadFromFile();
};