#ifndef OFERTA_H

#define OFERTA_H

typedef	struct {
	char* tip;
	int suprafata;
	char* adresa;
	int pret;
}Oferta;

Oferta* Creeaza_Oferta(const char* tip, int suprafata, const char* adresa, int pret);
void Distruge_Oferta(Oferta* o);

char* Get_Tip(Oferta* o);
int Get_Suprafata(Oferta* o);
char* Get_Adresa(Oferta* o);
int Get_Pret(Oferta* o);

void Set_Tip(Oferta* o, const char* tip);
void Set_Suprafata(Oferta* o, int suprafata);
void Set_Adresa(Oferta* o, const char* adresa);
void Set_Pret(Oferta* o, int pret);


#endif // !OFERTA_H