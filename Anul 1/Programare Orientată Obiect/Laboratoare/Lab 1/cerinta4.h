#ifndef cerinta4_h

#define cerinta4_h

void CifreZecimale(int k, int m, int n)
{
	printf("0.");
	for (int i = 0; i < n; i++)
	{
		k *= 10;
		printf("%d", k / m);
		k = k % m;

	}
	printf("\n");
	printf("---------------------------------------------\n");
}

#endif // !cerinta4_h
