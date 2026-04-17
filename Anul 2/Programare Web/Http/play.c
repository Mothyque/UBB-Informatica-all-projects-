#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>

#ifdef _WIN32
  #include <io.h>
  #define O_BINARY_MODE O_BINARY
#else
  #include <unistd.h>
  #define O_BINARY_MODE 0
#endif

struct data {
  int nr;
  int tries;
};

int getIdFromQueryString() {
  char *qs = getenv("QUERY_STRING");
  if (!qs) return 0;
  int id = 0;
  sscanf(qs, "id=%d", &id);
  return id;  
}

int getNumberFromQueryString() {
  char *qs = getenv("QUERY_STRING");
  if (!qs) return 0;
  int id, nr = -1;
  // Căutăm nr în query string
  char *p = strstr(qs, "nr=");
  if (p) sscanf(p, "nr=%d", &nr);
  return nr;  
}

int init() { 
  int r, id;
  int fd;
  char filename[100];
  struct data d;
  
  srand((unsigned int)time(NULL));
  r = rand() % 100;

  do {
    id = rand();
    sprintf(filename, "C:/xampp/tmp/%d.txt", id);
    fd = open(filename, O_WRONLY | O_CREAT | O_EXCL | O_BINARY_MODE, 0600);
  }
  while (fd < 0);

  d.nr = r;
  d.tries = 0;
  write(fd, &d, sizeof(d));
  close(fd);
  
  return id;
}

void destroy(int id) {
  char filename[100];
  sprintf(filename, "C:/xampp/tmp/%d.txt", id);
  remove(filename); 
}

int getNumberFromFile(int id) {
  char filename[100];
  int fd;
  struct data d;
  
  sprintf(filename, "C:/xampp/tmp/%d.txt", id);
  fd = open(filename, O_RDWR | O_BINARY_MODE);
  if (fd < 0) return -1;

  if (read(fd, &d, sizeof(d)) <= 0) {
      close(fd);
      return -1;
  }
  
  d.tries++;
  lseek(fd, 0, SEEK_SET);
  write(fd, &d, sizeof(d));  
  close(fd);
  return d.nr;
}

int getNoOfTries(int id) {
  char filename[100];
  int fd;
  struct data d;
  sprintf(filename, "C:/xampp/tmp/%d.txt", id);
  fd = open(filename, O_RDONLY | O_BINARY_MODE);
  if (fd < 0) return 0;
  read(fd, &d, sizeof(d));
  close(fd);
  return d.tries;    
}

int isNewUser() {
  char *qs = getenv("QUERY_STRING");
  if (qs == NULL || strlen(qs) == 0 || strstr(qs, "id=") == NULL)
    return 1;
  return 0;  
}

void printForm(int id) {
  printf("<form action='play.cgi' method='get'>\n");
  printf("<input type='hidden' name='id' value='%d'>\n", id);
  printf("Introduceti un numar intre 0 si 99: <input type='text' name='nr' autofocus>\n");
  printf("<input type='submit' value='Trimite'>\n");
  printf("</form>");
}

int main() {
  int id, status = 0;
  
  if (isNewUser()) {
    id = init();    
    status = 0;
  }
  else {
    int nr, target;
    id = getIdFromQueryString();
    nr = getNumberFromQueryString();
    target = getNumberFromFile(id);
    
    if (target == -1)
      status = 1;
    else if (nr == target)
      status = 2;
    else if (nr < target)
      status = 3;
    else if (nr > target)
       status = 4;                
  }
  
  printf("Content-type: text/html\n\n");
  printf("<html><head><title>Joc Ghicit</title></head><body>\n");
  
  switch (status) {
    case 0 : printf("<h3>Joc Nou! Am ales un numar.</h3>\n"); printForm(id); break;
    case 1 : printf("Sesiune expirata. <a href='play.cgi'>Incepe un joc nou</a>"); break;
    case 2 : 
        printf("Felicitari! Ai ghicit din %d incercari.<br>", getNoOfTries(id)); 
        printf("<a href='play.cgi'>Joaca din nou</a>");
        destroy(id); 
        break;
    case 3 : printf("<p style='color:blue'>Prea mic!</p>\n"); printForm(id); break;
    case 4 : printf("<p style='color:red'>Prea mare!</p>\n"); printForm(id); break;
  }
  
  printf("</body></html>");
  return 0;
}