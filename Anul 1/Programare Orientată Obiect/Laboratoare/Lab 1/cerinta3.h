#include <stdio.h>
#ifndef cerinta3_h

#define cerinta3_h

int SumaConsec(int n) 
{
	int aux1 = n;
	int indice = 1;
	int sir[101];
	while (1)
	{
		int aux2 = aux1;
		int suma = 0;
		int gasit = 0;
		for (int i = aux2 / 2; i >= 0; i--)
		{
			suma += i;
			sir[indice] = i;
			indice++;
			if (suma == n) 
			{
				gasit = 1;
				break;
			}
			if (suma > n) 
			{
				break;
			}
		}
		if(gasit == 1)
		{
			break;
		}
		else
		{
			while (indice)
			{
				sir[indice] = 0;
				indice--;
			}
			indice = 1;
			aux1--;
		}
	}
	for (int i = 1; i < indice;i++)
	{
		printf("%d ", sir[i]);
	}
	printf("\n");
	printf("---------------------------------------------\n");
}

#endif 
