#include "tests.h"
#include <assert.h>
#include <stdio.h>
#include <string.h>
#include "controller.h"
#include "repo.h"
#include "oferta.h"

void testCopyList() {
	/* Testează funcția de copiere a unei liste de oferte */
	MyList* v1 = createEmpty();
	add(v1, createOferta(1, "munte", "Paris", "10.02.2026", 15000));
	add(v1, createOferta(2, "mare", "Roma", "10.02.2026", 18000));

	assert(size(v1) == 2);
	MyList* v2 = copyList(v1, copyOferta); 

	assert(size(v2) == 2);
	Oferta* o = get(v2, 0);
	assert(strcmp(o->data_plecare, "10.02.2026") == 0);

	destroy(v1, (void (*)(void*))destroyOferta);
	destroy(v2, (void (*)(void*))destroyOferta);
}

void testAddService() {
	/* Testează funcția de adăugare a unei oferte */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	assert(addOferta(v, undo_list, 1, "city break", "Paris", "10.02.2026", 15000) == 1);
	assert(addOferta(v, undo_list, 1, "city break", "Beijing", "20.12.2027", 12000) == 0);

	assert(size(v) == 1);
	Oferta* o = get(v, 0);

	assert(o->id == 1);
	assert(strcmp(o->tip, "city break") == 0);
	assert(strcmp(o->destinatia, "Paris") == 0);
	assert(strcmp(o->data_plecare, "10.02.2026") == 0);
	assert(o->pret == 15000);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testFindService() {
	/* Testează funcția de găsire a unei oferte */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	int succes1 = addOferta(v, undo_list, 1, "munte", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);

	int succes2 = addOferta(v, undo_list, 2, "mare", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);

	int succes3 = addOferta(v, undo_list, 3, "city break", "Paris", "10.02.2026", 15000);
	assert(succes3 == 1);

	assert(size(v) == 3);
	int poz = findOferta(v, 2);

	assert(poz == 1);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testModifyService() {
	/* Testează funcția de modificare a unei oferte */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	int succes1 = addOferta(v, undo_list, 1, "munte", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);

	int succes2 = addOferta(v, undo_list, 2, "mare", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);

	int succes3 = addOferta(v, undo_list, 3, "city break", "Paris", "10.02.2026", 15000);
	assert(succes3 == 1);

	assert(size(v) == 3);
	int mod_of1 = modifyOferta(v, undo_list, 2, "mare", "Roma", "11.02.2026", 18000);
	assert(mod_of1 == 1);

	int mod_of2 = modifyOferta(v, undo_list, 4, "mare", "Roma", "11.02.2026", 18000);
	assert(mod_of2 == 0);

	int mod_of3 = modifyOferta(v, undo_list, 1, "skibidi", "Roma", "11.02.2026", 18000);
	assert(mod_of3 == 2);

	int mod_of4 = modifyOferta(v, undo_list, 1, "mare", "", "11.02.2026", 18000);
	assert(mod_of4 == 3);

	int mod_of5 = modifyOferta(v, undo_list, 1, "mare", "Roma", "11.25.2026", 18000);
	assert(mod_of5 == 4);

	int mod_of6 = modifyOferta(v, undo_list, 1, "mare", "Roma", "11.02.2026", -18000);
	assert(mod_of6 == 5);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testDeleteService() {
	/* Testează funcția de ștergere a unei oferte */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	int succes1 = addOferta(v, undo_list, 1, "munte", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);

	int succes2 = addOferta(v, undo_list, 2, "mare", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);

	int succes3 = addOferta(v, undo_list, 3, "city break", "Paris", "10.02.2026", 15000);
	assert(succes3 == 1);

	assert(size(v) == 3);
	int succes_del1 = deleteOferta(v, undo_list, 2);
	assert(succes_del1 == 1);
	int succes_del2 = deleteOferta(v, undo_list, 5);
	assert(succes_del2 == 0);
	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testdestFilter() {
	/* Testează funcția de filtrare a ofertelor după destinație */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	int succes1 = addOferta(v, undo_list, 1, "munte", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);

	int succes2 = addOferta(v, undo_list, 2, "mare", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);

	int succes3 = addOferta(v, undo_list, 3, "city break", "Medias", "10.02.2026", 15000);
	assert(succes3 == 1);

	assert(size(v) == 3);
	MyList* filteredList = destFilter(v, "Paris");
	assert(size(filteredList) == 2);
	destroy(filteredList, (void (*)(void*))destroyOferta);

	filteredList = destFilter(v, "");
	assert(size(filteredList) == 3);
	destroy(filteredList, (void (*)(void*))destroyOferta);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void  testtipFilter() {
	/* Testează funcția de filtrare a ofertelor după tip */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();

	int succes1 = addOferta(v, undo_list, 1, "munte", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);

	int succes2 = addOferta(v, undo_list, 2, "mare", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);

	int succes3 = addOferta(v, undo_list, 3, "munte", "Medias", "10.02.2026", 15000);
	assert(succes3 == 1);

	assert(size(v) == 3);
	MyList* filteredList = tipFilter(v, "munte");
	assert(size(filteredList) == 2);
	destroy(filteredList, (void (*)(void*))destroyOferta);

	filteredList = tipFilter(v, "");
	assert(size(filteredList) == 3);
	destroy(filteredList, (void (*)(void*))destroyOferta);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testpretFilter() {
	/* Testează funcția de filtrare a ofertelor după preț */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();

	int succes1 = addOferta(v, undo_list, 1, "mare", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);

	int succes2 = addOferta(v, undo_list, 2, "munte", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);

	int succes3 = addOferta(v, undo_list, 3, "city break", "Paris", "10.02.2026", 158000);
	assert(succes3 == 1);

	assert(size(v) == 3);
	MyList* filteredList = pretFilter(v, 15000);
	assert(size(filteredList) == 2);
	destroy(filteredList, (void (*)(void*))destroyOferta);

	filteredList = pretFilter(v, 0);
	assert(size(filteredList) == 3);
	destroy(filteredList, (void (*)(void*))destroyOferta);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testdataFilter()
{
	/* Testează funcția de filtrare a ofertelor după dată */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	int succes1 = addOferta(v, undo_list, 1, "mare", "Paris", "10.02.2026", 15000);
	assert(succes1 == 1);
	int succes2 = addOferta(v, undo_list, 2, "munte", "Paris", "10.02.2026", 15000);
	assert(succes2 == 1);
	int succes3 = addOferta(v, undo_list, 3, "city break", "Paris", "10.02.2026", 158000);
	assert(succes3 == 1);
	assert(size(v) == 3);
	MyList* filteredList = dataFilter(v, "10.02.2026");
	assert(size(filteredList) == 3);
	destroy(filteredList, (void (*)(void*))destroyOferta);
	filteredList = dataFilter(v, "10.02.2027");
	assert(size(filteredList) == 0);

	filteredList = dataFilter(v, "");
	assert(size(filteredList) == 3);
	for (int i = 0; i < filteredList->length; i++)
	{
		Oferta* o = get(filteredList, i);
		assert(strcmp(o->data_plecare, "10.02.2026") == 0);
	}
	destroy(filteredList, (void (*)(void*))destroyOferta);
	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testSortByPrice() {
	/* Testează funcția de sortare a ofertelor după preț */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	addOferta(v, undo_list, 1, "mare", "Mamaia", "12.06.2025", 500);
	addOferta(v, undo_list, 2, "munte", "Sinaia", "15.07.2025", 300);
	addOferta(v, undo_list, 3, "city break", "Paris", "20.08.2025", 700);

	sortOferte(v, 1, 1); // Sortare crescătoare după preț
	assert(((Oferta*)get(v, 0))->pret == 300);
	assert(((Oferta*)get(v, 1))->pret == 500);
	assert(((Oferta*)get(v, 2))->pret == 700);

	sortOferte(v, 1, 2); // Sortare descrescătoare după preț
	assert(((Oferta*)get(v, 0))->pret == 700);
	assert(((Oferta*)get(v, 1))->pret == 500);
	assert(((Oferta*)get(v, 2))->pret == 300);


	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testSortByDestination() {
	/* Testează funcția de sortare a ofertelor după destinație */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	addOferta(v, undo_list, 1, "mare", "Mamaia", "12.06.2025", 500);
	addOferta(v, undo_list, 2, "munte", "Sinaia", "15.07.2025", 300);
	addOferta(v, undo_list, 3, "city break", "Paris", "20.08.2025", 700);

	sortOferte(v, 2, 1); // Sortare crescătoare după destinație
	assert(strcmp(((Oferta*)get(v, 0))->destinatia, "Mamaia") == 0);
	assert(strcmp(((Oferta*)get(v, 1))->destinatia, "Paris") == 0);
	assert(strcmp(((Oferta*)get(v, 2))->destinatia, "Sinaia") == 0);

	sortOferte(v, 2, 2); // Sortare descrescătoare după destinație
	assert(strcmp(((Oferta*)get(v, 0))->destinatia, "Sinaia") == 0);
	assert(strcmp(((Oferta*)get(v, 1))->destinatia, "Paris") == 0);
	assert(strcmp(((Oferta*)get(v, 2))->destinatia, "Mamaia") == 0);

	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}


void testCreateOferta() {
	/* Testează funcția de creare a unei oferte */
	Oferta* o = createOferta(1, "mare", "Barcelona", "12.07.2025", 500);
	assert(o->id == 1);
	assert(strcmp(o->tip, "mare") == 0);
	assert(strcmp(o->destinatia, "Barcelona") == 0);
	assert(strcmp(o->data_plecare, "12.07.2025") == 0);
	assert(o->pret == 500);

	destroyOferta(o);
}

void testEsteDataValida() {
	/* Testează funcția de validare a unei date */
	assert(esteDataValida("01-01-2000") == 0); 
	assert(esteDataValida("01/01/2000") == 0); 
	assert(esteDataValida("01.01.20") == 0);   
	assert(esteDataValida("01012000") == 0);   
	assert(esteDataValida("01..01.2000") == 0); 
	assert(esteDataValida(".01.01.2000") == 0); 
	assert(esteDataValida("01.01.2000.") == 0); 
	assert(esteDataValida("32.01.2000") == 0); 
	assert(esteDataValida("00.01.2000") == 0); 
	assert(esteDataValida("15.13.2000") == 0); 
	assert(esteDataValida("15.00.2000") == 0); 
	assert(esteDataValida("15.06.1899") == 0); 
	assert(esteDataValida("15.06.2101") == 0); 

	assert(esteDataValida("29.02.2000") == 1); 
	assert(esteDataValida("28.02.2001") == 1); 
	assert(esteDataValida("31.01.2000") == 1); 
	assert(esteDataValida("30.04.2000") == 1); 
	assert(esteDataValida("31.04.2000") == 0); 
	assert(esteDataValida("29.02.1900") == 0); 
	assert(esteDataValida("29.02.2004") == 1); 

	assert(esteDataValida("01.01.1900") == 1); 
	assert(esteDataValida("31.12.2100") == 1); 
}

void testValideazaOferta() {
	/* Testează funcția de validare a unei oferte */
	Oferta* o1 = createOferta(1, "mare", "Barcelona", "12.07.2025", 500);
	assert(valideazaOferta(o1) == 1);
	destroyOferta(o1);

	Oferta* o2 = createOferta(2, "oras", "Paris", "15.08.2025", 600); // Tip invalid
	assert(valideazaOferta(o2) == 2);
	destroyOferta(o2);

	Oferta* o3 = createOferta(3, "munte", "", "20.09.2025", 400); // Destinatie goala
	assert(valideazaOferta(o3) == 3);
	destroyOferta(o3);

	Oferta* o4 = createOferta(4, "city break", "Londra", "40.10.2025", 700); // Data invalida
	assert(valideazaOferta(o4) == 4);
	destroyOferta(o4);

	Oferta* o5 = createOferta(5, "mare", "Ibiza", "10.06.2025", -100); // Pret invalid
	assert(valideazaOferta(o5) == 5);
	destroyOferta(o5);
}

void testResize() {
	/* Testează funcția de redimensionare a listei */
	MyList* v = createEmpty();

	for (int i = 0; i < 20; i++) {
		Oferta* o = createOferta(i, "city break", "Paris", "10.02.2026", 15000);
		add(v, o);
	}

	assert(v->capacity == 20); 

	assert(size(v) == 20);

	for (int i = 0; i < 20; i++) {
		Oferta* o = get(v, i);
		assert(o->id == i);
		assert(strcmp(o->tip, "city break") == 0);
		assert(strcmp(o->destinatia, "Paris") == 0);
		assert(strcmp(o->data_plecare, "10.02.2026") == 0);
		assert(o->pret == 15000);
	}
	destroy(v, (void (*)(void*))destroyOferta);
}

void testUndo()
{
	/* Testează funcția de undo */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();

	assert(undo(&v, undo_list) == 0);

	addOferta(v, undo_list, 1, "mare", "Mamaia", "12.06.2025", 500);
	addOferta(v, undo_list, 2, "munte", "Sinaia", "15.07.2025", 300);
	addOferta(v, undo_list, 3, "city break", "Paris", "20.08.2025", 700);

	assert(undo(&v, undo_list) == 1);
	assert(size(v) == 2);

	Oferta* o1 = get(v, 0);
	assert(o1->id == 1);
	assert(strcmp(o1->tip, "mare") == 0);
	assert(strcmp(o1->destinatia, "Mamaia") == 0);
	assert(strcmp(o1->data_plecare, "12.06.2025") == 0);
	assert(o1->pret == 500);
	Oferta* o2 = get(v, 1);
	assert(o2->id == 2);
	assert(strcmp(o2->tip, "munte") == 0);
	assert(strcmp(o2->destinatia, "Sinaia") == 0);
	assert(strcmp(o2->data_plecare, "15.07.2025") == 0);
	assert(o2->pret == 300);
	destroy(v, (void (*)(void*))destroyOferta);
	destroy(undo_list, destroyOfertaLista);
}

void testSortPret()
{
	/* Testează funcția de sortare a ofertelor după preț in treimi */
	MyList* v = createEmpty();
	MyList* undo_list = createEmpty();
	addOferta(v, undo_list, 1,"mare", "skibidi", "12.02.2025", 10000);
	addOferta(v, undo_list, 2, "munte", "skibidi", "12.02.2025", 9000);
	addOferta(v, undo_list, 3, "city break", "skibidi", "12.02.2025", 8000);
	addOferta(v, undo_list, 4, "mare", "skibidi", "12.02.2025", 7000);
	addOferta(v, undo_list, 5, "munte", "skibidi", "12.02.2025", 6000);
	addOferta(v, undo_list, 6, "city break", "skibidi", "12.02.2025", 5000);
	addOferta(v, undo_list, 7, "mare", "skibidi", "12.02.2025", 4000);
	addOferta(v, undo_list, 8, "munte", "skibidi", "12.02.2025", 3000);
	addOferta(v, undo_list, 9, "city break", "skibidi", "12.02.2025", 2000);
	addOferta(v, undo_list, 10, "mare", "skibidi", "12.02.2025", 1000);

	MyList* lista_sortata = createEmpty();
	lista_sortata = sortPret(v);

	assert(size(lista_sortata) == 3);
	MyList* lista1 = createEmpty();
	lista1 = get(lista_sortata, 0);
	assert(size(lista1) == 3);
	MyList* lista2 = createEmpty();
	lista2 = get(lista_sortata, 1);
	assert(size(lista2) == 3);
	MyList* lista3 = createEmpty();
	lista3 = get(lista_sortata, 2);
	assert(size(lista3) == 4);

	Oferta* verif = get(lista1, 0);
	assert(verif->pret == 1000);
	verif = get(lista1, 1);
	assert(verif->pret == 2000);
	verif = get(lista1, 2);
	assert(verif->pret == 3000);
	verif = get(lista2, 0);
	assert(verif->pret == 4000);
	verif = get(lista2, 1);
	assert(verif->pret == 5000);
	verif = get(lista2, 2);
	assert(verif->pret == 6000);
	verif = get(lista3, 0);
	assert(verif->pret == 7000);
	verif = get(lista3, 1);
	assert(verif->pret == 8000);
	verif = get(lista3, 2);
	assert(verif->pret == 9000);
	verif = get(lista3, 3);
	assert(verif->pret == 10000);

}