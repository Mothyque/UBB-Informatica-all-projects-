#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main()
{
	char* fifo = "./fifo";
	int numar = 0;
	int fd = open(fifo,O_RDONLY);
	if(fd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	read(fd,&numar,sizeof(int));	
	printf("%d \n", numar);
	close(fd);
	return 0;
}
