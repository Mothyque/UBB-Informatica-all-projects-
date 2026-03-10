from domain.student import Student
from repository.student_repository import StudentRepository


class StudentRepositoryFile(StudentRepository):
    def __init__(self, file_name):
        """
        Inițializează un nou repository de tip Student care stochează datele într-un fișier.
        :param file_name: numele fișierului în care se vor salva datele
        """
        StudentRepository.__init__(self)
        self.__file_name = file_name
        StudentRepository.clear(self)
        self._load_from_file()

    def _load_from_file(self):
        """
        Încarcă datele din fișierul specificat în repository.
        """
        try:
            cnt = 0
            with open(self.__file_name, "r") as file:
                lines = file.read().split("\n")
                for line in lines:
                    if line == "":
                        break
                    if cnt == 0:
                        student_id = int(line)
                        cnt += 1
                    elif cnt == 1:
                        name = line
                        cnt += 1
                    elif cnt == 2:
                        group = int(line)
                        cnt = 0
                        student = Student(student_id, name, group)
                        StudentRepository.adauga_student(self, student)
        except IOError:
            raise ValueError("Error reading file!")

    def adauga_student(self, student):
        """
        Adaugă un student nou în repository și salvează datele în fișier.
        :param student: studentul de adăugat
        :return: True dacă studentul a fost adăugat, False dacă există deja un student cu același ID
        """
        rezultat = super().adauga_student(student)
        self._save_to_file()
        return rezultat

    def sterge_student_id(self, student_id):
        """
        Șterge un student din repository după ID și salvează datele în fișier.
        :param student_id: id-ul studentului de șters
        :return: True dacă studentul a fost șters, False dacă nu a fost găsit
        """
        rezultat = super().sterge_student_id(student_id)
        self._save_to_file()
        return rezultat

    def sterge_student_nume(self, nume: str):
        """
        Șterge un student din repository după nume și salvează datele în fișier.
        :param nume: numele studentului de șters
        :return: True dacă studentul a fost șters, False dacă nu a fost găsit
        """
        rezultat = super().sterge_student_nume(nume)
        self._save_to_file()
        return rezultat


    def sterge_student_grupa(self, grupa: int):
        """
        Șterge toți studenții dintr-o grupă specificată și salvează datele în fișier.
        :param grupa: grupa de șters
        :return: True dacă cel puțin un student a fost șters, False dacă nu a fost găsit niciunul
        """
        rezultat = super().sterge_student_grupa(grupa)
        self._save_to_file()
        return rezultat

    def modifica_student_id(self, id_curent, id_nou):
        """
        Modifică ID-ul unui student și salvează datele în fișier.
        :param id_curent: id-ul curent al studentului
        :param id_nou: noul id al studentului
        :return: True dacă id-ul a fost modificat, False dacă studentul nu a fost găsit
        """
        rezultat = super().modifica_student_id(id_curent, id_nou)
        self._save_to_file()
        return rezultat

    def modifica_student_nume(self, id_student, nume_nou):
        """
        Modifică numele unui student și salvează datele în fișier.
        :param id_student: id-ul studentului
        :param nume_nou: noul nume al studentului
        :return: True dacă numele a fost modificat, False dacă studentul nu a fost găsit
        """
        rezultat = super().modifica_student_nume(id_student, nume_nou)
        self._save_to_file()
        return rezultat

    def modifica_student_grupa(self, id_student, grupa_noua):
        """
        Modifică grupa unui student și salvează datele în fișier.
        :param id_student: id-ul studentului
        :param grupa_noua: noua grupă a studentului
        :return: True dacă grupa a fost modificată, False dacă studentul nu a fost găsit
        """
        rezultat = super().modifica_student_grupa(id_student, grupa_noua)
        self._save_to_file()
        return rezultat

    def _save_to_file(self):
        with open(self.__file_name, "w") as file:
            for student in self.get_all():
                file.write(str(student.get_student_id()) + "," + student.get_name() + "," + str(student.get_group()) + "\n")