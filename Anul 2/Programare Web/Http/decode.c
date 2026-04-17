#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int hexatoint(char c) {
    if ((c >= 'a') && (c <= 'f')) return c - 'a' + 10;
    if ((c >= 'A') && (c <= 'F')) return c - 'A' + 10;
    if ((c >= '0') && (c <= '9')) return c - '0';
    return 0;
}

void decode(char *s) {
    int i = 0, j;
    while (s[i] != 0) {
        if (s[i] == '+') {
            s[i] = ' ';
        }
        if (s[i] == '%') {
            if (s[i+1] != 0 && s[i+2] != 0) {
                char c = 16 * hexatoint(s[i + 1]) + hexatoint(s[i + 2]);
                s[i] = c;
                j = i + 1;
                while (s[j + 2] != 0) {
                    s[j] = s[j + 2];
                    j++;
                }
                s[j] = 0;
            }
        }
        i++;
    }
}

int main() {
    char *qs_env = getenv("QUERY_STRING");
    char s[2048] = "";

    printf("Content-type: text/html\n\n");
    fflush(stdout);
    printf("<html><head><meta charset='UTF-8'></head><body>");

    if (qs_env != NULL && strlen(qs_env) > 0) {
        char buffer[2048];
        strncpy(buffer, qs_env, 2047);
        buffer[2047] = '\0';

        char *valoare = strchr(buffer, '=');
        if (valoare != NULL) {
            strcpy(s, valoare + 1); 
            
            printf("<b>Șirul brut din URL:</b> %s<br><br>\n", buffer);
            decode(s);
            printf("<b>Șirul decodificat:</b> %s<br><br>\n", s);
        } else {
            printf("Parametrul 'data' lipsește din URL.");
        }
    } else {
        printf("Nu am primit date. Folosiți formularul HTML.");
    }

    printf("<br><br><a href='decode.html'>Înapoi la formular</a>");
    printf("</body></html>");
    return 0;
}