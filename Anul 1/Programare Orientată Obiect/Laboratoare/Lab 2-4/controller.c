#include "repo.h"
#include "controller.h"

#include <stdlib.h>
#include <string.h>

#include "oferta.h"
#include "validator.h"

Controller Creeaza_Controller(Repository* repo)
{
	/* Initalizeaza controllerul
	 * repo: pointer la Repository
	 * returns: Controller
	 */
	Controller ctrl;
	ctrl.repo = repo;
	return ctrl;
}

int CmpMaiMare(Oferta* o1, Oferta* o2)
{
	/* Compara doua oferte
	 * o1: pointer la Oferta
	 * o2: pointer la Oferta
	 * returns: 1 daca o1 > o2, 0 altfel
	 */
	if (Get_Pret(o1) > Get_Pret(o2))
		return 1;
	if (Get_Pret(o1) < Get_Pret(o2))
		return 0;

	return strcmp(Get_Tip(o1), Get_Tip(o2)) > 0;
}

int CmpMaiMic(Oferta* o1, Oferta* o2)
{
	/* Compara doua oferte
	 * o1: pointer la Oferta
	 * o2: pointer la Oferta
	 * returns: 1 daca o1 < o2, 0 altfel
	 */
	if (Get_Pret(o1) < Get_Pret(o2))
		return 1;
	if (Get_Pret(o1) > Get_Pret(o2))
		return 0;

	return strcmp(Get_Tip(o1), Get_Tip(o2)) < 0;
}


int Adauga_Oferta_Controller(Controller* ctrl, const char* tip, int suprafata, const char* adresa, int pret)
{
	/* Adauga o oferta
	 * ctrl: pointer la Controller
	 * tip: tip-ul imobilului
	 * suprafata: suprafata imobilului
	 * adresa: adresa imobilului
	 */

	if (!Valideaza_Tip(tip)) return 2;
	if (!Valideaza_Suprafata(suprafata)) return 3;
	if (!Valideaza_Adresa(adresa)) return 4;
	if (!Valideaza_Pret(pret)) return 5;

	Oferta* o = Creeaza_Oferta(tip, suprafata, adresa, pret);
	Adauga_Oferta(ctrl->repo, o);
	return 1;
}

Repository* Afiseaza(Controller* ctrl)
{
	/* Returneaza ofertele
	 * ctrl: pointer la Controller
	 * returns: Repository
	 */
	return Get_All(ctrl->repo);
}

int Modifica_Oferta_Controller(Controller* ctrl, const char* tip, int suprafata, const char* adresa, int pret)
{
	/* Modifica o oferta
	 * ctrl: pointer la Controller
	 * tip: tip-ul imobilului
	 * suprafata: suprafata imobilului
	 * adresa: adresa imobilului
	 * pret: pretul imobilului
	 * returns: 1 daca oferta a fost modificata, 2 daca oferta nu exista, 3 daca tipul este invalid, 4 daca suprafata este invalida, 5 daca pretul este invalid
	 */

	if (!Valideaza_Exista_Oferta(adresa, ctrl->repo)) return 2;
	if (!Valideaza_Tip(tip)) return 3;
	if (!Valideaza_Suprafata(suprafata)) return 4;
	if (!Valideaza_Pret(pret)) return 5;

	Oferta* o = Creeaza_Oferta(tip, suprafata, adresa, pret);
	Modifica_Oferta(ctrl->repo, o);
	Distruge_Oferta(o);
	return 1;
}

int Sterge_Oferta_Controller(Controller* ctrl, const char* adresa)
{
	/* Sterge o oferta
	 * ctrl: pointer la Controller
	 * adresa: adresa imobilului
	 * returns: 1 daca oferta a fost stearsa, 2 daca oferta nu exista
	 */
	if (!Valideaza_Exista_Oferta(adresa, ctrl->repo)) return 2;

	Sterge_Oferta(ctrl->repo, adresa);
	return 1;
}

Repository* Ordoneaza(Controller* ctrl, CmpFunc cmp)
{
	/* Ordoneaza ofertele
	 * ctrl: pointer la Controller
	 * criteriu: criteriul de ordonare
	 * returns: Repository ordonat dupa criteriul dorit
	 */
	Repository* ordonat = Get_All(ctrl->repo);
	int sortat = 1;
	while (sortat)
	{
		sortat = 0;
		for (int i = 0; i < ordonat->lungime - 1; i++)
		{
			if (cmp(ordonat->oferte[i], ordonat->oferte[i + 1]) > 0)
			{
				Oferta* aux = ordonat->oferte[i];
				ordonat->oferte[i] = ordonat->oferte[i + 1];
				ordonat->oferte[i + 1] = aux;
				sortat = 1;
			}
		}
		
	}
	return ordonat;
}

Repository* Filtrare(Controller* ctrl, const char* criteriu, const char* val)
{
	/* Filtrare oferte
	 * ctrl: pointer la Controller
	 * criteriu: criteriul de filtrare
	 * val: valoarea de filtrare
	 * returns: Repository filtrat dupa criteriul dorit
	 */
	Repository* filtrat = (Repository*)malloc(sizeof(Repository));
	Initializeaza_Repo(filtrat);
	Repository* all = Get_All(ctrl->repo);
	if (strcmp(criteriu, "suprafata") == 0)
	{
		int suprafata = atoi(val);
		for (int i = 0; i < all->lungime; i++)
		{
			if (Get_Suprafata(all->oferte[i]) == suprafata)
			{
				Adauga_Oferta(filtrat, Creeaza_Oferta(Get_Tip(all->oferte[i]), Get_Suprafata(all->oferte[i]), Get_Adresa(all->oferte[i]), Get_Pret(all->oferte[i])));
			}
		}
	}
	else if (strcmp(criteriu, "tip") == 0)
	{
		for (int i = 0; i < all->lungime; i++)
		{
			if (strcmp(Get_Tip(all->oferte[i]), val) == 0)
			{
				Adauga_Oferta(filtrat, Creeaza_Oferta(Get_Tip(all->oferte[i]), Get_Suprafata(all->oferte[i]), Get_Adresa(all->oferte[i]), Get_Pret(all->oferte[i])));
			}
		}
	}
	else if (strcmp(criteriu, "pret") == 0)
	{
		int pret = atoi(val);
		for (int i = 0; i < all->lungime; i++)
		{
			if (Get_Pret(all->oferte[i]) == pret)
			{
				Adauga_Oferta(filtrat, Creeaza_Oferta(Get_Tip(all->oferte[i]), Get_Suprafata(all->oferte[i]), Get_Adresa(all->oferte[i]), Get_Pret(all->oferte[i])));
			}
		}
	}
	else
	{
		Free_Repo(filtrat);
		Free_Repo(all);
		return NULL;
	}
	Free_Repo(all);
	return filtrat;
}
