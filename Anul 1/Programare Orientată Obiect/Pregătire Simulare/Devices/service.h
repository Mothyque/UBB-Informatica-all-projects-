#pragma once

#include "repo.h"

class Service 
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Device>& afiseazaDevices() const;
	vector<Device> sorteazaDevices(const string& optiune) const;
};