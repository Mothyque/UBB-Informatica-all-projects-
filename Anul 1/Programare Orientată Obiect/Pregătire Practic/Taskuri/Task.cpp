#include "Task.h"

Task::Task(int id, const string& descriere, const vector<string>& programatori, const string& stare)
{
	this->id = id;
	this->descriere = descriere;
	this->programatori = programatori;
	this->stare = stare;
}

int Task::getId() const
{
	return id;
}

const string& Task::getDescriere() const
{
	return descriere;
}

int Task::getNrProgramatori() const
{
	return programatori.size();
}

const vector<string>& Task::getProgramatori() const
{
	return programatori;
}

const string& Task::getStare() const
{
	return stare;
}

void Task::setId(int idNou)
{
	id = idNou;
}

void Task::setDescriere(const string& descriereNoua)
{
	descriere = descriereNoua;
}

void Task::setStare(const string& stareNoua)
{
	stare = stareNoua;
}

void Task::adaugaProgramator(const string& programator)
{
	programatori.push_back(programator);
}