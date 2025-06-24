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
	void addObserver(Observer* observer)
	{
		observeri.push_back(observer);
	}
	void notifyObservers() const
	{
		for (const auto& obs : observeri)
		{
			obs->update();
		}
	}
};