#include <iostream>
#include <string>
#include <vector>

using std::cout;
using std::string;
using std::vector; 

// 4
template<typename ElemType> class Expresie
{
private:
	ElemType operand;
	vector<ElemType> undo_list;

public:
	Expresie<ElemType>(int operand) : operand (operand) {
		undo_list.push_back(operand);
	}
	Expresie<ElemType>(const Expresie<ElemType>& exp) : operand(exp.operand), undo_list(exp.undo_list) {}
	Expresie& operator=(const Expresie<ElemType>& exp)
	{
		operand = exp.operand;
		undo_list = exp.undo_list;
		return *this;
	}
	Expresie operator+(ElemType op)
	{
		Expresie<ElemType> exp = *this;
		exp.operand += op;
		exp.undo_list.push_back(exp.operand);
		return exp;
	}
	Expresie operator-(ElemType op)
	{
		Expresie<ElemType> exp = *this;
		exp.operand -= op;
		exp.undo_list.push_back(exp.operand);
		return exp;
	}
	Expresie& undo()
	{
		operand = undo_list[undo_list.size() - 2];
		undo_list.pop_back();
		return *this;
	}
	ElemType valoare()
	{
		return operand;
	}

	
public:
	
};

void operatii()
{
	Expresie<int> exp{ 3 };
	exp = exp + 7 + 3;
	exp = exp - 8;
	cout << exp.valoare() << "\n";
	exp.undo();
	cout << exp.valoare() << "\n";
	exp.undo().undo();
	cout << exp.valoare() << "\n";
}

//3

class Channel
{
public:
	virtual void send(const string& msg) const = 0;
	virtual ~Channel() = default;
};

class Telefon : public Channel
{
private:
	int nrTel;
public:
	Telefon(int nrTel) : nrTel(nrTel) {}
	virtual ~Telefon() = default;
	virtual void send(const string& msg) const override
	{
		int nr = rand() % 100;
		if (nr % 2 == 0)
		{
			throw std::exception();
		}
		cout << "Dial: " << nrTel << "\n";
	}
};

class Fax : public Telefon
{
public:
	Fax(int nrTel) : Telefon(nrTel) {}
	virtual ~Fax() = default;
	virtual void send(const string& msg) const override
	{
		Telefon::send(msg);
		cout << "Sending fax...\n";
	}
};

class Sms : public Telefon
{
public:
	Sms(int nrTel) : Telefon(nrTel) {}
	virtual ~Sms() = default;
	virtual void send(const string& msg) const override
	{
		Telefon::send(msg);
		cout << "Sending Sms...\n";
	}
};

class Failover : public Channel
{
private:
	Channel* c1;
	Channel* c2;

public:
	Failover(Channel* c1, Channel* c2) : c1(c1), c2(c2) {}
	virtual ~Failover()
	{
		delete c1;
		delete c2;
	}
	virtual void send(const string& msg) const override
	{
		try
		{
			c1->send(msg);
		}
		catch(const std::exception& exp)
		{
			c2->send(msg);
		}
	}
};

class Contact : public Channel
{
private:
	Channel* c1;
	Channel* c2;
	Channel* c3;

public:
	Contact(Channel* c1, Channel* c2, Channel* c3) : c1(c1), c2(c2), c3(c3) {}
	~Contact()
	{
		delete c1;
		delete c2;
		delete c3;
	}
	virtual void send(const string& msg) const override
	{
		int ok = 0;
		while (!ok)
		{
			try
			{
				c1->send(msg);
				ok = 1;
				break;
			}
			catch (std::exception& e){}
			try
			{
				c2->send(msg);
				ok = 1;
				break;
			}
			catch (std::exception& e) {}
			try
			{
				c3->send(msg);
				ok = 1;
				break;
			}
			catch (std::exception& e) {}
		}
	}
};

Contact* get_contact()
{
	return new Contact{ new Telefon{12234} ,new Failover{new Fax{1111},new Sms{2222}} ,new Failover{new Failover{new Telefon{3333},new Fax{4444}},new Sms{5555}} };
}


int main()
{
	srand(time(NULL));
	Contact* contact = get_contact();
	contact->send("msg1");
	cout << "\n";
	contact->send("msg2");
	cout << "\n";
	contact->send("msg3");
	cout << "\n";
	operatii();
}
