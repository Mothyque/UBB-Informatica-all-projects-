#include "repo.h"
#include <stdlib.h>
#include "oferta.h"

MyList* createEmpty() {
	/* Creează o listă goală
	 * Returnează:
	 * noul obiect de tip MyList
     */
    MyList* v = (MyList*)malloc(sizeof(MyList));
    v->length = 0;
    v->capacity = 10;
	v->elems = (ElemType*)malloc(sizeof(ElemType) * (size_t)v->capacity);
    return v;
}

void destroy(MyList* v, void(*destroyELem)(ElemType)) {
	/* Distruge lista și eliberează memoria
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * *destroyElem - pointer la funcția de distrugere a elementelor din listă
	 */
    if (v != NULL)
    {
		for (int i = 0; i < v->length; i++) {
			destroyELem(v->elems[i]);
		}
		free(v->elems);
		free(v);
    }
}

void destroyOfertaLista(void* l) {
	/* Distruge o listă de oferte și eliberează memoria alocată
	 * Parametri:
	 * l - pointer la lista de oferte (MyList*)
	 */
    if (l != NULL) {
        destroy((MyList*)l, (void (*)(void*))destroyOferta);
    }
}



ElemType get(MyList* v, int poz) {
	/* Returnează elementul de pe poziția 'poz'
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * poz - poziția elementului (int).
	 * Returnează:
	 * elementul de pe poziția 'poz'
	 */
    return v->elems[poz];
}

ElemType setElem(MyList* v, int poz, ElemType el) {
	/* Pune un element pe poziția 'poz'
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * poz - poziția elementului (int).
	 * el - elementul de adăugat (ElemType).
	 * Returnează:
	 * elementul înlocuit
	 */
    ElemType replacedElement = v->elems[poz];
    v->elems[poz] = el;
    return replacedElement;
}

int size(MyList* v) {
	/* Returnează numărul de elemente în listă
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * Returnează:
	 * lungimea listei
	 */
    return v->length;
}

void resize(MyList* v) {
	/* Redimensionează lista dublând capacitatea
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 */
    v->capacity *= 2;
    v->elems = (ElemType*)realloc(v->elems, sizeof(ElemType) * (size_t)v->capacity);
}

void add(MyList* v, ElemType el) {
	/* Adaugă un element în listă
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * el - elementul de adăugat (ElemType).
	 */
    if (v->length == v->capacity) {
        resize(v);
    }
    v->elems[v->length] = el;
    v->length++;
}

ElemType delete(MyList* v, int poz) {
	/* Șterge un element de pe poziția 'poz'
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * poz - poziția elementului de șters (int).
	 * Returnează:
	 * elementul șters
	 */
    ElemType deleted = v->elems[poz];
    for (int i = poz; i < v->length - 1; i++) {
        v->elems[i] = v->elems[i + 1];
    }
    v->length--;
    return deleted;
}

MyList* copyList(MyList* v, ElemType (*copyElem)(ElemType)) {
	/* Creează o copie a listei
	 * Parametri:
	 * v - pointer la lista de oferte (MyList*).
	 * *copyElem - pointer la funcția de copiere a elementelor din listă
	 * Returnează:
	 * copia listei
	 */
	MyList* newList = createEmpty();
	for (int i = 0; i < v->length; i++) {
		add(newList, copyElem(v->elems[i]));
	}
	return newList;
}

