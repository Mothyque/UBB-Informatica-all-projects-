#include "controller.h"
#include <string.h>
#include <stdlib.h>

#include "oferta.h"
#include "repo.h"

int addOferta(MyList* v, MyList* undo_list, int id, char* tip, char* destinatia, char* data_plecare, int pret) {
	/*
	*Creeaza o oferta noua si o adauga in lista v daca este valida.
	Parametri:
	v – pointer catre lista de oferte (MyList*).
	id – identificator unic al ofertei (int).
	tip – tipul ofertei (mare,munte sau city break) (char*).
	destinatia – locatia ofertei (char*).
	data_plecare – data plecarii (char*).
	pret – pretul ofertei (int).
	Returneaza:
	1 daca oferta a fost adaugata cu succes.
	0 daca oferta este invalida si nu a fost adaugata.
	 */
	add(undo_list, copyList(v, copyOferta));
	if (findOferta(v, id) != -1)
	{
		return 0;
	}
	Oferta* o = createOferta(id, tip, destinatia, data_plecare, pret);
	int c = valideazaOferta(o);
	if (c == 1)
	{
		add(v, o);
	}
	return c;
}

int findOferta(MyList* v, int id) {
	/*
	*Cauta o oferta cu id-ul specificat in lista v.
	Parametri:
	v – pointer catre lista de oferte (MyList*).
	id – identificatorul unic al ofertei cautate (int).
	Returneaza:
	Pozitia ofertei in lista (int) daca este gasita.
	-1 daca oferta nu exista in lista.
	 */
	int poz_to_delete = -1;
	for (int i = 0; i < v->length; i++) {
		Oferta* o = get(v, i);
		if (id == o->id) {
			poz_to_delete = i;
			break;
		}
	}
	return poz_to_delete;
}

int modifyOferta(MyList* v, MyList* undo_list, int id, char* tip_nou, char* destinatia_noua, char* data_plecare_noua, int pret_nou) {
	/*
	* Modifica datele unei oferte existente cu id-ul specificat.
	Parametri:
	v – pointer catre lista de oferte (MyList*).
	id – identificatorul unic al ofertei care trebuie modificata (int).
	tip_nou – noul tip al ofertei (char*).
	destinatia_noua – noua destinatie (char*).
	data_plecare_noua – noua data de plecare (char*).
	pret_nou – noul pret al ofertei (int).
	Returneaza:
	1 daca modificarea a fost realizata cu succes.
	0 daca oferta nu a fost gasita si nu s-a modificat nimic.
	 */
	add(undo_list, copyList(v, copyOferta));
	int poz_to_delete = findOferta(v, id);
	if (poz_to_delete != -1) {
		Oferta* ofertaNoua = createOferta(id, tip_nou, destinatia_noua, data_plecare_noua, pret_nou);
		int valid = valideazaOferta(ofertaNoua);
		if (valid != 1)
		{
			return valid;
		}
		Oferta* o = get(v, poz_to_delete);
		destroyOferta(o);
		setElem(v, poz_to_delete, ofertaNoua);
		return 1;
	}
	return 0;
}

int deleteOferta(MyList* v, MyList* undo_list, int id) {
	/*
	*Sterge o oferta cu id-ul dat din lista v, daca exista.
	Parametri:
	v – pointer catre lista de oferte (MyList*).
	id – identificatorul unic al ofertei de sters (int).
	Returneaza:
	1 daca oferta a fost stearsa cu succes.
	0 daca oferta nu a fost gasita.
	 */
	add(undo_list, copyList(v, copyOferta));
	int poz_to_delete = findOferta(v, id);
	if (poz_to_delete != -1) {
		Oferta* o = delete(v, poz_to_delete);
		destroyOferta(o);
		return 1;
	}
	return 0;
}

MyList* destFilter(MyList* v, char* destinatia) {
	/*
	* Creeaza si returneaza o noua lista de oferte care au aceeasi destinatie ca cea specificata.
	*
	* Parametri:
	* v – pointer catre lista de oferte (MyList*).
	* destinatia – destinatia pe care dorim sa o filtram (char*).
	*
	* Returneaza:
	* O lista noua care contine doar ofertele cu destinatia respectiva.
	* O copie a listei initiale daca destinatia este NULL sau goala.
	*/

	if (destinatia == NULL || strcmp(destinatia, "") == 0) {
		return copyList(v, (ElemType(*)(ElemType))copyOferta);
	}

	MyList* filteredList = createEmpty();

	for (int i = 0; i < v->length; i++) {
		Oferta* o = get(v, i);
		if (strcmp(o->destinatia, destinatia) == 0) {
			add(filteredList, createOferta(o->id, o->tip, o->destinatia, o->data_plecare, o->pret));
		}
	}

	return filteredList;
}


MyList* tipFilter(MyList* v, char* tip) {
	/*
	* Creeaza si returneaza o lista noua cu ofertele care au acelasi tip ca cel specificat.
	*
	* Parametri:
	* v – pointer catre lista de oferte (MyList*).
	* tip – tipul ofertei pentru filtrare (char*).
	*
	* Returneaza:
	* O lista noua care contine doar ofertele cu tipul specificat.
	*/

	if (strcmp(tip, "") == 0) {
		return copyList(v, (ElemType(*)(ElemType))copyOferta);
	}

	MyList* filteredList = createEmpty();

	for (int i = 0; i < v->length; i++) {
		Oferta* o = get(v, i);
		if (o != NULL && strcmp(o->tip, tip) == 0) {
			add(filteredList, createOferta(o->id, o->tip, o->destinatia, o->data_plecare, o->pret));
		}
	}

	return filteredList;
}


MyList* pretFilter(MyList* v, int pret) {
	/*
	* Creeaza si returneaza o lista noua cu ofertele care au acelasi pret ca cel specificat.
	*
	* Parametri:
	* v – pointer catre lista de oferte (MyList*).
	* pret – pretul ofertei pentru filtrare (int).
	*
	* Returneaza:
	* O lista noua care contine doar ofertele cu pretul specificat.
	* O copie a listei initiale daca pret este negativ sau zero.
	*/

	if (pret <= 0) {
		return copyList(v, (ElemType(*)(ElemType))copyOferta);
	}

	MyList* filteredList = createEmpty();

	for (int i = 0; i < v->length; i++) {
		Oferta* o = get(v, i);
		if (o != NULL && o->pret == pret) {
			add(filteredList, createOferta(o->id, o->tip, o->destinatia, o->data_plecare, o->pret));
		}
	}

	return filteredList;
}

MyList* dataFilter(MyList* v, char* data_plecare)
{
	/* Creeaza si returneaza o lista noua cu ofertele care au aceeasi data de plecare ca cea specificata.
	 * Parametri:
	 * v - pointer catre lista de oferte (MyList*).
	 * data_plecare - data de plecare pe care dorim sa o filtram (char*).
	 * Returneaza:
	 * O lista noua care contine doar ofertele cu data de plecare respectiva.
	 * O copie a listei initiale daca data_plecare este invalida sau goala.
	 */
	if (esteDataValida(data_plecare) == 0 || data_plecare == NULL) {
		return copyList(v, (ElemType(*)(ElemType))copyOferta);
	}
	MyList* filteredList = createEmpty();
	for (int i = 0; i < v->length; i++) {
		Oferta* o = get(v, i);
		if (strcmp(o->data_plecare, data_plecare) == 0) {
			add(filteredList, createOferta(o->id, o->tip, o->destinatia, o->data_plecare, o->pret));
		}
	}
	return filteredList;
}


void sortOferte(MyList* v, int criteriu, int ordine) {
	/* Sorteaza lista de oferte dupa un criteriu specificat.
	 * Parametri:
	 * v - pointer catre lista de oferte (MyList*).
	 * criteriu - criteriul de sortare (int).
	 * ordine - ordinea de sortare (int).
	 */
	for (int i = 0; i < v->length - 1; i++) {
		for (int j = i + 1; j < v->length; j++) {
			int conditie = 0;
			Oferta* o1 = get(v, i);
			Oferta* o2 = get(v, j);
			if (criteriu == 1) {
				conditie = (ordine == 1) ? (o1->pret > o2->pret) : (o1->pret < o2->pret);
			}
			else if (criteriu == 2) {
				conditie = (ordine == 1) ? (strcmp(o1->destinatia, o2->destinatia) > 0) : (strcmp(o1->destinatia, o2->destinatia) < 0);
			}
			if (conditie) {
				setElem(v, i, o2);
				setElem(v, j, o1);
			}
		}
	}
}



int undo(MyList** v, MyList* undo_list) {
	/* Functie de undo care revine la starea anterioara a listei de oferte.
	 * Parametri:
	 * v - pointer catre lista de oferte (MyList**).
	 * undo_list - lista de oferte anterioara (MyList*).
	 * Returneaza:
	 * 1 daca undo a fost realizat cu succes.
	 * 0 daca undo nu a fost realizat.
	 */
	if (size(undo_list) == 0) {
		return 0;
	}
	destroy(*v, (void (*)(void*))destroyOferta); 
	*v = (MyList*)delete(undo_list, size(undo_list) - 1); 
	return 1;
}

MyList* sortPret(MyList* v)
{
	/* Sorteaza ofertele dupa pret si le imparte in 3 liste egale.
  * Parametri:
  * v - pointer catre lista de oferte (MyList*).
  * Returneaza:
  * O lista noua care contine ofertele sortate dupa pret si impartite in 3 liste egale.
  */
	MyList* liste_sortate = createEmpty();
	MyList* lista_aux = copyList(v, (ElemType(*)(ElemType))copyOferta);
	sortOferte(lista_aux, 1, 1);
	int lungime = size(lista_aux);
	int l1, l2, l3;
	if (lungime % 3 == 0)
	{
		l1 = lungime / 3;
		l2 = l1;
		l3 = l1;
	}
	else if (lungime % 3 == 1)
	{
		l1 = lungime / 3;
		l2 = l1;
		l3 = l1 + 1;
	}
	else
	{
		l1 = lungime / 3;
		l2 = l1 + 1;
		l3 = l1 + 1;
	}

	MyList* list1 = createEmpty();
	MyList* list2 = createEmpty();
	MyList* list3 = createEmpty();

	for (int i = 0; i < l1; i++) 
	{
		add(list1, copyOferta(get(lista_aux, i)));
	}
	for (int i = l1; i < l1 + l2; i++) 
	{
		add(list2, copyOferta(get(lista_aux, i)));
	}
	for (int i = l1 + l2; i < lungime; i++) 
	{
		add(list3, copyOferta(get(lista_aux, i)));
	}

	add(liste_sortate, list1);
	add(liste_sortate, list2);
	add(liste_sortate, list3);

	destroy(lista_aux, (void (*)(void*))destroyOferta);
	return liste_sortate;
}
