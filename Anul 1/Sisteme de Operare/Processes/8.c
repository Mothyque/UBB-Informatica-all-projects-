#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
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
		close(f[0]);
		srand(time(NULL));
		while(1)
		{
			int n = rand() % 100000;
			int k = 0;
			printf("Introdu un numar: \n");
			scanf("%d",&k);
			while(getchar() != '\n');
			write(f[1],&n,sizeof(int));
			write(f[1],&k,sizeof(int));
			if(k == 0)
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
		while(1)
		{
			int n1,k1;
			read(f[0],&n1,sizeof(int));
			read(f[0],&k1,sizeof(int));
			if(k1 == 0)
			{
				break;
			}
			else
			{
				if(k1 % 2 == 0)
				{
					printf("Suma cifrelor lui %d este: ",n1);
					int suma = 0;
					while(n1)
					{
						suma += n1 % 10;
						n1 /= 10;
					}
					printf("%d \n",suma);
					
				}
				else
                                {
                                        printf("Produsul cifrelor lui %d este: ",n1);
                                        int produs = 1;
                                        while(n1)
                                        {
                                                produs *= n1 % 10;
                                                n1 /= 10;
                                        }
                                        printf("%d \n",produs);

                                }
			}
		}
		close(f[0]);
		exit(0);
	}
	return 0;

}
