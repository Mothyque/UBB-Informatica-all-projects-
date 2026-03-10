#pragma once
#include <string>

using std::string;

class Device
{
private:
	string tip;
	string model;
	int an;
	string culoare;
	int pret;

public:
	Device(const string& tip, const string& model, int an, const string& culoare, int pret);
	~Device() = default;
	const string& getTip() const;
	const string& getCuloare() const;
	const string& getModel() const;
	int getAn() const;
	int getPret() const;

	void setTip(const string& tipNou);
	void setCuloare(const string& culoareNoua);
	void setModel(const string& modelNou);
	void setAn(int anNou);
	void setPret(int pretNou);


};