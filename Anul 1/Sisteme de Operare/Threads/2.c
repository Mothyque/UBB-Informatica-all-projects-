#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <limits.h>

pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;
int sum = 0, avr, minim = INT_MAX;
pthread_barrier_t barr;
int fr[10], n;
pthread_t citire, thr[10];
int* v;

void* cititor(void* a)
{
	(void) a;
	FILE* fd = fopen("numere.txt","r");
	if(fd == NULL)
	{
		perror("fopen()");
		exit(EXIT_FAILURE);
	}
	int i;
	for(i = 0; i < n; i++)
	{
		fscanf(fd,"%d",&v[i]);
	}
	fclose(fd);
	return NULL;
}

void* worker(void* arg)
{
	int i, indice = *(int*)arg;
	free(arg);
	for(i = 0; i < n; i++)
	{
		if(v[i] % 10 == indice)
		{
			fr[indice]++;
		}
	}
	pthread_mutex_lock(&mtx);
	sum += fr[indice];
	pthread_mutex_unlock(&mtx);

	pthread_barrier_wait(&barr);

	avr = sum / 10;

	pthread_mutex_lock(&mtx);
	if(abs(avr-fr[indice]) < minim)
	{
		minim = abs(avr - fr[indice]);
	}
	pthread_mutex_unlock(&mtx);
	return NULL;
}

int main()
{
	printf("Introdu n: ");
	scanf("%d",&n);
	int i;
	v = malloc(n * sizeof(int));
	pthread_mutex_init(&mtx,NULL);

	pthread_barrier_init(&barr,NULL,10);
	pthread_create(&citire,NULL,cititor,NULL);
	pthread_join(citire,NULL);
	for(i = 0; i <= 9; i++)
	{
		fr[i] = 0;
	}
	for(i = 0; i <= 9; i++)
	{
		int* aux = (int*)malloc(sizeof(int));
		*aux = i;
		pthread_create(&thr[i],NULL,worker,aux);
	}
	for(i = 0; i <= 9; i++)
	{
		pthread_join(thr[i],NULL);
	}
	pthread_mutex_destroy(&mtx);
	for(i = 0; i <= 9; i++)
	{
		if(abs(avr - fr[i]) == minim)
		{
			printf("Cifra: %d | Nr de aparitii: %d\n", i,fr[i]);
		}
	}
	printf("Media: %d | Cea mai mica diferenta: %d\n",avr,minim);
	free(v);
	pthread_barrier_destroy(&barr);
	return 0;
}
