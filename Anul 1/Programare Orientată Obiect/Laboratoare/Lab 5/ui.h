#pragma once
#include "repo.h"

// Afișează meniul principal
void printMenu();

// Rulează toate testele
void testAll();

// Afișează toate ofertele din listă
void printAllOferte(MyList* v);

// Adaugă o ofertă prin interfața utilizatorului
void uiAdd(MyList* v, MyList* undo_list);

// Modifică o ofertă prin interfața utilizatorului
void uiModify(MyList* v, MyList* undo_list);

// Șterge o ofertă prin interfața utilizatorului
void uiDelete(MyList* v, MyList* undo_list);

// Sortează ofertele (de implementat)
void uiSort(MyList* v);

// Filtrează ofertele în funcție de criterii (destinatie, tip, preț)
void uiFilter(MyList* v);

// Rulează aplicația
void run();
