# Proiect MPP - Varianta Java

Acest repository conține soluțiile pentru temele de laborator din cadrul materiei Medii de Proiectare și Programare (MPP), implementate folosind limbajul **Java**.

## 📂 Modul de organizare a soluțiilor

Organizarea soluțiilor se face folosind branch-uri pentru a evidenția clar evoluția proiectului:
* Branch-ul `main` conține rezolvarea pentru Săptămâna 3.
* Ramura `lab4` conține rezolvarea și modificările aduse pentru Săptămâna 4.
* Ramura `lab5` conține arhitectura extinsă (Servicii și interfață grafică) pentru Săptămâna 5.

## 🚀 Funcționalități implementate

**Săptămâna 3**
* Proiectarea și implementarea claselor din model (entitățile de domeniu).
* Crearea interfețelor de la repository pentru viitoarea persistență a datelor.

**Săptămâna 4**
* Implementarea claselor din repository folosind baze de date relaționale (**SQLite** via JDBC), utilizând o arhitectură optimizată cu o singură conexiune refolosibilă.
* Adăugarea jurnalizării (Logging) la nivelul claselor din repository folosind librăria **Log4j2** (cu bridge SLF4J), direcționând istoricul de execuție exclusiv într-un fișier local (`logs/app.log`).
* Preluarea informațiilor pentru conectarea la baza de date dintr-un fișier de configurare extern (`db.properties`), citit direct din resursele aplicației (classpath).
* Securizarea accesului prin implementarea de parole hash-uite (**BCrypt**) direct în baza de date.

**Săptămâna 5**
* Proiectarea și implementarea stratului de **Servicii** (`MatchService`, `TicketService`, `UserService`), decuplând interfața de accesul la date.
* Crearea **Interfeței Grafice (GUI)** a proiectului folosind framework-ul **JavaFX** (fișiere `.fxml` pentru ferestre).
* Crearea unui `AbstractController` generic care centralizează injecția serviciilor și afișarea alertelor de interfață, eliminând codul duplicat.
* Integrarea completă a arhitecturii stratificate: controllerul interfeței grafice preia datele și apelează metodele din servicii, iar serviciile comunică cu repository-urile.
* Configurarea rulării ferestrelor în mod izolat pentru testare (Single Window), gestionând operațiile CRUD fără refresh automat, conform cerințelor laboratorului.
