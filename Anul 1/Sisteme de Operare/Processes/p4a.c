#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>

int main(int argc, char** argv)
{
	if(argc != 3)
	{
		perror("Eroare: Nr argumente invalid!");
		exit(EXIT_FAILURE);
	}
	char* fifo = argv[1];
	char* nume_fisier = argv[2];
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
	FILE* fp = fopen(nume_fisier,"r");
	char cuvant[100];
	if(fp == NULL)
	{
		perror("fopen()");
		exit(EXIT_FAILURE);
	}
	while(fscanf(fp,"%s",cuvant) == 1)
	{
		char frecv[100] = {0};
		int j = 0;
		for(int i = 0; cuvant[i] != '\0'; i++)
		{
			if((cuvant[i] >= 'a' && cuvant[i] <= 'z') || (cuvant[i] >= 'A' && cuvant[i] <= 'Z'))
			{
				int gasit = 0;
				for(int k = 0; k < j; k++)
				{
					if(frecv[k] == cuvant[i])
					{
						gasit = 1;
						break;
					}
				}
				if(gasit == 0)
				{
					frecv[j++] = cuvant[i];
				}
			}
		}
		write(fd,&j,sizeof(int));
	}
	fclose(fp);
	close(fd);
}
