from domain.student import Student

class StudentRepositoryDictionar:
    def __init__(self):
        """
        Inițializează o nouă instanță a clasei StudentRepository și adaugă o listă de studenți implicită.
        """
        self.dictionar_student = {
            "1" : Student(1, "Andrei", 211),
            "2": Student(2, "Paul", 911),
            "3": Student(3, "Cosmin", 211)
        }

    def get_all(self):
        """
        Returnează lista completă de studenți.
        :return: lista de obiecte Student
        """
        return list(self.dictionar_student.values())

    def adauga_student(self, student):
        """
        Adaugă un student nou în listă.
        :param student: obiectul Student de adăugat
        """
        self.dictionar_student[student.get_student_id()] = student

    def sterge_student_id(self, studid: int):
        """
        Șterge studentul din listă după ID.
        :param studid: ID-ul studentului de șters
        :return: True dacă studentul a fost șters, False dacă nu a fost găsit
        """
        if studid in self.dictionar_student:
            del self.dictionar_student[studid]
            return True
        return False

    def sterge_student_nume(self, nume: str):
        """
        Șterge studentul din listă după nume.
        :param nume: numele studentului de șters
        :return: True dacă studentul a fost șters, False dacă nu a fost găsit
        """
        if nume in self.dictionar_student:
            del self.dictionar_student[nume]
            return True
        return False

    def sterge_student_grupa(self, grupa: int):
        """
        Șterge toți studenții dintr-o grupă specificată.
        :param grupa: grupa studentului/studentilor de șters
        :return: True dacă cel puțin un student a fost șters, False dacă nu a fost găsit niciunul
        """
        chei_de_sters = [cheie for cheie, student in self.dictionar_student.items() if student.get_group() == grupa]
        for cheie in chei_de_sters:
            del self.dictionar_student[cheie]
        return len(chei_de_sters) > 0

    def modifica_student_id(self, idcurent, idnou):
        """
        Modifică ID-ul unui student specificat.
        :param idcurent: ID-ul curent al studentului
        :param idnou: noul ID al studentului
        :return: True dacă ID-ul a fost modificat, False dacă studentul nu a fost găsit
        """
        if idcurent in self.dictionar_student:
            student = self.dictionar_student.pop(idcurent)
            student.set_student_id(idnou)
            self.dictionar_student[idnou] = student
            return True
        return False

    def modifica_student_nume(self, idcurent, numenou):
        """
        Modifică numele unui student specificat.
        :param idcurent: ID-ul curent al studentului
        :param numenou: noul nume al studentului
        :return: True dacă numele a fost modificat, False dacă studentul nu a fost găsit
        """
        if idcurent in self.dictionar_student:
            student = self.dictionar_student.pop(idcurent)
            student.set_name(numenou)
            self.dictionar_student[numenou] = student
            return True
        return False

    def modifica_student_grupa(self, idcurent, grupanoua):
        """
        Modifică grupa unui student specificat.
        :param idcurent: ID-ul curent al studentului
        :param grupanoua: noua grupă a studentului
        :return: True dacă grupa a fost modificată, False dacă studentul nu a fost găsit
        """
        if idcurent in self.dictionar_student:
            student = self.dictionar_student.pop(idcurent)
            student.set_group(grupanoua)
            self.dictionar_student[grupanoua] = student
            return True
        return False

    def cauta_student_id(self, idcurent):
        """
        Caută și returnează o listă de studenți care au un ID specificat.
        :param idcurent: ID-ul studentului căutat
        :return: lista de obiecte Student cu ID-ul specificat
        """
        return [student for student in self.dictionar_student.values() if student.get_student_id() == idcurent]

    def cauta_student_nume(self, nume_curent):
        """
        Caută și returnează o listă de studenți care au un nume specificat.
        :param nume_curent: numele studentului căutat
        :return: lista de obiecte Student cu numele specificat
        """
        return [student for student in self.dictionar_student.values() if student.get_name() == nume_curent]

    def cauta_student_grupa(self, grupa_curenta):
        """
        Caută și returnează o listă de studenți care aparțin unei grupe specificate.
        :param grupa_curenta: grupa studentului/studentilor căutați
        :return: lista de obiecte Student din grupa specificată
        """
        return [student for student in self.dictionar_student.values() if student.get_group() == grupa_curenta]

    def get_lista_iduri(self):
        """
        Returnează lista de ID-uri ale tuturor studenților.
        :return: lista de ID-uri ca numere întregi
        """
        return list(self.dictionar_student.keys())
