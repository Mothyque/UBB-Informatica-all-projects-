#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
int main(int argc, char** argv)
{
	if(argc != 2)
	{
		printf("Numar de argumente invalid");
		exit(EXIT_FAILURE);
	}
	char* fifo2 = argv[1];
	int fd = open(fifo2,O_RDONLY);

	if(fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	int f[2], n = 0, divizor = 0;
	int res = pipe(f);
	if(res == -1)
	{
		perror("pipe()");
		exit(EXIT_FAILURE);
	}
	int pid = fork();
	if(pid == -1)
	{
		perror("pid()");
		exit(EXIT_FAILURE);
	}
	if(pid == 0)
	{
		close(f[0]);
		read(fd,&n,sizeof(int));
		close(fd);
		for(int i = n; i >= 1; i --)
		{
			if(n % i == 0)
			{
				write(f[1],&i,sizeof(int));
			}
		}
		close(f[1]);
		exit(0);
	}
	else
	{
		close(f[1]);
		while (read(f[0],&divizor,sizeof(int)) > 0)
		{
			printf("%d ", divizor);
		}
		printf("\n");
		close(f[0]);
		int status;
		wait(&status);
		unlink(fifo2);
	}
	return 0;
}
