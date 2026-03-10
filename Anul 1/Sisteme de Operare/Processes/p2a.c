#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/stat.h>
int cmmdc(int a, int b)
{
	if(a == 0)
	{
		return b;
	}
	if(b == 0)
	{
		return a;
	}
	if(a == b)
	{
		return a;
	}
	else if (a > b)
	{
		return cmmdc(b, a % b);
	}
	else
	{
		return cmmdc(b % a, a);
	}
}

int main(int argc, char** argv)
{
	if(argc != 2)
	{
		printf("Numar de argumente invalid");
		exit(EXIT_FAILURE);
	}
	char* fifo2 = argv[1];
	int res = mkfifo(fifo2,0644);
	if(res == -1)
	{
		perror("mkfifo()");
		exit(EXIT_FAILURE);
	}
	int fd = open(fifo2,O_WRONLY);
	if(fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	int a, b, divizor, multiplu;
	printf("Introdu doua numere: \n");
	scanf("%d %d", &a, &b);
	divizor = cmmdc(a,b);
	multiplu = (a * b) / divizor;
	write(fd,&multiplu,sizeof(int));
	close(fd);
	return 0;
}

