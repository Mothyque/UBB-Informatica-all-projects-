#ifndef FILM_H  
#define FILM_H  

#include <string>  
using std::string;

class Film
{
private:
	string titlu;
	string regizor;
	int an_aparitie;
	string actor;

public:
	Film(const string& titlu, const string& regizor, int an_aparitie, const string& actor)
		: titlu(titlu), regizor(regizor), an_aparitie(an_aparitie), actor(actor) {
	}
	Film(const Film& film) = default;
	Film() : an_aparitie(0) {}
	~Film() = default;
	Film& operator=(const Film& film) = default;

	const string& getTitlu() const;
	const string& getRegizor() const;
	int getAn() const;
	const string& getActor() const;

	void setTitlu(const string& titlu);
	void setRegizor(const string& regizor);
	void setAn(int an);
	void setActor(const string& actor);

	bool operator==(const Film& film) const;
};

#endif