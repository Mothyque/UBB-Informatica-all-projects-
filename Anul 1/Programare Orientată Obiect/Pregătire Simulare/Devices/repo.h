#pragma once
#include "device.h"
#include <vector>

using std::vector;

class Repository
{
private:
	vector <Device> devices;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<Device>& getAll() const;
	void loadFromFile();
};
