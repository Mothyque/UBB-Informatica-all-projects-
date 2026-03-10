#include "cerinta1.h"

#ifndef cerinta2_h

#define cerinta2_h

void GenereazaPrimele(int n)
{
	int prim = 2;
	while (n) 
	{
		printf("%d ", prim);
		n--;
		prim++;
		while (!EstePrim(prim))
		{
			prim++;
		}
	}
	printf("\n");
	printf("---------------------------------------------\n");
}

#endif 
