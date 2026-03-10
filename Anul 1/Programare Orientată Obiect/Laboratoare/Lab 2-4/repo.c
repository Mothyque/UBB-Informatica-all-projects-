#include <stdlib.h>
#include "repo.h"
#include <string.h>
#include "oferta.h"

void Initializeaza_Repo(Repository* repo)
{
    /* Initilizeaza repo-ul
     * repo: pointer la Repository
     */
    repo->capacitate = 2;
    repo->lungime = 0;
    repo->oferte = (Oferta**)malloc(repo->capacitate * sizeof(Oferta*));
}

void Adauga_Oferta(Repository* repo, Oferta* o)
{
    /* Adauga o oferta
     * repo: pointer la Repository
     * o: pointer la Oferta
     */
    if (repo->lungime == repo->capacitate)
    {
        Redimension_Repo(repo);
    }
    repo->oferte[repo->lungime] = o;
    repo->lungime++;
}

void Redimension_Repo(Repository* repo)
{
    /* Redimensioneaza repo-ul
     * repo: pointer la Repository
     */
    repo->capacitate *= 2;
    Oferta** oferte_noi = (Oferta**)malloc(repo->capacitate * sizeof(Oferta*));
    for (int i = 0; i < repo->lungime; i++)
    {
        oferte_noi[i] = repo->oferte[i];
    }
    free(repo->oferte);
    repo->oferte = oferte_noi;
}

Repository* Get_All(Repository* repo)
{
    /* Returneaza toate ofertele
     * repo: pointer la Repository
     * returns: pointer la Repository-ul auxiliar
     */
    Repository* repo2 = (Repository*)malloc(sizeof(Repository));
    repo2->capacitate = repo->capacitate;
    repo2->lungime = repo->lungime;
    repo2->oferte = (Oferta**)malloc(repo->capacitate * sizeof(Oferta*));
    for (int i = 0; i < repo->lungime; i++)
    {
        repo2->oferte[i] = Creeaza_Oferta(Get_Tip(repo->oferte[i]), Get_Suprafata(repo->oferte[i]), Get_Adresa(repo->oferte[i]), Get_Pret(repo->oferte[i]));
    }
    return repo2;
}

void Free_Repo(Repository* repo)
{
    /* Elibereaza memoria alocata pentru repo
     * repo: pointer la Repository
     */
	for (int i = 0; i < repo->lungime; i++)
    {
        Distruge_Oferta(repo->oferte[i]);
    }
    free((void*)repo->oferte);
    repo->oferte = NULL;

    free(repo);
}

void Modifica_Oferta(Repository* repo, Oferta* o)
{
    /* Modifica o oferta
     * repo: pointer la Repository
     * o: pointer la Oferta
     */
    for (int i = 0; i < repo->lungime; i++)
    {
        if (strcmp(Get_Adresa(repo->oferte[i]), Get_Adresa(o)) == 0)
        {
            Set_Tip(repo->oferte[i], Get_Tip(o));
            Set_Suprafata(repo->oferte[i], Get_Suprafata(o));
            Set_Pret(repo->oferte[i], Get_Pret(o));
            break;
        }
    }
}

void Sterge_Oferta(Repository* repo, const char* adresa)
{
    /* Sterge o oferta
     * repo: pointer la Repository
     * adresa: adresa imobilului
     */
    for (int i = 0; i < repo->lungime; i++)
    {
        if (strcmp(Get_Adresa(repo->oferte[i]), adresa) == 0)
        {
            Distruge_Oferta(repo->oferte[i]);
            for (int j = i; j < repo->lungime - 1; j++)
            {
                repo->oferte[j] = repo->oferte[j + 1];
            }
            repo->lungime--;
            repo->oferte[repo->lungime] = NULL; 
            break;
        }
    }
}
