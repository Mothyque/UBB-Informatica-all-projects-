#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int litereMari = 0, suma = 0;
pthread_mutex_t m1;
pthread_mutex_t m2;
void* worker(void* a)
{
	char c;
	FILE* f = fopen((char*)a, "r");
	if(f == NULL)
	{
		perror("fopen()");
		exit(EXIT_FAILURE);
	}
	while(fscanf(f,"%c",&c) != EOF)
	{
		if(c >= '0' && c <= '9')
		{
			pthread_mutex_lock(&m1);
			int numar = c - '0';
			suma += numar;
			pthread_mutex_unlock(&m1);
		}
		if(c >= 'A' && c <= 'Z')
		{
			pthread_mutex_lock(&m2);
			litereMari++;
			pthread_mutex_unlock(&m2);
		}
	}
	fclose(f);
	return NULL;
}

int main(int argc, char** argv)
{
	int cate = argc - 1;
	int i;
	pthread_t* threaduri;
	threaduri = malloc(cate * sizeof(pthread_t));
	pthread_mutex_init(&m1, NULL);
	pthread_mutex_init(&m2, NULL);
	for(i = 0; i < cate; i++)
	{
		pthread_create(&threaduri[i],NULL,worker,argv[i + 1]);
	}
	for(i = 0; i < cate; i++)
	{
		pthread_join(threaduri[i],NULL);
	}
	free(threaduri);
	printf("Suma este: %d \nNumarul de litere mari este: %d\n", suma, litereMari);
	pthread_mutex_destroy(&m1);
	pthread_mutex_destroy(&m2);
	return 0;
}
