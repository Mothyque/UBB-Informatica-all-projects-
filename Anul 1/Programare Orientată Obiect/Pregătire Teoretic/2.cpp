#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using std::cout;
using std::vector;
using std::string;


// 3
class Meniu
{
private:
	int pret;

public:
	virtual ~Meniu() = default;
	virtual int getPret() const { return pret; }
	virtual string descriere() const = 0;
};

class MicDejun : public Meniu
{
private:
	string denumire;

public:
	MicDejun(string denumire) : denumire{ denumire } {}
	~MicDejun() = default;
	string descriere() const override { return "Un meniu cu " + denumire; }
	int getPret() const 
	{
		if (denumire == "Ochiuri")
		{
			return 10;
		}
		return 15;
	}
};

class CuRacoritoare : public Meniu
{
private:
	Meniu* meniu;
public:
	CuRacoritoare(Meniu* meniu) : meniu{ meniu } {}
	~CuRacoritoare() { delete meniu; }
	int getPret() const override { return meniu->getPret() + 4; }
	string descriere() const override { return meniu->descriere() + " cu racoritoare"; }
};

class CuCafea : public Meniu
{
private:
	Meniu* meniu;
public:
	CuCafea(Meniu* meniu) : meniu{ meniu } {}
	~CuCafea() { delete meniu; }
	int getPret() const override { return meniu->getPret() + 5; }
	string descriere() const override { return meniu->descriere() + " si cu cafea"; }
};

vector<Meniu*> comanda()
{
	vector<Meniu*> items;
	Meniu* OmletaRacoritoareCafea = new CuCafea(new CuRacoritoare(new MicDejun("Omleta")));
	Meniu* OchiuriCafea = new CuCafea(new MicDejun("Ochiuri"));
	Meniu* Omleta = new MicDejun("Omleta");
	items.push_back(OmletaRacoritoareCafea);
	items.push_back(OchiuriCafea);
	items.push_back(Omleta);
	for (auto el : items)
	{
		cout << el->descriere() << " la pretul de " << el->getPret() << " de lei\n";
		delete el;
	}
	
	return items;
}

//4

template<typename Nume> class Masuratoare
{
private:
	int masuratoare;

public:
	Masuratoare(int masuratoare) : masuratoare(masuratoare) {}
	Masuratoare& operator+(int nr)
	{
		masuratoare += nr;
		return *this;
	}
	bool operator<(Masuratoare& m2)
	{
		return masuratoare < m2.masuratoare;
	}
	int value() const { return masuratoare; }
};

void masurare()
{
	vector<Masuratoare<int>> v{ 10,2,3 };
	v[2] + 3 + 2;
	std::sort(v.begin(), v.end());
	for (const auto& m : v)
	{
		cout << m.value() << " ";
	}

}

int main()
{
	comanda();
	masurare();
	_CrtDumpMemoryLeaks();
}