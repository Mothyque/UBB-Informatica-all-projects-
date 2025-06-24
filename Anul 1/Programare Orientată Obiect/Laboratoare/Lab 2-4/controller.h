#ifndef CONTROLLER_H

#define CONTROLLER_H

#include "repo.h"

typedef struct
{
	Repository* repo;
}Controller;

typedef int (*CmpFunc)(Oferta*, Oferta*);
int CmpMaiMare(Oferta* o1, Oferta* o2);
int CmpMaiMic(Oferta* o1, Oferta* o2);

Controller Creeaza_Controller(Repository* repo);
int Adauga_Oferta_Controller(Controller* ctrl, const char* tip, int suprafata, const char* adresa, int pret);
int Sterge_Oferta_Controller(Controller* ctrl, const char* adresa);
int Modifica_Oferta_Controller(Controller* ctrl, const char* tip, int suprafata, const char* adresa, int pret);
Repository* Afiseaza(Controller* ctrl);
Repository* Ordoneaza(Controller* ctrl, CmpFunc cmp);
Repository* Filtrare(Controller* ctrl, const char* criteriu, const char* valoare);

#endif

