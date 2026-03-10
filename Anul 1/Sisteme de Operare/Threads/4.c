#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int literaMare = 0, literaMica = 0, suma = 0;
pthread_mutex_t m;

typedef struct{
	char* fisier;
	char c;
}pair;

void* worker(void* a)
{
	pair* pa = (pair*)a;
	FILE* f = fopen(pa->fisier,"r");
	if(f == NULL)
	{
		perror("fopen()");
		exit(EXIT_FAILURE);
	}
	if (pa->c >= '0' && pa->c <= '9')
	{
		char caracter;
		while(fscanf(f,"%c",&caracter) != EOF)
		{
			if(caracter >= '0' && caracter <= '9')
			{
				int numar = caracter - '0';
				pthread_mutex_lock(&m);
				suma += numar;
				pthread_mutex_unlock(&m);
			}
		}
	}
	else if(pa->c >= 'A' && pa->c <= 'Z')
	{
		char caracter;
		while(fscanf(f,"%c",&caracter) != EOF)
		{
			if(caracter >= 'A' && caracter <= 'Z')
			{
				pthread_mutex_lock(&m);
				literaMare++;
				pthread_mutex_unlock(&m);
			}
		}
	}
	else if(pa->c >= 'a' && pa->c <= 'z')
        {
                char caracter;
                while(fscanf(f,"%c",&caracter) != EOF)
                {
                        if(caracter >= 'a' && caracter <= 'z')
                        {
                                pthread_mutex_lock(&m);
                                literaMica++;
                                pthread_mutex_unlock(&m);
                        }
                }
        }
	else
	{
		perror("Argument invalid");
		exit(EXIT_FAILURE);
	}
	fclose(f);
	return NULL;
}

int main(int argc, char** argv)
{
	int cate = (argc - 1) / 2, i = 0;
	pthread_t* threaduri;
	threaduri = malloc(cate * sizeof(pthread_t));
	pthread_mutex_init(&m,NULL);
	pair* p = malloc(cate * sizeof(pair));
	for(i = 0; i < cate; i++)
	{
		p[i].fisier = argv[2 * i + 1];
		p[i].c = *(argv[2 * i + 2]);
		pthread_create(&threaduri[i],NULL,worker,&p[i]);
	}
	for(i = 0; i < cate; i++)
	{
		pthread_join(threaduri[i],NULL);
	}
	pthread_mutex_destroy(&m);
	free(p);
	free(threaduri);
	printf("Numarul de litere mari: %d\nNumarul de litere mici: %d\nSuma: %d\n",literaMare,literaMica,suma);
	return 0;
}
