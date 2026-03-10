#pragma once

#include <vector>
#include "observer.h"

using std::vector;

class Observable
{
private:
	vector<Observer*> observers;

public:
	void addObserver(Observer* obs)
	{
		observers.push_back(obs);
	}
protected:
	void notifyObservers() const
	{
		for (auto& obs : observers)
			obs->actualizeaza();
	}
	virtual ~Observable() = default;
};