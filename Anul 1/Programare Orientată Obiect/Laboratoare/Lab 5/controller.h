#pragma once
#include "repo.h"

//Adauga o oferta
int addOferta(MyList* v, MyList* undo_list, int id, char* tip, char* destinatia, char* data_plecare, int pret);

//Gaseste o oferta dupa un id dat
int findOferta(MyList* v, int id);

//Modifica o oferta
int modifyOferta(MyList* v, MyList* undo_list, int id, char* tip_nou, char* destinatia_noua, char* data_plecare_noua, int pret_nou);

//Sterge o oferta
int deleteOferta(MyList* v, MyList* undo_list, int id);

//Filtreaza dupa destinatie
MyList* destFilter(MyList* v, char* destinatia);

//Filtreaza dupa tip
MyList* tipFilter(MyList* v, char* tip);

//Filtrare dupa pret
MyList* pretFilter(MyList* v, int pret);

MyList* dataFilter(MyList* v, char* data_plecare);

void sortOferte(MyList* v, int criteriu, int ordine);

//Functie de undo
int undo(MyList** v, MyList* undo_list);

MyList* sortPret(MyList* v);