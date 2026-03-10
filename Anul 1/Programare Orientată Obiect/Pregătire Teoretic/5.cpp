#include <iostream>
#include <string>
#include <vector>

using std::vector;
using std::cout;
using std::string;
using std::ostream;


// 4
template<typename ElemType> class Cos
{
private:
	vector<ElemType> obiecte;

public:
	Cos<ElemType>() {}
	~Cos<ElemType>() = default;
	Cos<ElemType>(const Cos<ElemType>& cos) : obiecte(cos.obiecte) {}
	Cos<ElemType>& operator+(ElemType ob)
	{
		obiecte.push_back(ob);
		return* this;
	}
	Cos<ElemType>& undo()
	{
		obiecte.pop_back();
		return *this;
	}
	void tipareste(ostream& os)
	{
		for (const auto& ob : obiecte)
		{
			os << ob << " ";
		}
	}
};

void cumparaturi()
{
	Cos<string> cos;
	cos = cos + "Mere";
	cos.undo();
	cos + "Mere";
	cos = cos + "Paine" + "Lapte";
	cos.undo().undo();
	cos.tipareste(cout);
}

class Participant
{
public:
	virtual ~Participant() = default;
	virtual void tipareste() = 0;
	virtual bool eVoluntar() { return true; }
};

class Personal : public Participant
{
private:
	string nume;

public:
	Personal(const string& nume) : nume(nume) {}
	virtual ~Personal() = default;
	virtual void tipareste() override { cout << nume; }
};

class Administrator : public Personal 
{
public:
	Administrator(const string& nume) : Personal(nume) {}
	virtual ~Administrator() = default;
	virtual void tipareste() override { 
		Personal::tipareste();
		cout << " Administrator";
	}
};

class Director : public Personal
{
public:
	Director(const string& nume) : Personal(nume) {}
	virtual ~Director() = default;
	virtual void tipareste() override {
		Personal::tipareste();
		cout << " Director";
	}
};

class Angajat : public Participant
{
private:
	Participant* p;
public:
	Angajat(Participant* p) : p(p) {}
	virtual ~Angajat() = default;
	virtual void tipareste() override
	{
		p->tipareste();
		cout << " Angajat";
	}
	virtual bool eVoluntar() override
	{
		return false;
	}
};

class ONG : public vector<Participant*>
{
private:

public:
	void add(Participant* part) { push_back(part); }
	vector<Participant*> getAll(bool voluntar)
	{
		vector<Participant*> aux;
		for (const auto& el : *this)
		{
			if (el->eVoluntar() == voluntar)
			{
				aux.push_back(el);
			}
		}
		return aux;
	}
};

ONG angajare()
{
	ONG a;
	a.add(new Angajat(new Administrator("Andrei")));
	a.add(new Administrator("Cornel"));
	a.add(new Director("Marin"));
	a.add(new Angajat(new Director("Marius")));
	vector<Participant*> voluntari = a.getAll(true);
	vector<Participant*> angajati = a.getAll(false);\
	for (const auto& part : voluntari)
	{
		part->tipareste();
		cout << "\n";
	}
	for (const auto& part : angajati)
	{
		part->tipareste();
		cout << "\n";
	}
	return a;
}

int main()
{
	angajare();
	cumparaturi();
}