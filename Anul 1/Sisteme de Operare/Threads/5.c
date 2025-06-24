#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

float suma = 0, avr = 0;
int** mat;
float* medii;
int n = 0, m = 0;
pthread_mutex_t mtx;
pthread_barrier_t barr;

void* worker(void* a)
{
        int linia = *(int*)a;
	free(a);
        int i;
        float suma_linie = 0;
        for(i = 0; i < m; i++)
        {
                suma_linie += mat[linia][i];
        }
        float avr_linie = suma_linie / m;
        medii[linia] = avr_linie;
        pthread_mutex_lock(&mtx);
        suma += avr_linie;
        pthread_mutex_unlock(&mtx);
        pthread_barrier_wait(&barr);
        avr = suma / n;
        return NULL;
}

int main(int argc, char** argv)
{
        pthread_t* threaduri;
        if(argc != 4)
        {
                perror("Numar de argumente invalid");
                exit(EXIT_FAILURE);
        }
        n = atoi(argv[2]);
        m = atoi(argv[3]);
        if(n == 0 || m == 0)
        {
                perror("Dimensiuni invalide pentru matrice");
                exit(EXIT_FAILURE);
        }
	char* nume_fisier = argv[1];
	FILE* f = fopen(nume_fisier, "r");
	if(f == NULL)
	{
		perror("Nu s-a reusit deschiderea fisierului");
		exit(EXIT_FAILURE);
	}
        int i,j;
	medii = malloc(n * sizeof(float));
	mat = malloc(n * sizeof(int*));
        for(i = 0; i < n; i++)
        {
                mat[i] = (int*)malloc(m * sizeof(int));
        }
	for(i = 0; i < n; i++)
	{
		for(j = 0; j < m; j++)
		{
			fscanf(f,"%d",&mat[i][j]);
		}
	}
        pthread_mutex_init(&mtx, NULL);
        pthread_barrier_init(&barr,NULL,n);
        threaduri = malloc(n * sizeof(pthread_t));
        for(i = 0; i < n; i++)
        {
		int* indice = malloc(sizeof(int));
		*indice = i;
                pthread_create(&threaduri[i],NULL,worker,indice);
        }
        for(i = 0; i < n; i++)
        {
                pthread_join(threaduri[i],NULL);
        }
        pthread_mutex_destroy(&mtx);
        pthread_barrier_destroy(&barr);
        free(threaduri);
        for(i = 0; i < n; i++)
        {
                free(mat[i]);
        }
        for(i = 0; i < n; i++)
        {
                printf("Media in ziua %d este: %.1f\n", i+1, medii[i]);
        }
        free(medii);
        printf("Media generala: %.1f\n",avr);
        return 0;
}
