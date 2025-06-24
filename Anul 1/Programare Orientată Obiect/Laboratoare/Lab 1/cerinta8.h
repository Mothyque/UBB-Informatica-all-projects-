#ifndef cerinta8_h

#define cerinta8_h

void Putere(int n, int p)
{
	int putere = 0;
	for (int i = 1; i <= n; i++)
	{
		if (i % p == 0)
		{
			int aux = i;
			while (aux % p == 0)
			{
				putere++;
				aux /= p;
			}
		}
	}
	printf("%d", putere);
	printf("\n");
	printf("---------------------------------------------\n");
}

#endif // !cerinta8_h
