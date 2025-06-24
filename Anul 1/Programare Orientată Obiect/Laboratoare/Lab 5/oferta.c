#include "oferta.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

// Creează o ofertă (cu alocare dinamică pentru câmpuri)
Oferta* createOferta(int id, const char* tip, const char* destinatia, const char* data_plecare, int pret) {
	/* Creează o ofertă nouă cu alocare dinamică pentru câmpuri.
	 * Parametri:
	 * id - identificatorul unic al ofertei (int).
	 * tip - tipul ofertei (char*).
	 * destinatia - locația ofertei (char*).
	 * data_plecare - data plecării (char*).
	 * pret - prețul ofertei (int).
     */
    Oferta* o = (Oferta*)malloc(sizeof(Oferta));

    o->id = id;
    o->pret = pret;

    size_t tip_len = strlen(tip) + 1;
    size_t dest_len = strlen(destinatia) + 1;
    size_t data_len = strlen(data_plecare) + 1;

    o->tip = (char*)malloc(tip_len);
    o->destinatia = (char*)malloc(dest_len);
    o->data_plecare = (char*)malloc(data_len);

    // Copiem datele în câmpurile alocate
    strcpy_s(o->tip, tip_len, tip);
    strcpy_s(o->destinatia, dest_len, destinatia);
    strcpy_s(o->data_plecare, data_len, data_plecare);

    return o;
}

void destroyOferta(Oferta* o) {
    /*Distruge oferta și eliberează memoria dinamică alocată
	 * Parametri:
	 * o - pointer la Oferta
	 */
	if (o != NULL) {
        free(o->tip);
        free(o->destinatia);
        free(o->data_plecare);
        free(o);
    }
}

int esteDataValida(const char* data) {
	/* Verifică dacă data este validă
	 * Parametri:
	 * data - data de verificat (char*)
	 * Returnează:
	 * 1 dacă data este validă, 0 altfel
     */
     
    if (strlen(data) != 10) return 0;

    if (data[2] != '.' || data[5] != '.') return 0;

    int zi, luna, an;
    if (sscanf_s(data, "%d.%d.%d", &zi, &luna, &an) != 3) return 0;

    if (an < 1900 || an > 2100) return 0;
    if (luna < 1 || luna > 12) return 0;

    int zileInLuna[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    if ((an % 4 == 0 && an % 100 != 0) || (an % 400 == 0)) {
        zileInLuna[1] = 29;
    }

    if (zi < 1 || zi > zileInLuna[luna - 1]) return 0;

    return 1; // Data este validă
}

int valideazaOferta(Oferta* o) {
	/* Validează o ofertă
	 * Parametri:
	 * o - pointer la Oferta
	 * Returnează:
	 * 1 dacă oferta este validă
	 * 2 dacă tipul ofertei nu este valid
	 * 3 dacă destinația este goală
	 * 4 dacă data nu este validă
	 * 5 dacă prețul este negativ
 	 */
    if (strcmp(o->tip, "city break") != 0 && strcmp(o->tip, "munte") != 0 && strcmp(o->tip, "mare") != 0) {
        return 2; 
    }

    if (strlen(o->destinatia) == 0) {
        return 3; 
    }

    if (!esteDataValida(o->data_plecare)) {
        return 4; 
    }

    if (o->pret <= 0) {
        return 5; 
    }

    return 1; 
}

void* copyOferta(void* o)
{
	/* Copiază o ofertă
	 * Parametri:
	 * o - pointer la Oferta
	 * Returnează:
	 * pointer la o nouă ofertă
	 */
	Oferta* ofertaOriginala = (Oferta*)o;
	return createOferta(ofertaOriginala->id, ofertaOriginala->tip, ofertaOriginala->destinatia, ofertaOriginala->data_plecare, ofertaOriginala->pret);

}