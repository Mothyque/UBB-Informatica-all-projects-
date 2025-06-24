#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/wait.h>

int main()
{
	char file[256];
	int n = 0, suma = 0;
	char citit;
	char* fifo = "./fifo";
	int res = mkfifo(fifo,0644);
	if(res == -1)
	{
		perror("mkfifo()");
		exit(EXIT_FAILURE);
	}
	int fd = open(fifo,O_WRONLY);
	if(fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	int f[2];
	res = pipe(f);
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
		printf("Introdu numele fisierului: \n");
		scanf("%s",file);
		FILE* fp = fopen(file,"r");
		printf("Introdu n: \n");
		scanf("%d",&n);
		while(fscanf(fp,"%c",&citit) == 1)
		{
			if(citit >= '0' && citit <= '9')
			{
				n--;
				int numar = citit - '0';
				if(numar % 2 == 0)
				{
					suma += numar;
				}
			}
			if(n == 0) break;
		}
		write(fd,&suma,sizeof(int));
		fclose(fp);
		close(f[1]);
		exit(0);
	}
	else
	{
		close(f[1]);
		while(read(f[0],&citit,sizeof(char)) > 0)
		{
			int numar = citit - '0';
			if(numar % 2 == 0)
			{
				write(fd,&numar,sizeof(int));
			}
		}
		close(f[0]);
		int status;
		wait(&status);
	}
	close(fd);
	unlink(fifo);
	return 0;
}
