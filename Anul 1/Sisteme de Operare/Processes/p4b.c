#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/wait.h>

int main(int argc, char** argv)
{
	if(argc != 2)
	{
		perror("Eroare! Numar de argumente invalid!");
		exit(EXIT_FAILURE);
	}
	char* fifo = argv[1];
	int fd = open(fifo,O_RDONLY);
	if(fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
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
	if(pid > 0)
	{
		close(f[0]);
		int nr = 0;
		while(read(fd,&nr,sizeof(int)))
		{
			int patrat = nr * nr;
			write(f[1],&patrat,sizeof(int));
		}
		close(f[1]);
		close(fd);
		wait(NULL);
	}
	else
	{
		close(f[1]);
		int afisare = 0;
		while(read(f[0],&afisare,sizeof(int)))
		{
			printf("Patrat: %d \n",afisare);
		}
		close(f[0]);
	}
	unlink(fifo);
	return 0;
}
