#include <stdio.h>
#include "tests.h"
#include <stdlib.h>
#include "controller.h"
#include "oferta.h"

void printMenu() {
	/* Printeaza meniul */
    printf("1. Adaugare oferta\n2. Actualizare oferta\n");
    printf("3. Sterge oferta\n4. Vizualizare oferte ordonat dupa pret, destinatie (crescator/descrescator)\n");
    printf("5. Vizualizare oferte filtrate dupa un criteriu (destinatie, tip, pret, data)\n6. Tipareste toate\n7. Undo\n0. Iesire\n");
}

void testAll() {
	/* Ruleaza toate testele */
	testCopyList();
    testAddService();
    testFindService();
    testModifyService();
    testDeleteService();
    testdestFilter();
    testtipFilter();
    testpretFilter();
	testdataFilter();
    testSortByPrice();
    testSortByDestination();
    testCreateOferta();
    testEsteDataValida();
    testValideazaOferta();
    testResize();
	testUndo();
	testSortPret();
}

void printAllOferte(MyList* v) {
	/* Afiseaza toate ofertele
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 */
    if (v->length == 0)
        printf("Nu exista oferte.\n");
    else {
        printf("Ofertele curente sunt:\n");
        for (int i = 0; i < size(v); i++)
        {
            Oferta* o = get(v, i);
            printf("ID: %d | Tip: %s | Destinatia: %s | Data Plecare : %s | Pret: %d\n",
                o->id, o->tip, o->destinatia, o->data_plecare, o->pret);
        }
    }
}

void uiAdd(MyList* v, MyList* undo_list) {
	/* Adauga o oferta
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * undo_list - lista de oferte anterioara (MyList*).
	 */
    int id, pret;
    char* tip = (char*)malloc(30 * sizeof(char));
    char* destinatia = (char*)malloc(30 * sizeof(char));
    char* data_plecare = (char*)malloc(30 * sizeof(char));

    if (tip == NULL || destinatia == NULL || data_plecare == NULL) {
        printf("Eroare la alocarea memoriei.\n");
        return;
    }

    printf("ID-ul ofertei este: ");
    scanf_s("%d", &id);

    printf("Tipul ofertei este (munte, mare, citybreak): ");
    scanf_s(" %29[^\n]", tip, 30);  // Citirea unui string cu spații
    while (getchar() != '\n');

    printf("Destinatia este: ");
    scanf_s("%29s", destinatia, 30);

    printf("Data plecarii este: ");
    scanf_s("%29s", data_plecare, 30);

    printf("Pretul este: ");
    scanf_s("%d", &pret);
    while (getchar() != '\n');
    int succes = addOferta(v, undo_list, id, tip, destinatia, data_plecare, pret);

    if (succes == 1) {
        printf("Oferta adaugata cu succes.\n");
    }
    else {
        if (succes == 0)
			printf("Eroare: ID-ul ofertei trebuie sa fie unic.\n");
		else
        if (succes == 2)
            printf("Eroare: Tipul ofertei trebuie sa fie 'city break', 'munte' sau 'mare'.\n");
        else if (succes == 3)
            printf("Eroare: Destinatia nu poate fi goala.\n");
        else if (succes == 4)
            printf("Eroare: Data trebuie sa fie în formatul 'dd.mm.yyyy' si sa fie valida.\n");
        else if (succes == 5)
            printf("Eroare: Pretul trebuie sa fie un numar pozitiv.\n");
    }

    free(tip);
    free(destinatia);
    free(data_plecare);
}

void uiModify(MyList* v, MyList* undo_list) {
	/* Modifica o oferta
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * undo_list - lista de oferte anterioara (MyList*).
	 */
    int id, pret_nou;
    char* tip_nou = (char*)malloc(30 * sizeof(char));
    char* destinatia_noua = (char*)malloc(30 * sizeof(char));
    char* data_plecare_noua = (char*)malloc(30 * sizeof(char));

    printf("Introduceti id-ul ofertei pentru modificare: ");
    scanf_s("%d", &id);

    printf("Tipul ofertei este (munte, mare, city break): ");
    scanf_s(" %29[^\n]", tip_nou, 30);  // Citirea unui string cu spații
    while (getchar() != '\n');

    printf("Destinatia este: ");
    scanf_s("%29s", destinatia_noua, 30);

    printf("Data plecarii este: ");
    scanf_s("%29s", data_plecare_noua, 30);

    printf("Pretul este: ");
    scanf_s("%d", &pret_nou);
    while (getchar() != '\n');

    int succes = modifyOferta(v, undo_list, id, tip_nou, destinatia_noua, data_plecare_noua, pret_nou);
    if (succes == 1)
    {
        printf("Oferta modificata cu succes.\n");
    }
	else if (succes == 0)
    {
        printf("ID invalid.\n");
    }
	else if (succes == 2)
	{
		printf("Eroare: Tipul ofertei trebuie sa fie 'city break', 'munte' sau 'mare'.\n");
	}
	else if (succes == 3)
	{
		printf("Eroare: Destinatia nu poate fi goala.\n");
	}
	else if (succes == 4)
	{
		printf("Eroare: Data trebuie sa fie in formatul 'dd.mm.yyyy' si sa fie valida.\n");
	}
	else if (succes == 5)
	{
		printf("Eroare: Pretul trebuie sa fie un numar pozitiv.\n");
	}

	free(tip_nou);
	free(destinatia_noua);
	free(data_plecare_noua);
}

void uiDelete(MyList* v, MyList* undo_list) {
	/* Sterge o oferta
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * undo_list - lista de oferte anterioara (MyList*).
	 */
    int id;
    printf("Introduceti id-ul ofertei pe care doriti sa o stergeti: ");
    scanf_s("%d", &id);
    while (getchar() != '\n');

    int succes = deleteOferta(v, undo_list, id);
    if (succes)
        printf("Oferta stearsa cu succes.\n");
    else
        printf("ID invalid.\n");
}

void uiSort(MyList* v) {
	/* Sorteaza ofertele dupa un criteriu si o ordine specificata
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 */
    int criteriu, ordine;

    printf("Alegeti criteriul de sortare:\n1. Pret\n2. Destinatie\n");
    scanf_s("%d", &criteriu);

    printf("Alegeti ordinea:\n1. Crescator\n2. Descrescator\n");
    scanf_s("%d", &ordine);
    while (getchar() != '\n');

    if ((criteriu != 1 && criteriu != 2) || (ordine != 1 && ordine != 2)) {
        printf("Optiune invalida!\n");
        return;
    }

    sortOferte(v, criteriu, ordine);
    printf("Sortare realizata cu succes!\n");
    printAllOferte(v);
}

void uiFilter(MyList* v) {
	/* Filtreaza ofertele dupa un criteriu specificat
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 */
    int t, pret;
    char tip[30], destinatia[30];

    printf("Alegeti tipul filtrarii (1.Destinatia | 2.Tip | 3.Pret | 4. Data): ");
    scanf_s("%d", &t);
    while (getchar() != '\n');

    if (t == 1) {
        printf("Destinatia este: ");
        scanf_s("%29s", destinatia, (unsigned)_countof(destinatia));
        MyList* filteredList = destFilter(v, destinatia);
        printAllOferte(filteredList);
    }
    else if (t == 2) {
        printf("Tipul este: ");
        scanf_s("%29[^\n]", tip, (unsigned)_countof(tip));
        MyList* filteredList = tipFilter(v, tip);
        printAllOferte(filteredList);
    }
    else if (t == 3) {
        printf("Pretul este: ");
        scanf_s("%d", &pret);
        MyList* filteredList = pretFilter(v, pret);
        printAllOferte(filteredList);
    }
	else if (t == 4) {
		printf("Data plecarii este: ");
		char data[30];
		scanf_s("%29s", data, (unsigned)_countof(data));
		MyList* filteredList = dataFilter(v, data);
		printAllOferte(filteredList);
	}
	else {
		printf("Optiune invalida!\n");
	}
}

void run() {
	/* Ruleaza meniul */
    MyList* oferteList = createEmpty();
	MyList* undo_list = createEmpty();
    int running = 1;
    while (running) {
        printMenu();
        char cmd;
        printf("Comanda este: ");
        scanf_s("%c", &cmd, 1);
        while (getchar() != '\n');
        switch (cmd) {
        case '1':
            uiAdd(oferteList, undo_list);
            break;
        case '2':
            uiModify(oferteList, undo_list);
            break;
        case '3':
            uiDelete(oferteList, undo_list);
            break;
        case '5':
            uiFilter(oferteList);
            break;
        case '4':
            uiSort(oferteList);
            break;
        case '6':
            printAllOferte(oferteList);
            break;
		case '7':
			int rez = undo(&oferteList, undo_list);
            if (rez == 0)
            {
                printf("Nu sunt operatii precedente.\n");
            }
            else
            {
                printf("Undo reusit\n");
            }
			break;
        case '0':
            running = 0;
            destroy(oferteList, (void (*)(void*))destroyOferta);
            destroy(undo_list, destroyOfertaLista);
            break;
        default:
            printf("Comanda invalida!\n");
        }
    }
}
