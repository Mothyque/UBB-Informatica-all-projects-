#pragma once
#include "Task.h"
class Repository
{
private:
	vector<Task> taskuri;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<Task>& getAll() const;
	void add(const Task& task);
	void remove(const Task& task);
	void loadFromFile();
	void saveToFile() const;
	vector<Task> search(const string& programator) const;
};

