from domain.student import Student

class StudentRepository:
    def __init__(self):
        """
        Inițializează o nouă instanță a clasei StudentRepository și adaugă o listă de studenți implicită.
        """
        self.lista_studenti = [
            Student(1, "Andrei", 211),
            Student(2, "Cosmin", 912),
            Student(3, "Paul", 211),
            Student(4, "Mihai", 915),
            Student(5, "Ana", 213),
            Student(6, "Maria", 214),
            Student(7, "Irina", 912),
            Student(8, "Alex", 213),
            Student(9, "Cristi", 915),
        ]

    def get_all(self):
        """
        Returnează lista completă de studenți.
        :return: lista de obiecte Student
        """
        return self.lista_studenti

    def adauga_student(self, student):
        """
        Adaugă un student nou în listă.
        :param student: obiectul Student de adăugat
        """
        self.lista_studenti.append(student)

    def sterge_student_id(self, studid: int):
        """
        Șterge studentul din listă după ID.
        :param studid: ID-ul studentului de șters
        :return: True dacă studentul a fost șters, False dacă nu a fost găsit
        """
        for student in self.lista_studenti:
            id_curent = student.get_student_id()
            if id_curent == studid:
                self.lista_studenti.remove(student)
                return True
        return False

    def sterge_student_nume(self, nume: str):
        """
        Șterge studentul din listă după nume.
        :param nume: numele studentului de șters
        :return: True dacă studentul a fost șters, False dacă nu a fost găsit
        """
        length = len(self.lista_studenti)
        self.lista_studenti = [student for student in self.lista_studenti if student.get_name() != nume]
        return len(self.lista_studenti) < length

    def sterge_student_grupa(self, grupa: int):
        """
        Șterge toți studenții dintr-o grupă specificată.
        :param grupa: grupa studentului/studentilor de șters
        :return: True dacă cel puțin un student a fost șters, False dacă nu a fost găsit niciunul
        """
        ok = 0
        for student in self.lista_studenti:
            grupa_curent = student.get_group()
            if grupa_curent == grupa:
                self.lista_studenti.remove(student)
                ok = 1
        return ok == 1

    def modifica_student_id(self, idcurent, idnou):
        """
        Modifică ID-ul unui student specificat.
        :param idcurent: ID-ul curent al studentului
        :param idnou: noul ID al studentului
        :return: True dacă ID-ul a fost modificat, False dacă studentul nu a fost găsit
        """
        for student in self.lista_studenti:
            studid = student.get_student_id()
            if studid == idcurent:
                student.set_student_id(idnou)
                return True
        return False

    def modifica_student_nume(self, idcurent, numenou):
        """
        Modifică numele unui student specificat.
        :param idcurent: ID-ul curent al studentului
        :param numenou: noul nume al studentului
        :return: True dacă numele a fost modificat, False dacă studentul nu a fost găsit
        """
        for student in self.lista_studenti:
            studid = student.get_student_id()
            if studid == idcurent:
                student.set_name(numenou)
                return True
        return False

    def modifica_student_grupa(self, idcurent, grupanoua):
        """
        Modifică grupa unui student specificat.
        :param idcurent: ID-ul curent al studentului
        :param grupanoua: noua grupă a studentului
        :return: True dacă grupa a fost modificată, False dacă studentul nu a fost găsit
        """
        for student in self.lista_studenti:
            studid = student.get_student_id()
            if studid == idcurent:
                student.set_group(grupanoua)
                return True
        return False

    def cauta_student_id(self, idcurent):
        """
        Caută și returnează o listă de studenți care au un ID specificat.
        :param idcurent: ID-ul studentului căutat
        :return: lista de obiecte Student cu ID-ul specificat
        """
        lista = []
        for student in self.lista_studenti:
            studid = student.get_student_id()
            if studid == idcurent:
                lista.append(student)
        return lista

    def cauta_student_nume(self, nume_curent):
        """
        Caută și returnează o listă de studenți care au un nume specificat.
        :param nume_curent: numele studentului căutat
        :return: lista de obiecte Student cu numele specificat
        """
        lista = []
        for student in self.lista_studenti:
            nume = student.get_name()
            if nume == nume_curent:
                lista.append(student)
        return lista

    def cauta_student_grupa(self, grupa_curenta):
        """
        Caută și returnează o listă de studenți care aparțin unei grupe specificate.
        :param grupa_curenta: grupa studentului/studentilor căutați
        :return: lista de obiecte Student din grupa specificată
        """
        lista = []
        for student in self.lista_studenti:
            grupa = student.get_group()
            if grupa == grupa_curenta:
                lista.append(student)
        return lista

    def get_lista_iduri(self, index=0, lista_id=None):
        """
        Returnează lista de ID-uri ale tuturor studenților.
        :return: lista de ID-uri ca numere întregi
        """
        if lista_id is None:
            lista_id = []
        if index >= len(self.lista_studenti):
            return lista_id
        lista_id.append(self.lista_studenti[index].get_student_id())
        return self.get_lista_iduri(index + 1, lista_id)
    #recursive function

    def get_student_by_id(self, student_id:int):
        """
        Returnează studentul după id-ul acestuia.
        :param student_id: id-ul studentului
        :return: obiect de tip Student
        """
        """
        Analiza complexitatii:
        - caz favoaribil (primul student este chiar cel cautat): T(n) = 1 ∈ θ(1)
        - caz defavorabil (studentul cu id-ul student_id nu exista): T(n) = n ∈ θ(n)
        - caz mediu: Sansa ca studentul sa se gaseasca in sirul cu n elemente (daca acesta exista) este de 1/n. T(n) = 1/n + 2/n + ... + n/n = (1 + 2 + ... + n)/n = n(n+1)/2n = (n+1)/2 ∈ θ(n)
        - complexitate: O(n)
        """
        for student in self.lista_studenti:
            if student.get_student_id() == student_id:
                return student
        return None



    def clear(self):
        """
        Șterge toți studenții din listă.
        """
        self.lista_studenti.clear()