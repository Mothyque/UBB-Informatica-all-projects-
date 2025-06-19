class Student:
    def __init__(self, studentid: int, name: str, group: int):
        """
        Inițializează o nouă instanță de tip Student.
        :param studentid: ID-ul studentului ca număr întreg
        :param name: Numele studentului ca șir de caractere
        :param group: Grupa studentului ca număr întreg
        """
        self.__studentid = studentid
        self.__name = name
        self.__group = group

    def get_student_id(self):
        """
        Returnează ID-ul studentului.
        :return: ID-ul studentului ca număr întreg
        """
        return self.__studentid

    def set_student_id(self, v: int):
        """
        Setează ID-ul studentului.
        :param v: noul ID al studentului ca număr întreg
        """
        self.__studentid = v

    def get_name(self):
        """
        Returnează numele studentului.
        :return: numele studentului ca șir de caractere
        """
        return self.__name

    def set_name(self, v: str):
        """
        Setează numele studentului.
        :param v: noul nume al studentului ca șir de caractere
        """
        self.__name = v

    def get_group(self):
        """
        Returnează grupa studentului.
        :return: grupa studentului ca număr întreg
        """
        return self.__group

    def set_group(self, v: int):
        """
        Setează grupa studentului.
        :param v: noua grupă a studentului ca număr întreg
        """
        self.__group = v

    @classmethod
    def creeaza_student(cls, studentid, nume, grupa):
        """
        Creează și returnează o nouă instanță a clasei Student.
        :param studentid: ID-ul studentului ca număr întreg
        :param nume: numele studentului ca șir de caractere
        :param grupa: grupa studentului ca număr întreg
        :return: o instanță a clasei Student
        """
        return cls(studentid, nume, grupa)

    def __eq__(self, other):
        """
        Verifică egalitatea între două obiecte de tip Student.
        :param other: obiectul de comparat
        :return: True dacă obiectele sunt egale, False altfel
        """
        if isinstance(other, Student):
            return self.__studentid == other.__studentid and self.__name == other.__name and self.__group == other.__group
        return False

    def __str__(self):
        """
        Returnează o reprezentare sub formă de șir a obiectului Student.
        :return: descrierea studentului sub formă de șir de caractere
        """
        return "Student: ID = " + str(self.__studentid) + "; Nume = " + self.__name + "; Grupa = " + str(self.__group)
