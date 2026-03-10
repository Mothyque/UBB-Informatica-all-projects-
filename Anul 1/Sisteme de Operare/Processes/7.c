#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
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
	if(pid > 0)
	{
		int a, b;
		close(f[0]);
		char op;
		FILE* fp = fopen("operatii.txt","r");
		while(fscanf(fp,"%d %c %d",&a,&op,&b) == 3)
		{
			write(f[1],&a,sizeof(int));
			write(f[1],&b,sizeof(int));
			write(f[1],&op,sizeof(char));
		}
		char finish = 'f';
		write(f[1],&finish,sizeof(char));	
		fclose(fp);
		close(f[1]);
		wait(NULL);
	}
	else
	{
		int a, b;
		char ch;
		close(f[1]);
		while(read(f[0],&a, sizeof(int)) > 0 && read(f[0],&b,sizeof(int)) > 0 && read(f[0],&ch,sizeof(char)) > 0)
		{
			if(ch == 'f')
			{
				break;
			}
			if(ch == '+')
			{
				int rezultat = a + b;
				printf("%d + %d = %d \n", a,b,rezultat);
			}
			else if(ch == '-')
                        {
                                int rezultat = a - b;
                                printf("%d - %d = %d \n", a,b,rezultat);
                        }
			else if(ch == '*')
                        {
                                int rezultat = a * b;
                                printf("%d * %d = %d \n", a,b,rezultat);
                        }
			else if(ch == '/')
                        {
				if(b != 0)
				{
					int rezultat = a / b;
                                	printf("%d / %d = %d \n", a,b,rezultat);
				}
                                else
				{
					perror("Eroare! Division by zero!");
				}
                        }
			else
			{
				perror("Eroare semn");
			}
		}
		close(f[0]);
	}
	return 0;
}


