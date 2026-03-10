#include <iostream>
#include <string>
#include <algorithm>
#include <vector>

using std::string;
using std::vector;
using std::ostream;
using std::cout;

class Transformer
{
public:
	virtual void transform(vector<int>& nrs) = 0;
	virtual ~Transformer() = default;
};

class Adder : public Transformer
{
private:
	int cat;

public:
	Adder(int cat) : cat(cat) {}
	virtual void transform(vector<int>& nrs) override
	{
		for (auto& el : nrs)
		{
			el += cat;
		}
	}
	~Adder() override = default;
};

class Swapper : public Transformer
{
public:
	virtual void transform(vector<int>& nrs) override
	{ 
		for (int i = 0; i < nrs.size() - 1; i += 2)
		{
			int aux = nrs[i];
			nrs[i] = nrs[i + 1];
			nrs[i + 1] = aux;
		}
	}
	virtual ~Swapper() = default;

};

class Nuller : public Adder
{
public:
	Nuller(int cat) : Adder(cat) {}
	virtual void transform(vector<int>& nrs) override
	{
		for (auto& el : nrs)
		{
			if (el > 10)
			{
				el = 0;
			}
		}
	}
	virtual ~Nuller() = default;
};

class Composer : public Transformer
{
private:
	Transformer* t1;
	Transformer* t2;

public:
	Composer(Transformer* t1, Transformer* t2) : t1(t1), t2(t2) {}
	virtual void transform(vector<int>& nrs) override
	{
		t1->transform(nrs);
		t2->transform(nrs);
	}
	~Composer()
	{
		delete t1;
		delete t2;
	}
};

class Numbers : public Transformer
{
private:
	Transformer* t;
	vector<int> nrs;

public:
	Numbers(Transformer* t) : t(t) {}
	void add(int c)
	{
		nrs.push_back(c);
	}
	virtual void transform(vector<int>& nrs)
	{
		std::sort(nrs.begin(), nrs.end(), [](int a, int b)
			{
				return a > b;
			});
		t->transform(nrs);
	}
	~Numbers()
	{
		delete t;
	}
};

Numbers* f()
{
	return new Numbers(new Composer(new Nuller(9), new Composer(new Swapper(), new Adder(3))));
}

class Examen
{
private:
	string descriere;
	string ora;

public:
	Examen(const string& descriere, const string& ora) : descriere{descriere} , ora{ora} {}
	string getDescriere() const { return descriere + " ora " + ora; }
};

template<typename ElemType> class ToDo
{
private:
	vector<ElemType> examene;

public:
	ToDo<ElemType>& operator <<(const Examen& examen)
	{
		examene.push_back(examen);
		return *this;
	}
	void printToDoList(ostream& os) const
	{
		cout << "De facut:";
		for (auto& ex : examene)
		{
			os << ex.getDescriere() << ";";
		}
	}
};

void functie()
{
	ToDo<Examen> todo;
	Examen oop{ "oop scris", "8:00" };
	todo << oop << Examen{ "oop practic", "11:00" };
	cout << oop.getDescriere();
	todo.printToDoList(cout);
}

int main()
{
	functie();
}