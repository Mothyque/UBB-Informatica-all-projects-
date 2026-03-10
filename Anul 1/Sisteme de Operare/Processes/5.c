#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
int main()
{
	int f[2];
	int a = 0, b = 0;
	char op;
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
		srand(time(NULL));
		while(1)
		{
			a = rand() % 100;
			b = rand() % 100;
			printf("Introdu simbol: +, -, *, /, 0 \n");
			scanf("%c",&op);
			while(getchar() != '\n');
			write(f[1],&a,sizeof(int));
			write(f[1],&b,sizeof(int));
			write(f[1],&op,sizeof(char));
			if(op == '0')
			{
				break;
			}
		}
		close(f[1]);
		wait(NULL);
	
	}
	else
	{
		close(f[1]);
		FILE* fp = fopen("rezultate.txt","w");
		if(fp == NULL)
		{
			perror("open()");
			exit(EXIT_FAILURE);
		}
		while(1)
		{
			int a, b;
			char op;
			read(f[0],&a,sizeof(int));
			read(f[0],&b,sizeof(int));
			read(f[0],&op,sizeof(char));
			if(op == '0')
			{
				break;
			}
			int rezultat;
			if(op == '+')
			{
				rezultat = a + b;
				fprintf(fp,"%d + %d = %d \n",a,b,rezultat);
			}
			else if(op == '-')
                        {
                                rezultat = a - b;
                                fprintf(fp,"%d - %d = %d \n",a,b,rezultat);
                        }
			else if(op == '*')
                        {
                                rezultat = a * b;
                                fprintf(fp,"%d * %d = %d \n",a,b,rezultat);
                        }
			else if(op == '/')
                        {
				if(b != 0)
				{
					rezultat = a / b;
                                	fprintf(fp,"%d / %d = %d \n",a,b,rezultat);
				}
				else
				{
					perror("Eroare! Impartire la 0");
				}	
                        }
			else
			{
				perror("Instructiune invalida!");
			}
		}
		fclose(fp);
		close(f[0]);
	}
	return 0;
}
