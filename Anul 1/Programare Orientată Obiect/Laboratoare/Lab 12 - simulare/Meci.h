#pragma once
#include <string>

using std::string;
class Meci
{
private:
	string echipa1;
	string echipa2;

public:
	Meci(const string& echipa1, const string& echipa2);
	~Meci() = default;
	const string& getEchipa1() const;
	const string& getEchipa2() const;
	void setEchipa1(const string& echipa1Noua);
	void setEchipa2(const string& echipa2Noua);

	bool operator==(const Meci& other) const
	{
		return (this->echipa1 == other.echipa1 && this->echipa2 == other.echipa2) ||
			(this->echipa1 == other.echipa2 && this->echipa2 == other.echipa1);
	}

};

