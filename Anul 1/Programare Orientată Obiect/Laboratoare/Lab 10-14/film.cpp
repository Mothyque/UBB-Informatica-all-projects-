#include "film.h"
#include <string>
using std::string;

const string& Film::getTitlu() const
{
    /* Returneaza titlul filmului
     * returns: titlul filmului
     */
    return titlu;
}

const string& Film::getRegizor() const
{
    /* Returneaza numele regizorului
     * returns: numele regizorului
     */
    return regizor;
}

int Film::getAn() const
{
    /* Returneaza anul aparitiei filmului
     * returns: anul aparitiei filmului
     */
    return an_aparitie;
}

const string& Film::getActor() const
{
    /* Returneaza numele actorului principal
     * returns: numele actorului principal
     */
    return actor;
}

void Film::setTitlu(const string& titlu_nou)
{
    /* Seteaza titlul filmului
     * parameter titlu_nou: noul titlu al filmului
     */
    this->titlu = titlu_nou;
}

void Film::setRegizor(const string& regizor_nou)
{
    /* Seteaza numele regizorului
     * parameter regizor_nou: noul nume al regizorului
     */
    this->regizor = regizor_nou;
}

void Film::setAn(int an_nou)
{
    this->an_aparitie = an_nou;
}

void Film::setActor(const string& actor_nou)
{
    /* Seteaza numele actorului principal
     * parameter actor_nou: noul nume al actorului principal
     */
    this->actor = actor_nou;
}

bool Film::operator==(const Film& film) const
{
    /* Verifica daca doua filme sunt identice
     * parameter f: filmul cu care se compara
     * returns: True daca filmele sunt identice, False altfel
     */
    return titlu == film.titlu && regizor == film.regizor;
}