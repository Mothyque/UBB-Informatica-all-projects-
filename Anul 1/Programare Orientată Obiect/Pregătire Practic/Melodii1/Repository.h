#pragma once
#include "Melodie.h"
#include <vector>

#include "Observable.h"

using std::vector;

class Repository
{
private:
	vector<Melodie> melodii;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	void loadFromFile();
	void storeToFile();
	const vector<Melodie>& getAll() const;
	void deleteMelodie(const Melodie& m);
	void addMelodie(const Melodie& m);
	void modifyMelodie(const Melodie& m1, const Melodie& m2);
};

