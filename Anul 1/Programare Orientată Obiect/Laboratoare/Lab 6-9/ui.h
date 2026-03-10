#ifndef UI_H
#define UI_H

#include <vector>
#include "service.h"

using std::vector;

class Console
{
private:
	Service& service;
	CosInchiriere& cos;

public:
	Console(Service& service, CosInchiriere& cos) : service(service), cos(cos){}
	~Console() = default;
	void runMeniu() const;
	void meniuA() const;
	void meniu1() const;
	void meniu2() const;
	void meniu3() const;
	void meniu4() const;
	void meniu5() const;
	void meniu6() const;
	void meniu7() const;
	void meniuE() const;
	void meniuU() const;
	void afisareFilme(const vector<Film>& filme) const;
};

#endif