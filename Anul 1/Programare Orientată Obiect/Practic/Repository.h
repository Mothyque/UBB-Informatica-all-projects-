#pragma once
#include "Student.h"
#include <vector>
class Repository
{
private:
	vector<Student> studenti;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	vector<Student>& getAll();
	void add(const Student& stud);
	void remove(const Student& stud);
	void loadFromFile();
	void saveToFile() const;
};

