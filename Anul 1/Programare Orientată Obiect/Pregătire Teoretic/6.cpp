#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using std::string;
using std::cout;
using std::vector;


//4
class Sesiune
{
private:
	string nume;
	vector<string> studenti;

public:
	Sesiune(string nume) : nume(nume) {}
	Sesiune& operator+(const string& el)
	{
		studenti.push_back(el);
		return *this;
	}
	auto begin()
	{
		return studenti.begin();
	}
	auto end()
	{
		return studenti.end();
	}
	const string& getNume() const
	{
		return nume;
	}
	vector<string> select(const string& nume)
	{
		vector<string>new_studenti;
		copy_if(studenti.begin(), studenti.end(), back_inserter(new_studenti), [&](string el) {
			return el.find(nume) != -1;
			});
		return new_studenti;
	}
};

class Conferinta{
private:
	vector<Sesiune> sesiuni;
public:
	Sesiune& track(const string& nume)
	{
		for (auto& el : sesiuni)
		{
			if (el.getNume() == nume)
			{
				return el;
			}
		}
		sesiuni.push_back(Sesiune(nume));
		return sesiuni[sesiuni.size() - 1];
	}
};

void conferinta()
{
	Conferinta conf;
	conf.track("Artificial Inteligence") + "Ion Ion" + "Vasile Aron";
	Sesiune s = conf.track("Software");
	s + "Anar Lior" + "Aurora Bran";
	for (auto name : conf.track("Artificial Intelignce"))
	{
		cout << name << " ";
	}
	vector<string> v = conf.track("Software").select("ar");
	for (auto name : v)
	{
		cout << name << ",";
	}
}

//3

class Mancare
{
private:
	int pret;

public:
	virtual string descriere() = 0;
	virtual ~Mancare() = default;
	virtual int getPret() { return pret; }
};

class Burger : public Mancare
{
private:
	string nume;

public:
	Burger(const string& nume) : nume(nume){}
	virtual string descriere() { return "Burger " + nume + " "; }
	virtual ~Burger() = default;
	virtual int getPret()
	{
		if (nume == "Big Tasty")
		{
			return 20;
		}
		if (nume == "Booster")
		{
			return 18;
		}
		if (nume == "McPuisor")
		{
			return 15;
		}
	}
};

class CuCartof : public Mancare
{
private:
	Mancare* m1;

public:
	CuCartof(Mancare* m1) : m1(m1) {}
	virtual string descriere() { return m1->descriere() + "cu cartofi "; }
	virtual ~CuCartof() { delete m1; }
	virtual int getPret() { return m1->getPret() + 3; }
};

class CuSos : public Mancare
{
private:
	Mancare* m1;

public:
	CuSos(Mancare* m1) : m1(m1) {}
	virtual string descriere() { return m1->descriere() + "cu sos "; }
	virtual ~CuSos() { delete m1; }
	virtual int getPret() { return m1->getPret() + 2; }
};

void comanda()
{
	vector<Mancare*> mancare;
	mancare.push_back(new Burger("McPuisor"));
	mancare.push_back(new CuSos(new CuCartof(new Burger("Big Tasty"))));
	mancare.push_back(new CuCartof(new Burger("Booster")));
	mancare.push_back(new CuSos(new Burger("Booster")));
	for (const auto& burger : mancare)
	{
		cout << burger->descriere() << "la pretul de " << burger->getPret() << " lei. \n";
	}
}

int main()
{
	comanda();
	conferinta();
}