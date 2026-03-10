import random
from functools import cmp_to_key

from domain.student import Student
from domain.validator import Validator
from sortari.cmp_grupa_nume import cmp_grupa_asc_nume_desc
from sortari.insertion_sort import insertion_sort


class StudentController:
    def __init__(self, student_repository):
        """
        Inițializează o instanță a clasei StudentController cu un repository de studenți.
        :param student_repository: repository-ul de studenți pentru gestionare
        """
        self.student_repository = student_repository

    def afiseaza_studenti(self):
        """
        Afișează toți studenții disponibili în repository.
        """
        lista_studenti = self.student_repository.get_all()
        return lista_studenti

    def adauga_student(self, studentid, nume, grup):
        """
        Adaugă un student nou în repository.
        :param studentid: ID-ul studentului
        :param nume: numele studentului
        :param grup: grupa din care face parte studentul
        """
        Validator.valideaza_id(studentid)
        Validator.valideaza_id_duplicat(studentid, self.get_lista_iduri())
        Validator.valideaza_nume(nume)
        Validator.valideaza_grupa(grup)
        studentid = int(studentid)
        grup = int(grup)
        student_nou = Student.creeaza_student(studentid, nume, grup)
        self.student_repository.adauga_student(student_nou)

    def sterge_student(self, comanda: str, variabila):
        """
        Șterge un student din repository în funcție de criteriul specificat.
        :param comanda: tipul de criteriu pentru ștergere ('id', 'nume' sau 'grupa')
        :param variabila: valoarea criteriului specificat pentru identificarea studentului
        :return: True dacă studentul a fost șters, False altfel
        """
        rezultat = False
        if comanda.lower() == 'id':
            Validator.valideaza_id(variabila)
            variabila = int(variabila)
            Validator.valideaza_id(variabila)
            Validator.valideaza_id_existent(variabila, self.get_lista_iduri())
            rezultat = self.student_repository.sterge_student_id(variabila)
        elif comanda.lower() == 'nume':
            Validator.valideaza_nume(variabila)
            rezultat = self.student_repository.sterge_student_nume(variabila)
        elif comanda.lower() == 'grupa':
            Validator.valideaza_grupa(variabila)
            variabila = int(variabila)
            rezultat = self.student_repository.sterge_student_grupa(variabila)

        return rezultat

    def modifica_student(self, comanda, idcurent, variabila):
        """
        Modifică informațiile unui student în funcție de criteriul specificat.
        :param comanda: tipul de modificare ('id', 'nume' sau 'grupa')
        :param idcurent: ID-ul studentului care trebuie modificat
        :param variabila: noua valoare pentru criteriul specificat
        :return: True dacă studentul a fost modificat, False altfel
        """
        rezultat = False
        if comanda.lower() == 'id':
            Validator.valideaza_id(idcurent)
            idcurent = int(idcurent)
            Validator.valideaza_id_existent(idcurent, self.get_lista_iduri())
            Validator.valideaza_id(variabila)
            variabila = int(variabila)
            Validator.valideaza_id_duplicat(variabila, self.get_lista_iduri())
            rezultat = self.student_repository.modifica_student_id(idcurent, variabila)
        elif comanda.lower() == 'nume':
            Validator.valideaza_id(idcurent)
            idcurent = int(idcurent)
            Validator.valideaza_id_existent(idcurent, self.get_lista_iduri())
            Validator.valideaza_nume(variabila)
            rezultat = self.student_repository.modifica_student_nume(idcurent, variabila)
        elif comanda.lower() == 'grupa':
            Validator.valideaza_id(idcurent)
            idcurent = int(idcurent)
            Validator.valideaza_id_existent(idcurent, self.get_lista_iduri())
            Validator.valideaza_grupa(variabila)
            variabila = int(variabila)
            rezultat = self.student_repository.modifica_student_grupa(idcurent, variabila)

        return rezultat

    def cauta_student(self, comanda, variabila):
        """
        Caută studenți în repository în funcție de criteriul specificat.
        :param comanda: tipul de căutare ('id', 'nume' sau 'grupa')
        :param variabila: valoarea criteriului pentru căutare
        """
        if comanda.lower() == 'id':
            Validator.valideaza_id(variabila)
            Validator.valideaza_id_existent(int(variabila), self.get_lista_iduri())
            variabila = int(variabila)
            lista = self.student_repository.cauta_student_id(variabila)
            Validator.valideaza_lista(lista)
            return lista
        elif comanda.lower() == 'nume':
            Validator.valideaza_nume(variabila)
            lista = self.student_repository.cauta_student_nume(variabila)
            Validator.valideaza_lista(lista)
            return lista
        elif comanda.lower() == 'grupa':
            Validator.valideaza_grupa(variabila)
            variabila = int(variabila)
            lista = self.student_repository.cauta_student_grupa(variabila)
            Validator.valideaza_lista(lista)
            return lista

    def get_lista_iduri(self):
        """
        Returnează lista de ID-uri ale tuturor studenților.
        :return: lista de ID-uri (int) ale studenților
        """
        return self.student_repository.get_lista_iduri()


    def random_student(self, n):
        """
        Adaugă n studenți random în repository.
        :param n: numarul de studenti de adăugat
        """
        count = 0
        while count < n:
            try:
                studid = random.randint(10, 105)
                lungime_nume = random.randint(3, 10)
                nume = ''.join(random.choice("abcdefghijklmnopqrstuvwxyz") for x in range(lungime_nume))
                grupa = random.randint(1, 1000)
                Validator.valideaza_id_duplicat(studid, self.get_lista_iduri())
                student_nou = Student.creeaza_student(studid, nume, grupa)
                self.student_repository.adauga_student(student_nou)
                count += 1
            except ValueError:
                continue

    def sorteaza_grupa_crescator_nume_descrescator(self):
        """
        Returnează lista de studenți sortată după grupă crescător și nume descrescător.
        """
        lista_studenti = []
        for student in self.student_repository.get_all():
            lista_studenti.append(student)
        lista_studenti = insertion_sort(lista_studenti, cmp = cmp_grupa_asc_nume_desc)
        return lista_studenti
