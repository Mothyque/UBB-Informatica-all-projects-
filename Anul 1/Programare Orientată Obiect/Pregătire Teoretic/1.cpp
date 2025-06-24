#include <iostream>
#include <vector>
#include <string>

using std::string;
using std::vector;
using std::cout;

// Exercitiul 3
class Smoothy
{
private:
	int pret;

public:
	Smoothy(int pret) : pret(pret) {}
	virtual ~Smoothy() = default;
	virtual int getPret() const { return pret; }
	virtual string descriere() const = 0;
};

class DecoratorSmoothy : public Smoothy
{
private:
	Smoothy* s;
public:
	DecoratorSmoothy(Smoothy* sm) : Smoothy(sm->getPret()), s(sm) {}
	~DecoratorSmoothy() override { delete s; }
	int getPret() const override { return s->getPret(); }
	string descriere() const override { return s->descriere(); }
};

class SmoothyCuFrisca : public DecoratorSmoothy
{
public:
	SmoothyCuFrisca(Smoothy* sm) : DecoratorSmoothy(sm) {}
	int getPret() const override { return DecoratorSmoothy::getPret() + 2; }
	string descriere() const override { return DecoratorSmoothy::descriere() + " cu frisca"; }
};

class SmoothyCuUmbreluta : public DecoratorSmoothy
{
public:
	SmoothyCuUmbreluta(Smoothy* sm) : DecoratorSmoothy(sm) {}
	int getPret() const override { return DecoratorSmoothy::getPret() + 3; }
	string descriere() const override { return DecoratorSmoothy::descriere() + " cu umbreluta"; }
};

class BasicSmoothy : public Smoothy
{
private:
	string name;
public:
	BasicSmoothy(const string& name, int pret) :name(name), Smoothy(pret) {}
	string descriere() const override { return "Smoothy cu " + name; }
};

vector<Smoothy*> Smoothies()
{
	vector<Smoothy*> smoothies;

	Smoothy* kiviCuFriscaSiUmbreluta = new SmoothyCuUmbreluta(new SmoothyCuFrisca(new BasicSmoothy("kivi", 10)));
	Smoothy* capsuniCuFrisca = new SmoothyCuFrisca(new BasicSmoothy("capsuni", 10));
	Smoothy* kivi = new BasicSmoothy("kivi", 10);
	smoothies.push_back(kiviCuFriscaSiUmbreluta);
	smoothies.push_back(capsuniCuFrisca);
	smoothies.push_back(kivi);
	return smoothies;
}


//Exercitiul 4
#include <vector>
#include <string>
#include <iostream>

using std::vector;
using std::cout;
using std::string;

template<typename Nume>class Geanta
{
private:
	Nume nume;
	vector<string> obiecte;

public:
	Geanta<Nume>(Nume nume) : nume{ nume } {}
	Geanta<Nume>(const Geanta<Nume>& g) : nume{ g.nume }, obiecte{ g.obiecte } {}

	Geanta<Nume> operator+(const string& ob)
	{
		Geanta<Nume> copie = *this;
		copie.obiecte.push_back(ob);
		return copie;
	}

	auto begin()
	{
		return obiecte.begin();
	}
	auto end()
	{
		return obiecte.end();
	}
};

void calatorie()
{
	Geanta<string> geanta{ "Ion" };
	geanta = geanta + string{ "Haine" };
	geanta = geanta + string{ "Minge" };
	geanta + string{ "Pahar" };
	for (auto obj : geanta)
	{
		cout << obj << "\n";
	}
}

int main()
{
	std::vector<Smoothy*> smoothies = Smoothies();
	for (const auto& smoothie : smoothies)
	{
		std::cout << smoothie->descriere() << " la " << smoothie->getPret() << " lei\n";
	}
	for (auto s : smoothies)
	{
		delete s;
	}

	calatorie();
	return 0;
}
