#pragma once

typedef struct {
    int id;
    char* tip;
    char* destinatia;
    char* data_plecare;
    int pret;
} Oferta;

// Creează o ofertă cu alocare dinamică
Oferta* createOferta(int id, const char* tip, const char* destinatia, const char* data_plecare, int pret);

// Distruge oferta și eliberează memoria dinamică
void destroyOferta(Oferta* o);

// Validare pentru formatul datei (dd.mm.yyyy)
int esteDataValida(const char* data);

// Funcție pentru validarea unei oferte
int valideazaOferta(Oferta* o);

// Funcție pentru copierea unei oferte
void* copyOferta(void* o);
