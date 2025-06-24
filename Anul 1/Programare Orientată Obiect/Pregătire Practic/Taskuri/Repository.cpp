#include "Repository.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::ofstream;
using std::stringstream;
using std::getline;

Repository::Repository(const string& filename)
{
	this->filename = filename;
	loadFromFile();
}

const vector<Task>& Repository::getAll() const
{
	return taskuri;
}

void Repository::add(const Task& task)
{
	taskuri.push_back(task);
	saveToFile();
}

void Repository::remove(const Task& task)
{
	for(int i = 0; i < taskuri.size(); ++i)
	{
		if(taskuri[i].getId() == task.getId())
		{
			taskuri.erase(taskuri.begin() + i);
			saveToFile();
			return;
		}
	}
}

vector<Task> Repository::search(const string& programator) const
{
	vector<Task> result;
	for(const auto& task : taskuri)
	{
		int okTask = 0;
		vector<string> programatori = task.getProgramatori();
		for(const auto& prog : programatori)
		{
			int ok = 1;
			for(int i = 0; i < prog.size(); i++)
			{
				if (prog[i] != programator[i])
				{
					ok = 0;
					break;
				}
			}
			if (ok == 1)
			{
				okTask = 1;
				break;
			}
		}
		if(okTask == 1)
		{
			result.push_back(task);
		}
	}
	return result;
}

void Repository::loadFromFile()
{
	ifstream fin(filename);
	string linie;
	while (getline(fin, linie))
	{
		stringstream ss(linie);
		string idS, descriere, programator, stare;
		getline(ss, idS, ',');
		getline(ss, descriere, ',');
		getline(ss, stare, ',');
		vector<string> programatoriA;
		while (getline(ss, programator, ','))
		{
			programatoriA.push_back(programator);
		}
		int id = stoi(idS);
		Task task(id, descriere, programatoriA, stare);
		taskuri.push_back(task);
	}
	fin.close();
}

void Repository::saveToFile() const
{
		ofstream fout(filename);
	for (const auto& task : taskuri)
	{
		fout << task.getId() << "," << task.getDescriere() << "," << task.getStare();
		const auto& programatori = task.getProgramatori();
		for (const auto& prog : programatori)
		{
			fout << "," << prog;
		}
		fout << "\n";
	}
	fout.close();
}