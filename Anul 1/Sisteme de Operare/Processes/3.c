#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>


int main()
{
	int p[2];
	int res = pipe(p);

	if(res == -1)
	{
		perror("pipe()");
		exit(EXIT_FAILURE);
	}
	int pid = fork();
	if(pid == -1)
	{
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	else if (pid == 0)
	{
		int a, b;
		close(p[1]);
		read(p[0],&a,sizeof(int));
		read(p[0],&b,sizeof(int));
		int suma = a + b;
		printf("Suma numerelor %d si %d este %d\n",a,b,suma);
		close(p[0]);
		exit(0);
	}
	else
	{
		close(p[0]);
		int a, b;
		printf("Introdu cele 2 numere:\n");
		scanf("%d %d",&a, &b);
		write(p[1],&a,sizeof(int));
		write(p[1],&b,sizeof(int));	
		int status;
		wait(&status);
		close(p[1]);
	}
	return 0;
}
