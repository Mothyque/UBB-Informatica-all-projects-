#pragma once
#include "Observer.h"
#include <vector>

using std::vector;

class Observable
{
private:
	vector<Observer*> observeri;

public:
	Observable() = default;
	~Observable() = default;
	void addObserver(Observer* obs)
	{
		observeri.push_back(obs);
	}
	void notifyObservers() const
	{
		for (auto& obs : observeri)
		{
			obs->update();
		}
	}
};