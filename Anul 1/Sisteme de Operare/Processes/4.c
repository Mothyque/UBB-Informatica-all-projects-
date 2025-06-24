#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>

int main()
{
	int p[2];
	int res = pipe(p);
	
	if (res == -1)
	{
		perror("pipe()");
		exit(EXIT_FAILURE);
	}
	
	int pid = fork();
	if (pid == -1)
	{
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	else if(pid == 0)
	{
		char litera, text[101];
		int cnt = 0;
		close(p[1]);
		read(p[0],&litera,sizeof(char));
		read(p[0], &text, 101*sizeof(char));
		for (int i = 0; i < 101; ++i)
		{
			if(text[i] == litera)
			{
				cnt++;
			}
		}
		printf("Litera %c apare in text de %d ori \n",litera,cnt);
		close(p[0]);
		exit(0);
	}
	else
	{
		close(p[0]);
		char litera, text[101];
		printf("Introdu litera: \n");
		scanf("%s",&litera);
		getchar();
		printf("Introdu text: \n");
		fgets(text,101,stdin);
		write(p[1],&litera,sizeof(char));
		write(p[1],&text,101*sizeof(char));
		close(p[1]);
		int status;
		wait(&status);
	}
	
	return 0;
}

