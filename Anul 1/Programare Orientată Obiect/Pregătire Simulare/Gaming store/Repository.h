#pragma once
#include <vector>

#include "joc.h"

using std::vector;

class Repository
{
private:
	vector<Joc> jocuri;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	void loadFromFile();
	const vector<Joc>& getAll() const;
};

