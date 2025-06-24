#include "repo.h"

class Service
{
private:
	Repository& repo;
public:
	Service(Repository& repo);
	~Service() = default;
	const vector<Doctor>& afiseazaDoctori() const;
	vector<Doctor> filtreaza(const string& optiune, const string& sectie, const string& prenume) const;
};
