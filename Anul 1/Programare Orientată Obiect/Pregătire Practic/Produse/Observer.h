#pragma once

class Observer
{
public:
	Observer() = default;
	~Observer() = default;
	virtual void update() = 0;
};