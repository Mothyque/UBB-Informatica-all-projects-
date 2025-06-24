#include <assert.h>
#include <stdio.h>

#include "cerinta.h"
#include "cerinta1.h"
#include "cerinta2.h"
#include "cerinta3.h"
#include "cerinta4.h"
#include "cerinta8.h"
#include "cerinta5.h"



void Teste(void)
{
	/* Testeaza functia NumarPrim */

    sir test;

    int expected1[] = {2, 1};
    test = Numar_Sir(2);
	assert(test.lg == 2);
    for (int i = 0; i < test.lg; i++)
    {
        assert(test.v[i] == expected1[i]);
    }

    int expected2[] = { 4, 2, 2 };
	test = Numar_Sir(4);
	assert(test.lg == 3);
    for (int i = 0; i < test.lg; i++)
    {
		assert(test.v[i] == expected2[i]);
    }

    int expected3[] = {19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	test = Numar_Sir(19);
	assert(test.lg == 19);
	for (int i = 0; i < test.lg; i++)
	{
		assert(test.v[i] == expected3[i]);
	}

	int expected4[] = { 6, 2, 2, 3, 3, 3 };
	test = Numar_Sir(6);
	assert(test.lg == 6);
    for (int i = 0; i < test.lg; i++)
    {
        assert(test.v[i] == expected4[i]);
    }
}

void Meniu(void)
{
	while (1)
    {
        printf("Meniu principal: \n");
        printf("1. Genereaza numere prime mai mici decat un nr natural\n");
        printf("2. Genereaza primele n numere prime\n");
        printf("3. Serie de numere consecutive\n");
        printf("4. Determina primele n cifre din k / m\n");
        printf("5. Tipareste numarul precizat \n");
        printf("6. Descompune in factori primi un numar natural dat \n");
        printf("8. Afiseaza exponentul la care numarul prim p apare in descompunerea in factori prim a numarului N = 1 * 2 * ... * n\n");
        printf("E. Exit\n");
        printf("Introdu instructiunea >>> ");
        char instructiune;

        scanf_s("%c", &instructiune, 1);

        switch (instructiune)
        {
        case '1':
        {
            printf("Introdu numar: ");
            int nr;
            scanf_s(" %d", &nr);
            Genereaza(nr);
            break;
        }
        case '2':
        {
            printf("Introdu numar: ");
            int nr;
            scanf_s(" %d", &nr);
            GenereazaPrimele(nr);
            break;
        }
        case '3':
        {
            printf("Introdu numar: ");
            int nr;
            scanf_s(" %d", &nr);
            SumaConsec(nr);
            break;
        }
        case '4':
        {
            printf("Introdu numere: ");
            int k, m, n;
            scanf_s(" %d %d %d", &k, &m, &n);
            CifreZecimale(k, m, n);
            break;
        }
        case '5':
        {
            printf("Introdu numar: ");
            int nr;
            scanf_s(" %d", &nr);
            while (getchar() != '\n'); 
            sir rezultat = Numar_Sir(nr);
            for (int i = 0; i < rezultat.lg; i++)
            {
                printf("%d ", rezultat.v[i]);
            }
			printf("\n");
            break;
        }
        case '6':
        {
            printf("Introdu numar: ");
            int nr;
            scanf_s(" %d", &nr);
            Descompune(nr);
            break;
        }
        case '8':
        {
            printf("Introdu numere: ");
            int n, p;
            scanf_s(" %d %d", &n, &p);
            Putere(n, p);
            break;
        }
        case 'E':
        case 'e':
            printf("Parasire aplicatie...\n");
            return;
        default:
            printf("Instructiune invalida!\n");
            break;
        }
    }
}

int main(int argc, char** argv)
{
    Teste();
	Meniu();
	return 0;
}
