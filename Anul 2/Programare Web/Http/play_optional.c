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

int getIdFromCookie() {
  char *cookie_env = getenv("HTTP_COOKIE");
  if (!cookie_env) return 0;
  
  int id = 0;
  char *p = strstr(cookie_env, "jid=");
  if (p) {
    sscanf(p, "jid=%d", &id);
  }
  return id;
}

int getNumberFromQueryString() {
  char *qs = getenv("QUERY_STRING");
  if (!qs) return -1;
  int nr = -1;
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
    if (id < 0) id = -id;
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

void printForm() {
  printf("<form action='play_optional.cgi' method='get'>\n");
  printf("Introduceti un numar (0-99): <input type='text' name='nr' autofocus>\n");
  printf("<input type='submit' value='Trimite'>\n");
  printf("</form>");
}

int main() {
  int id = getIdFromCookie();
  int nr = getNumberFromQueryString();
  int status = 0;
  int new_id_created = 0;

  if (id == 0 || nr == -1) {
    id = init();
    new_id_created = 1;
    status = 0;
  }
  else {
    int target = getNumberFromFile(id);
    if (target == -1) {
      id = init();
      new_id_created = 1;
      status = 0;
    }
    else if (nr == target) status = 2;
    else if (nr < target)  status = 3;
    else if (nr > target)  status = 4;
  }

  if (new_id_created) {
    printf("Set-Cookie: jid=%d; Path=/; HttpOnly\n", id);
  }
  
  if (status == 2) {
      printf("Set-Cookie: jid=deleted; Path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT\n");
  }

  printf("Content-type: text/html\n\n");
  printf("<html><head><title>Joc Ghicit Cookie</title></head><body>\n");

  switch (status) {
    case 0 : printf("<h3>Joc Nou (Sesiune prin Cookie)!</h3>\n"); printForm(); break;
    case 2 : 
        printf("Felicitari! Ai ghicit din %d incercari.<br>", getNoOfTries(id)); 
        printf("<a href='play_optional.cgi?nr=-1'>Joaca din nou</a>");
        destroy(id); 
        break;
    case 3 : printf("<p style='color:blue'>Prea mic!</p>\n"); printForm(); break;
    case 4 : printf("<p style='color:red'>Prea mare!</p>\n"); printForm(); break;
  }

  printf("</body></html>");
  return 0;
}