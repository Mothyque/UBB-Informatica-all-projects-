#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

int MDO::aloca_cheie()
{
	/* Aloca o cheie in MDO.
	 * Preconditii: cheile sunt ordonate crescator
	 * Complexitate: θ(1) (theta) amortizat
	 * Returneaza: pozitia cheii alocate
	 */
	if (prim_liber_cheie == -1) 
	{
		redimensioneaza_chei();
	}
	int pozitie = prim_liber_cheie;
	prim_liber_cheie = urm_cheie[prim_liber_cheie];
	return pozitie;
}

void MDO::dealoca_cheie(int pozitie)
{
	/* Dealoca o cheie in MDO.
	 * Preconditii: cheile sunt ordonate crescator
	 * Complexitate: θ(1) (theta)
	 * Parametrii: pozitie - pozitia cheii de dealocat
	 */ 
	urm_cheie[pozitie] = prim_liber_cheie;
	prim_liber_cheie = pozitie;
}

void MDO::redimensioneaza_chei()
{
	/* Redimensioneaza cheile in MDO.
	 * Preconditii: cheile sunt ordonate crescator
	 * Complexitate: θ(n) (theta)
	 */
	cap_chei *= 2;

	TCheie* chei_noi = new TCheie[cap_chei];
	int* urm_cheie_noi = new int[cap_chei];
	int* prec_cheie_noi = new int[cap_chei];
	int* head_valori_noi = new int[cap_chei];

	for (int i = 0; i < cap_chei / 2; ++i) {
		chei_noi[i] = chei[i];
		urm_cheie_noi[i] = urm_cheie[i];
		prec_cheie_noi[i] = prec_cheie[i];
		head_valori_noi[i] = head_valori[i];
	}

	for (int i = cap_chei / 2; i < cap_chei - 1; ++i) {
		urm_cheie_noi[i] = i + 1;
		prec_cheie_noi[i] = -1;  
		head_valori_noi[i] = -1;
	}
	urm_cheie_noi[cap_chei - 1] = -1; 
	prec_cheie_noi[cap_chei - 1] = -1;
	head_valori_noi[cap_chei - 1] = -1;

	prim_liber_cheie = cap_chei / 2;

	delete[] chei;
	delete[] urm_cheie;
	delete[] prec_cheie;
	delete[] head_valori;

	chei = chei_noi;
	urm_cheie = urm_cheie_noi;
	prec_cheie = prec_cheie_noi;
	head_valori = head_valori_noi;
}

int MDO::creeaza_nod_cheie(TCheie c)
{
	/* Creaza un nod cheie in MDO.
	 * Preconditii: cheile sunt ordonate crescator
	 * Complexitate: θ(1) (theta)
	 * Parametrii: c - cheia de adaugat
	 * Returneaza: pozitia cheii alocate
	 */
	int pozitie = aloca_cheie();
	chei[pozitie] = c;
	urm_cheie[pozitie] = -1;
	prec_cheie[pozitie] = -1;
	head_valori[pozitie] = -1;
	return pozitie;
}

int MDO::aloca_valoare()
{
	/* Aloca o valoare in MDO.
	 * Preconditii: -
	 * Complexitate: θ(1) (theta) amortizat
	 * Returneaza: pozitia valorii alocate
	 */
	if (prim_liber_valoare == -1) 
	{
		redimensioneaza_valori();
	}
	int pozitie = prim_liber_valoare;
	prim_liber_valoare = urm_valoare[prim_liber_valoare];
	return pozitie;
}

void MDO::dealoca_valoare(int pozitie)
{
	/* Dealoca o valoare in MDO.
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Parametrii: pozitie - pozitia valorii de dealocat
	 */
	urm_valoare[pozitie] = prim_liber_valoare;
	prim_liber_valoare = pozitie;
}

void MDO::redimensioneaza_valori()
{
	/* Redimensioneaza valorile in MDO.
	 * Preconditii: -
	 * Complexitate: θ(n) (theta)
	 */
	cap_valori *= 2;
	TValoare* valori_noi = new TValoare[cap_valori];
	int* urm_valoare_noi = new int[cap_valori];
	int* prec_valoare_noi = new int[cap_valori];

	for (int i = 0; i < cap_valori / 2; ++i) {
		valori_noi[i] = valori[i];
		urm_valoare_noi[i] = urm_valoare[i];
		prec_valoare_noi[i] = prec_valoare[i];
	}

	for (int i = cap_valori / 2; i < cap_valori - 1; ++i) {
		urm_valoare_noi[i] = i + 1;
		prec_valoare_noi[i] = -1;  
	}
	urm_valoare_noi[cap_valori - 1] = -1;
	prec_valoare_noi[cap_valori - 1] = -1;

	prim_liber_valoare = cap_valori / 2;

	delete[] valori;
	delete[] urm_valoare;
	delete[] prec_valoare;

	valori = valori_noi;
	urm_valoare = urm_valoare_noi;
	prec_valoare = prec_valoare_noi;
}


MDO::MDO(Relatie r)
{
	/* Constructorul implicit al MultiDictionarului Ordonat
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Parametrii: r - relatia de ordine
	 */
	rel = r;

	cap_chei = 10;
	chei = new TCheie[cap_chei];
	urm_cheie = new int[cap_chei];
	prec_cheie = new int[cap_chei];
	head_valori = new int[cap_chei];
	prim_cheie = -1;
	prim_liber_cheie = 0;

	cap_valori = 10;
	valori = new TValoare[cap_valori];
	urm_valoare = new int[cap_valori];
	prec_valoare = new int[cap_valori];
	prim_liber_valoare = 0;

	for (int i = 0; i < cap_chei; ++i)
	{
		urm_cheie[i] = -1;
		prec_cheie[i] = -1;
		head_valori[i] = -1;
	}

	for (int i = 0; i < cap_valori - 1; ++i)
	{
		urm_valoare[i] = i + 1;
		prec_valoare[i] = -1;
	}
	urm_valoare[cap_valori - 1] = -1;
	prec_valoare[cap_valori - 1] = -1;
}


void MDO::adauga(TCheie c, TValoare v)
{
	/* Adauga o pereche (cheie, valoare) in MDO.
	 * Preconditii: -
	 * Complexitate: O(n)
	 * Parametrii: c - cheia de adaugat
	 *			   v - valoarea de adaugat
	 */
	int pozitie_cheie = -1;
	int pozitie_curenta = prim_cheie;
	while (pozitie_curenta != - 1 && rel(chei[pozitie_curenta], c))
	{
		if (chei[pozitie_curenta] == c)
		{
			pozitie_cheie = pozitie_curenta;
			break;
		}
		pozitie_curenta = urm_cheie[pozitie_curenta];
	}
	if (pozitie_cheie == -1)
	{
		pozitie_cheie = creeaza_nod_cheie(c);
		if (prim_cheie == -1)
		{
			prim_cheie = pozitie_cheie;
		}
		else
		{
			int pozitie_anterioara = -1, curent = prim_cheie;
			while (curent != -1 && rel(chei[curent], c))
			{
				pozitie_anterioara = curent;
				curent = urm_cheie[curent];
			}
			if (pozitie_anterioara == -1)
			{
				urm_cheie[pozitie_cheie] = prim_cheie;
				prec_cheie[prim_cheie] = pozitie_cheie;
				prim_cheie = pozitie_cheie;
			}
			else
			{
				urm_cheie[pozitie_cheie] = curent;
				urm_cheie[pozitie_anterioara] = pozitie_cheie;
				prec_cheie[pozitie_cheie] = pozitie_anterioara;
				if (curent != -1)
				{
					prec_cheie[curent] = pozitie_cheie;
				}
			}
		}
	}

	int pozitie_valoare = aloca_valoare();
	valori[pozitie_valoare] = v;
	urm_valoare[pozitie_valoare] = head_valori[pozitie_cheie];
	prec_valoare[pozitie_valoare] = -1;

	if (head_valori[pozitie_cheie] != -1)
	{
		prec_valoare[head_valori[pozitie_cheie]] = pozitie_valoare;
	}
	head_valori[pozitie_cheie] = pozitie_valoare;
}

vector<TValoare> MDO::cauta(TCheie c) const
{
	/* Cauta o cheie si returneaza vectorul de valori asociate
	 * Preconditii: -
	 * Complexitate: O(n)
	 * Parametrii: c - cheia de cautat
	 * Returneaza: vectorul de valori asociate cheii c
	 */
	vector<TValoare> valori_gasite;

	int pozitie_cheie = -1;
	for (int i = prim_cheie; i  != -1; i = urm_cheie[i])
	{
		if (chei[i] == c)
		{
			pozitie_cheie = i;
			break;
		}
	}
	if (pozitie_cheie == -1)
	{
		return valori_gasite;
	}
	int pozitie_valoare = head_valori[pozitie_cheie];
	while (pozitie_valoare != -1)
	{
		valori_gasite.push_back(valori[pozitie_valoare]);
		pozitie_valoare = urm_valoare[pozitie_valoare];
	}
	return valori_gasite;
}

bool MDO::sterge(TCheie c, TValoare v) {
	/* Sterge o cheie si o valoare
	 * Preconditii: -
	 * Complexitate: O(n)
	 * Parametrii: c - cheia de sters
	 *			   v - valoarea de sters
	 * Returneaza: true daca s-a gasit cheia si valoarea de sters, false altfel
	 */
	int pozitie_cheie = -1;
	for (int i = prim_cheie; i != -1; i = urm_cheie[i])
	{
		if (chei[i] == c)
		{
			pozitie_cheie = i;
			break;
		}
	}
	if (pozitie_cheie == -1)
	{
		return false;
	}

	int pozitie_valoare = head_valori[pozitie_cheie];
	int pozitie_anterioara = -1;
	while (pozitie_valoare != -1)
	{
		if (valori[pozitie_valoare] == v)
		{
			if (pozitie_anterioara != -1)
			{
				urm_valoare[pozitie_anterioara] = urm_valoare[pozitie_valoare];
			}
			else
			{
				head_valori[pozitie_cheie] = urm_valoare[pozitie_valoare];
			}
			dealoca_valoare(pozitie_valoare);
			if (head_valori[pozitie_cheie] == -1)
			{
				if (prec_cheie[pozitie_cheie] != -1)
				{
					urm_cheie[prec_cheie[pozitie_cheie]] = urm_cheie[pozitie_cheie];
				}
				else
				{
					prim_cheie = urm_cheie[pozitie_cheie];
				}
				if (urm_cheie[pozitie_cheie] != -1)
				{
					prec_cheie[urm_cheie[pozitie_cheie]] = prec_cheie[pozitie_cheie];
				}
				dealoca_cheie(pozitie_cheie);
			}
			return true;
		}
		pozitie_anterioara = pozitie_valoare;
		pozitie_valoare = urm_valoare[pozitie_valoare];
	}
	return false;
}

int MDO::dim() const {
	/* Returneaza numarul de perechi (cheie, valoare) din MDO
	 * Preconditii: -
	 * Complexitate: O(n) (unde n = max(numarul de chei, numarul de valori))
	 * Returneaza: numarul de perechi (cheie, valoare) din MDO
	 */
	int count = 0;
	for (int i = prim_cheie; i != -1; i = urm_cheie[i])
	{
		int pozitie_valoare = head_valori[i];
		while (pozitie_valoare != -1)
		{
			count++;
			pozitie_valoare = urm_valoare[pozitie_valoare];
		}
	}
	return count;
}

bool MDO::vid() const {
	/* Verifica daca MultiDictionarul Ordonat e vid
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Returneaza: true daca MultiDictionarul Ordonat e vid, false altfel
	 */
	return prim_cheie == -1;
}

IteratorMDO MDO::iterator() const
{
	/* Se returneaza iterator pe MDO
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 * Returneaza: iteratorul pe MDO
	 */
	return IteratorMDO(*this);
}

vector <TCheie> MDO::multimeaCheilor() const
{
	/* Returneaza vectorul de chei din MDO
	 * Preconditii: mdo - multidictionar ordonat
	 * Complexitate caz favorabil = Complexitate caz defavorabil = Complexitate medie = θ(n) (theta)
	 * Returneaza: vectorul de chei din MDO
	 */

	/* subalgoritm multimeaCheilor(MDO m)
	 * i <- mdo.prim_cheie
	 * cat_timp i != -1 executa
	 *    @adauga_cheie
	 *    i <- mdo.urm_cheie[i]
	 * sf_cat_timp
	 * @returneaza chei
	 * sf_subalgoritm
	 */
	vector<TCheie> chei_gasite;
	for (int i = prim_cheie; i != -1; i = urm_cheie[i])
	{
		chei_gasite.push_back(chei[i]);
	}
	return chei_gasite;
}

MDO::~MDO()
{
	/* Destructorul MultiDictionarului Ordonat
	 * Preconditii: -
	 * Complexitate: θ(1) (theta)
	 */
	delete[] chei;
	delete[] urm_cheie;
	delete[] prec_cheie;
	delete[] head_valori;
	delete[] valori;
	delete[] urm_valoare;
	delete[] prec_valoare;
}