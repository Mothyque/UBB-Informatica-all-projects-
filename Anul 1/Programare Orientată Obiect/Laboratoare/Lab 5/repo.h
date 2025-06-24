#pragma once

typedef void* ElemType;

typedef struct {
    ElemType* elems;
    int length;
    int capacity;
} MyList;

// Creează o listă goală
MyList* createEmpty();

// Distruge lista și eliberează memoria
void destroy(MyList* v, void(*destroyElem)(ElemType));

void destroyOfertaLista(void* l);


// Returnează elementul de pe poziția 'poz'
ElemType get(MyList* v, int poz);

// Pune un element pe poziția 'poz'
ElemType setElem(MyList* v, int poz, ElemType el);

// Returnează numărul de elemente în listă
int size(MyList* v);

// Adaugă un element în listă
void add(MyList* v, ElemType el);

// Șterge un element de pe poziția 'poz'
ElemType delete(MyList* v, int poz);

// Creează o copie a listei
MyList* copyList(MyList* v, ElemType (*copyElem)(ElemType));
