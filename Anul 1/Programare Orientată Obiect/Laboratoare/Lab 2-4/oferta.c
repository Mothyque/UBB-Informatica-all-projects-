#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "oferta.h"

Oferta* Creeaza_Oferta(const char* tip, int suprafata, const char* adresa, int pret)
{
    /* Creeaza o oferta
     * tip: tipul imobilului
     * suprafata: suprafata imobilului
     * adresa: adresa imobilului
     * pret: pretul imobilului
     * returns: pointer la Oferta noua
     */
    Oferta* o = (Oferta*)malloc(sizeof(Oferta));
    o->tip = (char*)malloc((strlen(tip) + 1) * sizeof(char));
    strcpy_s(o->tip, strlen(tip) + 1, tip);
    o->suprafata = suprafata;
    o->adresa = (char*)malloc((strlen(adresa) + 1) * sizeof(char));
    strcpy_s(o->adresa, strlen(adresa) + 1, adresa);
    o->pret = pret;
    return o;
}

void Distruge_Oferta(Oferta* o)
{
    /* Distruge o oferta
     * o: pointer la Oferta
     */
    if (o == NULL) return;

    free(o->tip);
    o->tip = NULL;

    free(o->adresa);
    o->adresa = NULL;
    free(o);
}

char* Get_Tip(Oferta* o)
{
    /* Returneaza tipul imobilului
     * o: pointer la Oferta
     */
    return o->tip;
}

int Get_Suprafata(Oferta* o)
{
    /* Returneaza suprafata imobilului
     * o: pointer la Oferta
     * returns: suprafata imobilului
     */
    return o->suprafata;
}

char* Get_Adresa(Oferta* o)
{
    /* Returneaza adresa imobilului
     * o: pointer la Oferta
     * returns: adresa imobilului
     */
    return o->adresa;
}

int Get_Pret(Oferta* o)
{
    /* Returneaza pretul imobilului
     * o: pointer la Oferta
     * returns: pretul imobilului
     */
    return o->pret;
}

void Set_Tip(Oferta* o, const char* tip)
{
    /* Seteaza tipul imobilului
     * o: pointer la Oferta
     * tip: noul tip
     */
    free(o->tip);
    o->tip = (char*)malloc((strlen(tip) + 1) * sizeof(char));
    strcpy_s(o->tip, strlen(tip) + 1, tip);
}

void Set_Suprafata(Oferta* o, int suprafata)
{
    /* Seteaza suprafata imobilului
     * o: pointer la Oferta
     * suprafata: noua suprafata
     */
    o->suprafata = suprafata;
}

void Set_Adresa(Oferta* o, const char* adresa)
{
    /* Seteaza adresa imobilului
     * o: pointer la Oferta
     * adresa: noua adresa
     */
    free(o->adresa);
    o->adresa = (char*)malloc((strlen(adresa) + 1) * sizeof(char));
    strcpy_s(o->adresa, strlen(adresa) + 1, adresa);
}

void Set_Pret(Oferta* o, int pret)
{
    /* Seteaza pretul imobilului
     * o: pointer la Oferta
     * pret: noul pret
     */
    o->pret = pret;
}
