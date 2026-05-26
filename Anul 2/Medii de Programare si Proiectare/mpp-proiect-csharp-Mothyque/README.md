# Proiect MPP - Varianta C# (.NET)

Acest repository conține soluțiile pentru temele de laborator din cadrul materiei Medii de Proiectare și Programare (MPP), implementate folosind platforma **C# / .NET**.

## 📂 Modul de organizare a soluțiilor

Organizarea soluțiilor se face folosind branch-uri pentru a evidenția clar evoluția proiectului:
* Branch-ul `main` conține rezolvarea pentru Săptămâna 3.
* Ramura separată `lab4` conține rezolvarea și modificările aduse pentru Săptămâna 4.

## 🚀 Funcționalități implementate

**Săptămâna 3**
* Proiectarea și implementarea claselor din model (entitățile de domeniu).
* Crearea interfețelor de la repository pentru viitoarea persistență a datelor.

**Săptămâna 4**
* Implementarea claselor din repository folosind baze de date relaționale (PostgreSQL via `Npgsql`).
* Adăugarea jurnalizării (Logging) la nivelul claselor din repository folosind pachetul **log4net**.
* Preluarea informațiilor pentru conectarea la baza de date dintr-un fișier de configurare extern (`App.config`).
