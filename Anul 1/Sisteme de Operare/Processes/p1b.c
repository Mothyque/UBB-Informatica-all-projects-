#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#define MAX_BUF 1024

int main()
{
	char* fifo1 = "./fifo1";
	int fd = open(fifo1, O_RDONLY);
	if(fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	int suma = 0;
	char a;
	int f[2];
	int res = pipe(f);
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
	if(pid == 0)
	{
		close(f[0]);
		while(read(fd,&a,sizeof(char)) > 0)
		{
			int n = a - '0';
			suma += n;
		}
		write(f[1],&suma,sizeof(int));
		close(f[1]);
		exit(0);
	}
	else
	{
		close(f[1]);
		read(f[0],&suma,sizeof(int));
		printf("Divizorii sunt: 1 ");
		for(int i = 2; i <= suma / 2; i++)
		{
			if(suma % i == 0)
			{
				printf("%d ",i);
			}
		}
		printf("%d. \n",suma);
		close(f[0]);
		int status;
		wait(&status);
		exit(0);
	}	
	close(fd);
	return 0;
}
