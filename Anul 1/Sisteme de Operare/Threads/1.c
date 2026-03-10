#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <limits.h>

pthread_t citire, thr[26];
int n, fr[100];
int *v = NULL;
pthread_barrier_t barr;
int sum = 0, avr, minim = INT_MAX;
pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;

void* cititor(void* a)
{
	FILE* fd = fopen("numere.txt","r");
	int i = 0;
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
		if(v[i] == indice)
		{
			fr[indice]++;
		}
	}
	pthread_mutex_lock(&mtx);
		sum += fr[indice];
	pthread_mutex_unlock(&mtx);

	pthread_barrier_wait(&barr);
	avr = sum / 25;

	pthread_mutex_lock(&mtx);
		if(abs(avr-fr[indice]) < minim)
		{
			minim = abs(avr - fr[indice]);
		}
	pthread_mutex_unlock(&mtx);
	return NULL;
}

int prim(int x)
{
	int i;
	if(x == 2)
	{
		return 1;
	}
	for(i = 2; i <= x / 2; i++)
	{
		if(x %i == 0)
		{
			return 0;
		}
	}
	return 1;
}

int main(int argc, char** argv)
{
	int cnt = 0, i;	
	int* aux;

	printf("Introdu n: ");
	scanf("%d",&n);
	v = malloc(n* sizeof(int));
	pthread_create(&citire,NULL,cititor,NULL);
	pthread_join(citire,NULL);

	pthread_barrier_init(&barr,NULL,25);

	for(i = 2; i < 100; i++)
	{
		if(prim(i) == 1)
		{
			aux = (int*)malloc(sizeof(int));
			*aux = i;
			pthread_create(&thr[cnt],NULL,worker,aux);
			cnt++;
		}
	}
	for(i = 0; i < cnt; i++)
	{
		pthread_join(thr[i],NULL);
	}
	pthread_mutex_destroy(&mtx);

	for(i = 2; i < 100; i++)
	{
		if(prim(i) == 1)
		{
			if(abs(sum/25-fr[i]) == minim)
			{
				printf("Numar: %d | Aparitii: %d\n",i,fr[i]);
			}
		}
	}
	printf("Media aritmetica: %d | Cea mai mica diferenta: %d\n",avr,minim);
	free(v);
	pthread_barrier_destroy(&barr);
	return 0;
}
