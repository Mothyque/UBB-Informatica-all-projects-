#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <time.h>
int main()
{
	int to_parent[2],to_child[2];
	char ch;
	int res1 = pipe(to_parent);
	int res2 = pipe(to_child);
	if(res1 == -1 || res2 == -1)
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
		srand(time(NULL));
		close(to_child[0]);
		close(to_parent[1]);
		int nr = rand() % 901 + 100;
		write(to_child[1],&nr,sizeof(int));
		while(1)
		{
			int nr_primit = 0;
			read(to_parent[0],&nr_primit,sizeof(int));
			if(nr_primit == nr)
			{
				ch = '=';
				printf("%d = %d \n", nr_primit,nr);
				write(to_child[1],&ch,sizeof(char));
				break;
			}
			else if(nr_primit > nr)
			{
				ch = '>';
				printf("%d > %d \n", nr_primit,nr);
				write(to_child[1],&ch,sizeof(char));
			}
			else
			{
				ch = '<';
				printf("%d < %d \n", nr_primit,nr);
				write(to_child[1],&ch, sizeof(char));
			}
		}	
		close(to_child[1]);
		close(to_parent[0]);
		wait(NULL);
	}
	else
	{
		close(to_child[1]);
		close(to_parent[0]);
		srand(time(NULL) ^ getpid());
		while(1)
		{
			int nr_generat = rand() % 901 + 100;
			write(to_parent[1],&nr_generat,sizeof(int));
			char ch;
			read(to_child[0],&ch,sizeof(char));
			if(ch == '=')
			{
				break;
			}
		}
		close(to_parent[1]);
		close(to_child[0]);
	}
	return 0;
}
