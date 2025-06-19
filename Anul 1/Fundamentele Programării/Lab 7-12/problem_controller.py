import random

from domain.problema import Problema
from domain.validator import Validator


class ProblemController:
    def __init__(self, problem_repository):
        """
        Inițializează o instanță a clasei ProblemController cu un repository de probleme.
        :param problem_repository: repository-ul de probleme pentru gestionare
        """
        self.problem_repository = problem_repository

    def afiseaza_probleme(self):
        """
        Afișează toate problemele disponibile în repository.
        """
        lista_probleme = self.problem_repository.get_all()
        return lista_probleme

    def adauga_problema(self, numar_lab_numar_proba, descriere: str, deadline: int):
        """
        Adaugă o problemă nouă în repository.
        :param numar_lab_numar_proba: identificatorul problemei în formatul 'Numar_Lab_Număr_Probă'
        :param descriere: descrierea problemei
        :param deadline: deadline-ul problemei
        """
        Validator.valideaza_numar_lab_numar_problema(numar_lab_numar_proba)
        Validator.valideaza_numar_lab_numar_problema_duplicat(numar_lab_numar_proba,self.get_lista_probleme())
        Validator.valideaza_deadline(deadline)
        problema_noua = Problema.creeaza_problema(numar_lab_numar_proba, descriere, deadline)
        self.problem_repository.adauga_problema(problema_noua)

    def sterge_problema(self, comanda: str, variabila):
        """
        Șterge o problemă din repository în funcție de comandă și variabilă.
        :param comanda: tipul de criteriu pentru ștergere ('numar' sau 'deadline')
        :param variabila: valoarea criteriului specificat pentru identificarea problemei
        :return: True dacă problema a fost ștearsă, False altfel
        """
        rezultat = False
        if comanda.lower() == "numar":
            Validator.valideaza_numar_lab_numar_problema(variabila)
            Validator.valideaza_numar_lab_numar_problema_existent(variabila,self.get_lista_probleme())
            rezultat = self.problem_repository.sterge_problema_numarlab_numarprob(variabila)
        elif comanda.lower() == 'deadline':
            Validator.valideaza_deadline(variabila)
            variabila = int(variabila)
            rezultat = self.problem_repository.sterge_problema_deadline(variabila)

        return rezultat

    def modifica_problema(self, comanda, numarlab_numarprob, variabila):
        """
        Modifică o problemă specificată în funcție de comanda și variabila date.
        :param comanda: tipul de modificare ('numar', 'descriere' sau 'deadline')
        :param numarlab_numarprob: identificatorul problemei care trebuie modificată
        :param variabila: noua valoare pentru criteriul specificat
        :return: True dacă problema a fost modificată, False altfel
        """
        rezultat = False
        if comanda.lower() == "numar":
            Validator.valideaza_numar_lab_numar_problema(numarlab_numarprob)
            Validator.valideaza_numar_lab_numar_problema_existent(numarlab_numarprob,self.get_lista_probleme())
            Validator.valideaza_numar_lab_numar_problema(variabila)
            Validator.valideaza_numar_lab_numar_problema_duplicat(variabila,self.get_lista_probleme())
            rezultat = self.problem_repository.modifica_problema_numar_lab_numar_prob(numarlab_numarprob, variabila)
        elif comanda.lower() == "descriere":
            Validator.valideaza_numar_lab_numar_problema(numarlab_numarprob)
            Validator.valideaza_numar_lab_numar_problema_existent(numarlab_numarprob, self.get_lista_probleme())
            rezultat = self.problem_repository.modifica_problema_descriere(numarlab_numarprob, variabila)
        elif comanda.lower() == "deadline":
            Validator.valideaza_numar_lab_numar_problema(numarlab_numarprob)
            Validator.valideaza_numar_lab_numar_problema_existent(numarlab_numarprob, self.get_lista_probleme())
            Validator.valideaza_deadline(variabila)
            variabila = int(variabila)
            rezultat = self.problem_repository.modifica_problema_deadline(numarlab_numarprob, variabila)

        return rezultat

    def cauta_problema(self, comanda, variabila):
        """
        Caută probleme în repository în funcție de comanda și variabila date.
        :param comanda: tipul de căutare ('problema', 'laborator' sau 'deadline')
        :param variabila: valoarea criteriului pentru căutare
        """
        if comanda.lower() == 'problema':
            Validator.valideaza_numar_lab_numar_problema(variabila)
            lista = self.problem_repository.cauta_problema_problema(variabila)
            Validator.valideaza_lista(lista)
            return lista
        elif comanda.lower() == 'laborator':
            Validator.valideaza_numar_lab(variabila)
            lista = self.problem_repository.cauta_problema_laborator(variabila)
            Validator.valideaza_lista(lista)
            return lista

        elif comanda.lower() == 'deadline':
            Validator.valideaza_deadline(variabila)
            lista = self.problem_repository.cauta_problema_deadline(variabila)
            Validator.valideaza_lista(lista)
            return lista

    def get_lista_probleme(self):
        """
        Returnează lista de identificatori ai tuturor problemelor.
        :return: lista de identificatori (numar_lab_numar_prob) ca string-uri
        """
        return self.problem_repository.get_lista_probleme()

    def random_problem(self, n):
        """
        Adaugă n probleme random în repository.
        :param n: numărul de probleme de adăugat
        """
        count = 0
        while count < n:
            try:
                nr_lab = random.randint(1, 10)
                nr_prob = random.randint(1, 10)
                descriere = ''.join(random.choice("abcdefghijklmnopqrstuvwxyz") for _ in range(random.randint(10, 20)))
                deadline = random.randint(1, 14)
                nr_lab_nr_prob = f"{nr_lab}_{nr_prob}"
                Validator.valideaza_numar_lab_numar_problema(nr_lab_nr_prob)
                Validator.valideaza_numar_lab_numar_problema_duplicat(nr_lab_nr_prob,self.problem_repository.get_lista_probleme())
                Validator.valideaza_deadline(deadline)
                problema_noua = Problema.creeaza_problema(nr_lab_nr_prob, descriere, deadline)
                self.problem_repository.adauga_problema(problema_noua)
                count += 1
            except ValueError:
                continue