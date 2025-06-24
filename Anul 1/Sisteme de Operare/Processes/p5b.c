#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/wait.h>

int main()
{
	char* fifo = "./fifo";
	int fd = open(fifo, O_WRONLY);
	if (fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	int a = 0, suma = 0;
	FILE* fp = fopen("numere.txt", "r");
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
		while(fscanf(fp,"%d",&a) == 1)
        	{
                	write(f[1],&a,sizeof(int));
        	}
		close(f[1]);
	}
	else
	{
		close(f[1]);
		while(read(f[0],&a,sizeof(int)))	
		{
			suma += a;
		}	
		close(f[0]);
		write(fd,&suma,sizeof(int));
		wait(NULL);
	}
	fclose(fp);
	close(fd);
	return 0;
}
