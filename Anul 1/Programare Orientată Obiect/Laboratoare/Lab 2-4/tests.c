#include <stdio.h>
#include <assert.h>
#include <stdlib.h>
#include <string.h>

#include "controller.h"
#include "oferta.h"
#include "repo.h"
#include "validator.h"

void Teste_Domain()
{
	/* Teste pentru Domain */

	Oferta* o = Creeaza_Oferta("apartament", 50, "strada1", 100);
	assert(o != NULL);

	assert(strcmp(Get_Tip(o), "apartament") == 0);
	assert(Get_Suprafata(o) == 50);
	assert(strcmp(Get_Adresa(o), "strada1") == 0);
	assert(Get_Pret(o) == 100);

	Set_Tip(o, "casa");
	Set_Suprafata(o, 60);
	Set_Adresa(o, "strada2");
	Set_Pret(o, 200);
	assert(strcmp(Get_Tip(o), "casa") == 0);
	assert(Get_Suprafata(o) == 60);
	assert(strcmp(Get_Adresa(o), "strada2") == 0);
	assert(Get_Pret(o) == 200);

	Distruge_Oferta(o);
}

void Teste_Repo()
{
	/* Teste pentru Repository*/

	Repository* repo = (Repository*)malloc(sizeof(Repository));
	Initializeaza_Repo(repo);
	Oferta* o1 = Creeaza_Oferta("apartament", 50, "strada1", 100);
	Oferta* o2 = Creeaza_Oferta("apartament", 60, "strada2", 200);

	Adauga_Oferta(repo, o1);
	Adauga_Oferta(repo, o2);

	Repository* repo2 = Get_All(repo);
	assert(repo2->lungime == 2);
	assert(strcmp(Get_Tip(repo2->oferte[0]), "apartament") == 0);
	assert(Get_Suprafata(repo2->oferte[0]) == 50);
	assert(strcmp(Get_Adresa(repo2->oferte[0]), "strada1") == 0);
	assert(Get_Pret(repo2->oferte[0]) == 100);

	Oferta* o3 = Creeaza_Oferta("casa", 70, "strada3", 300);
	Adauga_Oferta(repo, o3);
	assert(repo->lungime == 3);
	assert(repo->capacitate == 4);
	assert(strcmp(Get_Tip(repo->oferte[2]), "casa") == 0);
	assert(Get_Suprafata(repo->oferte[2]) == 70);
	assert(strcmp(Get_Adresa(repo->oferte[2]), "strada3") == 0);
	assert(Get_Pret(repo->oferte[2]) == 300);

	Oferta* o4 = Creeaza_Oferta("teren", 900, "strada1", 500);
	Modifica_Oferta(repo, o4);
	assert(strcmp(Get_Tip(repo->oferte[0]), "teren") == 0);
	assert(Get_Suprafata(repo->oferte[0]) == 900);
	assert(strcmp(Get_Adresa(repo->oferte[0]), "strada1") == 0);
	assert(Get_Pret(repo->oferte[0]) == 500);

	assert(repo->lungime == 3);
	Sterge_Oferta(repo, "strada1");
	assert(repo->lungime == 2);

	Free_Repo(repo);
	Free_Repo(repo2);
	Distruge_Oferta(o4);
}

void Test_Controller()
{
	/* Teste pentru Controller*/

	Repository* repo = malloc(sizeof(Repository));
	Initializeaza_Repo(repo);
	Controller ctrl = Creeaza_Controller(repo);

	assert(Adauga_Oferta_Controller(&ctrl, "skibidi", 50, "strada1", 100) == 2);
	assert(Adauga_Oferta_Controller(&ctrl, "apartament", -10, "strada1", 100) == 3);
	assert(Adauga_Oferta_Controller(&ctrl, "apartament", 50, "", 100) == 4);
	assert(Adauga_Oferta_Controller(&ctrl, "apartament", 50, "strada1", -100) == 5);

	assert(Adauga_Oferta_Controller(&ctrl, "apartament", 50, "strada1", 100) == 1);
	assert(Adauga_Oferta_Controller(&ctrl, "apartament", 60, "strada2", 200) == 1);
	assert(Adauga_Oferta_Controller(&ctrl, "casa", 70, "strada3", 300) == 1);
	assert(repo->lungime == 3);
	assert(repo->capacitate == 4);
	assert(strcmp(Get_Tip(repo->oferte[2]), "casa") == 0);
	assert(Get_Suprafata(repo->oferte[2]) == 70);
	assert(strcmp(Get_Adresa(repo->oferte[2]), "strada3") == 0);
	assert(Get_Pret(repo->oferte[2]) == 300);
	
	Repository* repo_afisare = Afiseaza(&ctrl);
	assert(repo_afisare->lungime == 3);
	assert(repo_afisare->capacitate == 4);
	assert(strcmp(Get_Tip(repo_afisare->oferte[2]), "casa") == 0);
	assert(Get_Suprafata(repo_afisare->oferte[2]) == 70);
	assert(strcmp(Get_Adresa(repo_afisare->oferte[2]), "strada3") == 0);
	assert(Get_Pret(repo_afisare->oferte[2]) == 300);
	Free_Repo(repo_afisare);
	
	assert(Modifica_Oferta_Controller(&ctrl, "teren", 900, "skibidi", 500) == 2);
	assert(Modifica_Oferta_Controller(&ctrl, "skibidi", 900, "strada1", 500) == 3);
	assert(Modifica_Oferta_Controller(&ctrl, "teren", -100, "strada1", 500) == 4);
	assert(Modifica_Oferta_Controller(&ctrl, "teren", 900, "strada1", -100) == 5);
	
	assert(Modifica_Oferta_Controller(&ctrl, "teren", 900, "strada1", 500) == 1);
	assert(strcmp(Get_Tip(repo->oferte[0]), "teren") == 0);
	assert(Get_Suprafata(repo->oferte[0]) == 900);
	assert(strcmp(Get_Adresa(repo->oferte[0]), "strada1") == 0);
	assert(Get_Pret(repo->oferte[0]) == 500);

	assert(repo->lungime == 3);
	assert(Sterge_Oferta_Controller(&ctrl, "skibidi") == 2);
	assert(Sterge_Oferta_Controller(&ctrl, "strada1") == 1);
	assert(repo->lungime == 2);

	Adauga_Oferta_Controller(&ctrl, "teren", 50, "strada4", 200);
	Adauga_Oferta_Controller(&ctrl, "apartament", 50, "strada5", 300);
	Adauga_Oferta_Controller(&ctrl, "teren", 50, "strada6", 400);
	Adauga_Oferta_Controller(&ctrl, "casa", 50, "strada7", 400);
	Adauga_Oferta_Controller(&ctrl, "apartament", 50, "strada8", 400);

	Repository* repo_ordonat = Ordoneaza(&ctrl, CmpMaiMare);
	assert(strcmp(Get_Adresa(repo_ordonat->oferte[0]), "strada2") == 0);
	assert(strcmp(Get_Adresa(repo_ordonat->oferte[6]), "strada6") == 0);
	Free_Repo(repo_ordonat);

	
	Repository* repo_ordonat2 = Ordoneaza(&ctrl, CmpMaiMic);
	assert(strcmp(Get_Adresa(repo_ordonat2->oferte[0]), "strada6") == 0);
	assert(strcmp(Get_Adresa(repo_ordonat2->oferte[6]), "strada2") == 0);
	Free_Repo(repo_ordonat2);

	
	Repository* repo_filtrat_pret = Filtrare(&ctrl, "pret", "400");
	assert(repo_filtrat_pret->lungime == 3);
	Free_Repo(repo_filtrat_pret);
	
	Repository* repo_filtrat_suprafata = Filtrare(&ctrl, "suprafata", "50");
	assert(repo_filtrat_suprafata->lungime == 5);
	Free_Repo(repo_filtrat_suprafata);
	
	Repository* repo_filtrat_tip = Filtrare(&ctrl, "tip", "teren");
	assert(repo_filtrat_tip->lungime == 2);
	Free_Repo(repo_filtrat_tip);

	Repository* repo_filtrat_invalid = Filtrare(&ctrl, "skibidi", "400");
	assert(repo_filtrat_invalid == NULL);
		
	
	Free_Repo(repo);
}

void Teste_Validator()
{
	/* Teste pentru Validator*/

	assert(Valideaza_Tip("apartament") == 1);
	assert(Valideaza_Tip("casa") == 1);
	assert(Valideaza_Tip("teren") == 1);
	assert(Valideaza_Tip("garsoniera") == 0);
	assert(Valideaza_Tip("") == 0);
	assert(Valideaza_Suprafata(50) == 1);
	assert(Valideaza_Suprafata(0) == 0);
	assert(Valideaza_Suprafata(-1) == 0);
	assert(Valideaza_Adresa("strada1") == 1);
	assert(Valideaza_Adresa("") == 0);
	assert(Valideaza_Pret(100) == 1);
	assert(Valideaza_Pret(0) == 0);
	assert(Valideaza_Pret(-1) == 0);

	Repository* repo = (Repository*)malloc(sizeof(Repository));
	Initializeaza_Repo(repo);
	Oferta* o1 = Creeaza_Oferta("apartament", 50, "strada1", 100);
	Oferta* o2 = Creeaza_Oferta("apartament", 60, "strada2", 200);
	Adauga_Oferta(repo, o1);
	Adauga_Oferta(repo, o2);

	assert(Valideaza_Exista_Oferta("strada1", repo) == 1);
	assert(Valideaza_Exista_Oferta("strada3", repo) == 0);

	Free_Repo(repo);
}


void Ruleaza_Teste()
{
	/* Ruleaza toate testele */

	Teste_Domain();
	Teste_Repo();
	Test_Controller();
	Teste_Validator();
	
}