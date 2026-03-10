# Sistem de gestiune a unei facultăți 

Aplicație Desktop (JAVA FX + JDBC) pentru gestionarea relațiilor de tip Părinte-Copil, fără utilizarea unui framework ORM.

Cerințe preliminare:
- Java 17 sau o versiune ulterioară
- Baza de date PostgreSQL instalată local
- Un IDE pentru Java (Eclipse, IntelliJ IDEA, NetBeans)

Configurare Baze de Date:
1. Deschide pgAdmin sau terminalul PostgreSQL.
2. Creeaza o baza de date nouă numită 'college' (sau orice alt nume)
3. Ruleaza scriptul 'init.sql' aflat în directorul sql pentru a crea tabelele necesare și a popula baza de date cu date de test.

Configurarea Conexiunii:

1. Deschide fișierul Main.Java din directorul src.
2. Mergi la metoda 'start()'.
3. Modifică variabilele 'url', 'user', și 'password' pentru a se potrivi cu configurația ta de PostgreSQL.

```java
    String url = "jdbc:postgresql://localhost:5432/college"; // Adaptează numele bazei de date
    String username = "postgres"; // Adaptează cu numele de utilizator al bazei de date
    String password = "password"; // Adaptează cu parola bazei de date
```

Cum se rulează aplicația:
* Din IDE-ul tău: rulează clasa Main.Java pentru a porni aplicația.
* Folosind Gradle: deschide terminalul în directorul proiectului și rulează comanda `./gradlew run` (sau `gradlew.bat run` pe Windows).

Funcționalități Extra implementate:
* Sortare: Făcând click pe capul de tabel (ex: "Nume", "Age"), datele se sortează automat.
* Validare avansată: Restricții pe vârstă (strict pozitiva) și număr de credite (0-30).
* Refresh: Buton dedicat pentru resincronizarea datelor din baza de date. (Datele se sincronizeaza oricum automat prin observer, dar acest buton poate fi folosit pentru a forța o actualizare manuală)