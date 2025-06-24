#include "cerinta.h"

#include <stdio.h>

sir Numar_Sir(int n)
{
	/* Tipareste un numar precizat de termeni din sirul
	 *1, 2,1, 3,2,1, 4,2,2, 5,4,3,2,1, 6,2,2,3,3,3, 7,6, ...
	 * n - nr natural
	 * returneaza numarul precizat
	*/
	sir s;
	if (NumarPrim(n))
	{
		s.lg = n;
		for (int i = n; i >= 1; i--)
		{
			s.v[n - i] = i;
		}
	}
	else
	{
		s.lg = 1;
		int indice = 0;
		s.v[indice] = n;
		indice++;

		for (int i = 2; i <= n / 2; i++)
		{
			if (n % i  == 0)
			{
				int aux = i;
				while (aux)
				{
					s.lg++;
					s.v[indice] = i;
					indice++;
					aux--;
				}
			}
			
		}
	}
	return s;

}

int NumarPrim(int  n)
{
	/* Determina daca un numar este prim sau nu
	 * n - numar natural
	 * returneaza 1 daca numarul este prim, 0 in caz contrar
	*/

	if (n == 2)
	{
		return 1;
	}
	if (n % 2 == 0 || n <= 2)
	{
		return 0;
	}
	for (int i = 3; i <= n / 2; i += 2)
	{
		if (n % i == 0)
		{
			return 0;
		}
	
	}
	return 1;
}