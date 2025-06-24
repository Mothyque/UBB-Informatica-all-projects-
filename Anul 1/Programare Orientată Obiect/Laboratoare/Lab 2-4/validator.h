#ifndef VALIDATOR_H

#define VALIDATOR_H
#include "repo.h"


int Valideaza_Tip(const char* tip);
int Valideaza_Suprafata(int suprafata);
int Valideaza_Adresa(const char* adresa);
int Valideaza_Pret(int pret);
int Valideaza_Exista_Oferta(const char* adresa, Repository* repo);


#endif // !VALIDATOR_H
