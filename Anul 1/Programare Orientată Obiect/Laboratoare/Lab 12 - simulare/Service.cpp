#include "Service.h"

Service::Service(Repository& repo) : repo(repo) {}

const vector<Echipa>& Service::afiseazaEchipe() const
{
	return repo.getAll();
}

