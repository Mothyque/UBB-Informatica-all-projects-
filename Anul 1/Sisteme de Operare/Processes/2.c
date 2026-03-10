#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int prim(int a)
{
	if (a == 2 || a == 3)
	{
		return 1;
	}
	if (a % 2 == 0 || a < 2)
	{
		return 0;
	}
	for(int i = 5; i < a / 2; i++)
	{
		if (a % i == 0)
			return 0;
	}
	return 1;
}
int main()
{
	int p[2];
	int res = pipe(p);

	if(res == -1)
	{
		perror("pipe()");
		exit(EXIT_FAILURE);
	}

	int f = fork();
	int n;
	if (f == -1)
	{
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	else if (f == 0)
	{
		close(p[1]);
		read(p[0],&n,sizeof(int));
		if (prim(n) == 1)
		{
			printf("Numarul %d este prim\n", n);
		}	
		else
		{
			printf("Numarul %d nu este prim\n",n);
		}
		close(p[0]);
		exit(0);
	}
	else
	{
		close(p[0]);
		printf("Introdu numarul: ");
		scanf("%d",&n);
		write(p[1],&n,sizeof(int));

		int status;
		wait(&status);
		close(p[1]);
	}
	return 0;
}

