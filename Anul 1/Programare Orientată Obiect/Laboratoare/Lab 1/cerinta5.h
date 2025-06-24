#include <malloc.h>
#include <stdio.h>
#ifndef cerinta5_h

#define cerinta5_h

int Prim(int n);

void Descompune(int n)
{
	if (n == 1)
	{
		printf("1 = 1 ^ 1\n");
		printf("---------------------------------------------\n");
		return;
	}

	int* v = malloc(sizeof(int) * (n + 1));
	for (int i = 0; i <= n; i++)
	{
		v[i] = 0;
	}
	int aux = n;
	for (int i = 2; i <= n; i++) 
	{
		if (n % i == 0 && Prim(i))
		{
			while (n % i == 0)
			{
				v[i]++;
				n /= i;
			}
		}
	}
	printf("%d = ", aux);

	int first = 1;  
	for (int i = 2; i <= aux; i++) 
	{
		if (v[i] != 0)  
		{
			if (!first) {
				printf(" * ");  
			}
			printf("%d ^ %d", i, v[i]);
			first = 0; 
		}
	}
	free(v);
	printf("\n");
	printf("---------------------------------------------\n");
	
}

int Prim(int n)
{
	if (n == 2)
	{
		return 1;
	}
	if (n < 2 || n % 2 == 0)
		return 0;
	for (int i = 3; i * i <= n; i++)
	{
		if (n % i == 0)
			return 0;
	}
	return 1;
}

#endif // !cerinta13_h
