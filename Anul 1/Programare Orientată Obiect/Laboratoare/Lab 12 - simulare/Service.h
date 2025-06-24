#pragma once
#include "Repository.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Echipa>& afiseazaEchipe() const;
};

