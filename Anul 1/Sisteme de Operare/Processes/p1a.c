#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
int main()
{
	char* fifo1 = "./fifo1";
	int res = mkfifo(fifo1,0644);
	if(res == -1)
	{
		perror("mkfifo()");
		exit(EXIT_FAILURE);
	}
	int fd = open(fifo1,O_WRONLY);
	if(fd == -1 )
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	char nume_fisier[256];
	printf("Introdu nume fisier: \n");
	scanf("%s",nume_fisier);
	FILE* fp = fopen(nume_fisier,"r");
	char ch;
	while(fscanf(fp,"%c",&ch) != -1)
	{
		if(ch <= '9' && ch >= '0')
		{
			write(fd,&ch,sizeof(char));
		}
	}
	fclose(fp);
	close(fd);
	unlink(fifo1);
	return 0;			
}
