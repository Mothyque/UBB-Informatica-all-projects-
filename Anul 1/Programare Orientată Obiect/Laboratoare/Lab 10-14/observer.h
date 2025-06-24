#pragma once
class Observer
{
public:
	virtual void actualizeaza() = 0;
	virtual ~Observer() = default;
};