
	#include <stdio.h>
	#include "ui.h"

	#include <stdlib.h>
	#include <string.h>

	#include "oferta.h"
	#include "controller.h"
	#include "validator.h"

	void Ruleaza_Meniu()
	{
		/* Ruleaza meniul principal al aplicatiei */
		Repository* repo = (Repository*)malloc(sizeof(Repository));
		Controller ctrl = Creeaza_Controller(repo);
		Initializeaza_Repo(repo);
		Adauga_Valori_Repo(&ctrl);
		


		while (1)
		{
			printf("Agentie imobiliara\n");
			printf("A. Afiseaza toate ofertele\n");
			printf("1. Adauga oferta\n");
			printf("2. Actualizeaza oferta\n");
			printf("3. Sterge oferta\n");
			printf("4. Vizualizeaza oferte ordonate\n");
			printf("5. Filtreaza oferte\n");
			printf("E. Exit\n");
			char optiune;

			printf(">>>> ");
			scanf_s("%c", &optiune, 1);
			while (getchar() != '\n');
			switch (optiune)
			{
			case 'A':
			case 'a':
				printf("Oferte:\n");
				Afiseaza_Imobile(&ctrl);
				printf("--------------------\n");
				break;
			case '1':
			{
				char tip[20], adresa[50];
				int suprafata, pret;
				printf("Tip: ");
				scanf_s("%s", tip, (unsigned)sizeof(tip));
				while (getchar() != '\n');
				printf("Suprafata: ");
				scanf_s("%d", &suprafata);
				while (getchar() != '\n');
				printf("Adresa: ");
				scanf_s("%s", adresa, (unsigned)sizeof(adresa));
				while (getchar() != '\n');
				printf("Pret: ");
				scanf_s("%d", &pret);
				while (getchar() != '\n');
				int eroare = Adauga_Oferta_Controller(&ctrl, tip, suprafata, adresa, pret);
				if (eroare == 2)
				{
					printf("Tip invalid\n");
					printf("--------------------\n");
					break;
				}
				if (eroare == 3)
				{
					printf("Suprafata invalida\n");
					printf("--------------------\n");
					break;
				}
				if (eroare == 4)
				{
					printf("Adresa invalida\n");
					printf("--------------------\n");
					break;
				}
				if (eroare == 5)
				{
					printf("Pret invalid\n");
					printf("--------------------\n");
					break;
				}
				printf("Oferta adaugata\n");
				printf("--------------------\n");
				break;
			}
			case '2':
			{
				char tip[20], adresa[50];
				int suprafata, pret;
				printf("Tip: ");
				scanf_s("%s", tip, (unsigned)sizeof(tip));
				while (getchar() != '\n');
				printf("Suprafata: ");
				scanf_s("%d", &suprafata);
				while (getchar() != '\n');
				printf("Adresa: ");
				scanf_s("%s", adresa, (unsigned)sizeof(adresa));
				while (getchar() != '\n');
				printf("Pret: ");
				scanf_s("%d", &pret);
				while (getchar() != '\n');

				int verif = Valideaza_Exista_Oferta(adresa, ctrl.repo);
				if (!verif)
				{
					printf("Nu exista oferta!\n");
					printf("--------------------\n");
					break;
				}
				int eroare = Modifica_Oferta_Controller(&ctrl, tip, suprafata, adresa, pret);
				if (eroare == 2)
				{
					printf("Tip invalid\n");
					printf("--------------------\n");
					break;
				}
				if (eroare == 3)
				{
					printf("Suprafata invalida\n");
					printf("--------------------\n");
					break;
				}
				if (eroare == 4)
				{
					printf("Adresa invalida\n");
					printf("--------------------\n");
					break;
				}
				if (eroare == 5)
				{
					printf("Pret invalid\n");
					printf("--------------------\n");
					break;
				}
				Modifica_Oferta_Controller(&ctrl, tip, suprafata, adresa, pret);
				printf("Oferta modificata\n");
				printf("--------------------\n");
				break;
			}
			case '3':
			{
				char adresa[50];
				printf("Adresa: ");
				scanf_s("%s", adresa, (unsigned)sizeof(adresa));
				while (getchar() != '\n');
				int eroare = Sterge_Oferta_Controller(&ctrl, adresa);
				if (eroare == 2)
				{
					printf("Nu exista oferta!\n");
					printf("--------------------\n");
					break;
				}
				printf("Oferta stearsa\n");
				printf("--------------------\n");
				break;
			}
			case '4':
				printf("Crescator / Descrescator? \n");
				char directie[20];
				scanf_s("%s", directie, (unsigned)sizeof(directie));
				while (getchar() != '\n');
				Repository* ordonat = NULL;
				if (strncmp(directie,"crescator", sizeof(directie) - 1) == 0)
				{
					ordonat = Ordoneaza(&ctrl, CmpMaiMare);
				}
				else if (strcmp(directie, "descrescator") == 0)
				{
					ordonat = Ordoneaza(&ctrl, CmpMaiMic);
				}
				else
				{
					printf("Ordonare invalida!\n");
					printf("--------------------\n");
					break;
				}
				for (int i = 0; i < ordonat->lungime; i++)
				{
					Oferta* o = ordonat->oferte[i];
					printf("Tip: %s, Suprafata: %d, Adresa: %s, Pret: %d\n", Get_Tip(o), Get_Suprafata(o), Get_Adresa(o), Get_Pret(o));
				}
				Free_Repo(ordonat);
				printf("--------------------\n");
				break;
			case '5':
				printf("Criteriu(suprafata, tip, pret): ");
				char criteriu[20];
				scanf_s("%s", criteriu, (unsigned)sizeof(criteriu));
				while (getchar() != '\n');
				printf("Valoare: ");
				char valoare[20];
				scanf_s("%s", valoare, (unsigned)sizeof(valoare));
				while (getchar() != '\n');
				Repository* filtrat = Filtrare(&ctrl, criteriu, valoare);
				if (filtrat == NULL || filtrat->lungime == 0)
				{
					printf("Filtrare invalida!\n");
					printf("--------------------\n");
					if (filtrat != NULL) {
						Free_Repo(filtrat);
					}
					break;
				}
				for (int i = 0; i < filtrat->lungime; i++)
				{
					Oferta* o = filtrat->oferte[i];
					printf("Tip: %s, Suprafata: %d, Adresa: %s, Pret: %d\n", Get_Tip(o), Get_Suprafata(o), Get_Adresa(o), Get_Pret(o));
				}
				Free_Repo(filtrat);
				printf("--------------------\n");
				break;
			case 'E':
			case 'e':
				printf("Parasire aplicatie...\n");
				Free_Repo(repo);
				return;
			default:
				printf("Optiune invalida\n");
				break;
			}
		}
	}

	void Afiseaza_Imobile(Controller* ctrl)
	{
		/* Afiseaza toate ofertele din repo */
		Repository* afisare = Afiseaza(ctrl);
		for (int i = 0; i < afisare->lungime; i++)
		{
			Oferta* o = afisare->oferte[i];
			printf("Tip: %s, Suprafata: %d, Adresa: %s, Pret: %d\n", Get_Tip(o), Get_Suprafata(o), Get_Adresa(o), Get_Pret(o));
		}
		Free_Repo(afisare);
	}

	void Adauga_Valori_Repo(Controller* ctrl)
	{
		/* Adauga valori in repo */
		Adauga_Oferta_Controller(ctrl,"apartament", 50, "strada1", 100);
		Adauga_Oferta_Controller(ctrl, "apartament", 60, "strada2", 200);
		Adauga_Oferta_Controller(ctrl, "casa", 70, "strada3", 300);
		Adauga_Oferta_Controller(ctrl, "teren", 80, "strada4", 100);
		Adauga_Oferta_Controller(ctrl, "casa", 120, "strada5", 100);
		Adauga_Oferta_Controller(ctrl, "apartament", 80, "strada6", 200);
	}