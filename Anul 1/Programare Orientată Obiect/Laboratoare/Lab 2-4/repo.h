#ifndef REPO_H
#define REPO_H

#include "oferta.h"

typedef struct
{
    Oferta** oferte; 
    int lungime;
    int capacitate;
} Repository;

void Initializeaza_Repo(Repository* repo);
void Adauga_Oferta(Repository* repo, Oferta* o);
void Modifica_Oferta(Repository* repo, Oferta* o);
void Sterge_Oferta(Repository* repo, const char* adresa);
void Redimension_Repo(Repository* repo);
Repository* Get_All(Repository* repo);
void Free_Repo(Repository* repo);

#endif // REPO_H
