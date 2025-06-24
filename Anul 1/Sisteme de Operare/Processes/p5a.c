#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <time.h>

int main()
{
	char* fifo = "./fifo";
	int res = mkfifo(fifo,0644);
	if(res == -1)
	{
		perror("mkfifo()");
		exit(EXIT_FAILURE);
	}
	FILE* fp = fopen("numere.txt","w");
	srand(time(NULL));
	for(int i = 1; i <= 100; i++)
	{
		int a = rand() % 100;
		fprintf(fp,"%d ", a);
	}
	fclose(fp);
	int suma = 0;
	int fd = open(fifo,O_RDONLY);
	read(fd,&suma,sizeof(int));
	close(fd);
	printf("Suma numerelor este: %d \n",suma);
	unlink(fifo);
}
