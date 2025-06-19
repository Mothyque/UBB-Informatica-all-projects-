class Laborator:
    """
    Clasa Laborator reprezintă un obiect de tip laborator.
    """
    def __init__(self, student_id, numar_lab_numar_prob, nota):
        self.__student_id = student_id
        self.__numar_lab_numar_prob = numar_lab_numar_prob
        self.__nota = nota

    def get_student_id_lab(self):
        """
        Returnează student_id-ul.
        :return: id studentului
        """
        return self.__student_id

    def set_student_id_lab(self, v):
        """
        Modifică student_id-ul.
        :param v: id nou
        """
        self.__student_id = v

    def get_numar_lab_numar_prob_lab(self):
        """
        Returnează numărul laboratorului și al problemei.
        :return: numărul laboratorului și al problemei
        """
        return self.__numar_lab_numar_prob

    def set_numar_lab_numar_prob_lab(self, v):
        """
        Modifică numărul laboratorului și al problemei.
        :param v: noul număr al laboratorului și al problemei
        """
        self.__numar_lab_numar_prob = v

    def get_nota(self):
        """
        Returnează nota.
        :return: nota
        """
        return self.__nota

    def set_nota(self, v):
        """
        Modifică nota.
        :param v: noua nota
        """
        self.__nota = v



    @classmethod
    def creeaza_laborator(cls, student_id, numar_lab_numar_prob, nota):
        """
        Creează și returnează un obiect de tip Laborator.
        :param student_id: id student
        :param numar_lab_numar_prob: numarul laboratorului si al problemei
        :param nota: nota
        :return: obiect de tip Laborator
        """
        return cls(student_id, numar_lab_numar_prob, nota)

    def __eq__(self, other):
        """
        Verifică egalitatea între două obiecte de tip Laborator.
        :param other: obiectul de comparat
        :return: True dacă obiectele sunt egale, False altfel
        """
        if isinstance(other, Laborator):
            return self.__student_id == other.__student_id and self.__numar_lab_numar_prob == other.__numar_lab_numar_prob
        return False

    def __str__(self):
        """
        Returnează un string cu datele laboratorului.
        :return: descrierea laboratorului sub forma de sir de caractere
        """
        return f"Laborator_Problema: {self.__numar_lab_numar_prob}, StudentID: {self.__student_id}, Nota:{self.__nota}"