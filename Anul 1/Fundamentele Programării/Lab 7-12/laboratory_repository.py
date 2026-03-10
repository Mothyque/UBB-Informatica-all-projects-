from domain.laborator import Laborator
from sortari.insertion_sort import insertion_sort


class LaboratoryRepository:
    def __init__(self, student_repository):
        """
        Inițializează o nouă instanță a clasei LaboratorRepository și adaugă o listă de laboratoare implicită.
        """
        self.__lab = [
            Laborator(1, "3_5", 4),
            Laborator(2, "1_5", 0),
            Laborator(3, "3_5", 4),
            Laborator(1, "2_1", 3),
            Laborator(4, "2_1", 6),
            Laborator(5, "1_5", 5),
            Laborator(7, "3_5", 7),
            Laborator(2, "2_1", 4),
        ]
        self.__student_repository = student_repository

    def get_all(self):
        """
        Returnează lista completă de laboratoare.
        :return: lista de obiecte Laborator
        """
        return self.__lab

    def nume_laborator(self, student_id):
        """
        Returnează numele studentului după id-ul acestuia.
        :param student_id: id-ul studentului
        :return: numele studentului
        """
        student = self.__student_repository.get_student_by_id(student_id)
        if student:
            return student.get_name()
        return None

    def asignare_laborator(self, lab):
        """
        Asignarea unui laborator.
        :param lab: laboratorul
        :return: True daca laboratorul a fost asignat, False altfel
        """
        self.__lab.append(lab)

    def notare_laborator(self, lab):
        """
        Notarea unui laborator.
        :param lab: laboratorul
        :return: True daca laboratorul a fost notat, False altfel
        """
        for laborator in self.__lab:
            if lab == laborator:
                laborator.set_nota(lab.get_nota())
                return True
        return False

    def sterge_lab_id(self, student_id):
        """
        Sterge un laborator dupa id-ul studentului.
        :param student_id: id-ul studentului
        :return: True daca laboratorul a fost sters, False altfel
        """
        length = len(self.__lab)
        self.__lab = [laborator for laborator in self.__lab if laborator.get_student_id_lab() != student_id]
        return length != len(self.__lab)

    def sterge_lab_numar_lab_numar_probleme(self, numar_lab_numar_prob):
        """
        Sterge toate laboratoarele dupa numarul laboratorului si numarul problemei.
        :param numar_lab_numar_prob: numarul laboratorului si numarul problemei
        """
        self.__lab = [laborator for laborator in self.__lab if laborator.get_numar_lab_numar_prob_lab() != numar_lab_numar_prob]

    def ordoneaza_studenti_nume(self, numar_lab_numar_prob):
        """
        Ordoneaza studentii dupa nume.
        :param numar_lab_numar_prob: numarul laboratorului si numarul problemei
        :return: lista de studenti ordonata dupa nume
        """
        lista_studenti = []
        for laborator in self.__lab:
            if laborator.get_numar_lab_numar_prob_lab() == numar_lab_numar_prob and laborator.get_nota() != 0:
                lista_studenti.append([self.nume_laborator(laborator.get_student_id_lab()), laborator.get_nota()])
        lista_studenti = insertion_sort(lista_studenti, key=lambda x: x[0])
        return lista_studenti

    def ordoneaza_studenti_nota(self, numar_lab_numar_prob):
        """
        Ordoneaza studentii dupa nota.
        :param numar_lab_numar_prob: numarul laboratorului si numarul problemei
        :return: lista de studenti ordonata dupa nota
        """
        lista_studenti = []
        for laborator in self.__lab:
            if laborator.get_numar_lab_numar_prob_lab() == numar_lab_numar_prob and laborator.get_nota() != 0:
                lista_studenti.append([self.nume_laborator(laborator.get_student_id_lab()), laborator.get_nota()])
        lista_studenti.sort(key=lambda x: x[1], reverse=True)
        return lista_studenti

    def media_sub_5(self):
        """
        Returneaza media notelor sub 5.
        :return: lista de dictionare cu id-ul studentului si media notelor sub 5
        """
        id_maxim = 0
        lista_studenti_sub_5 = []

        for laborator in self.__lab:
            if laborator.get_student_id_lab() > id_maxim:
                id_maxim = laborator.get_student_id_lab()

        for x in range(1, id_maxim + 1):
            note = []
            for laborator in self.__lab:
                if laborator.get_student_id_lab() == x and laborator.get_nota() != 0:
                    note.append(laborator.get_nota())
            if len(note) != 0:
                media = sum(note) / len(note)
                if media < 5 and media != 0:
                    lista_studenti_sub_5.append([x, media])

        return lista_studenti_sub_5

    def clear(self):
        """
        Șterge toate laboratoarele.
        """
        self.__lab = []

    def numar_probleme_student(self, idstud):
        """
        Returneaza numarul de probleme ale unui student.
        :param idstud: id-ul studentului
        :return: numarul de probleme ale studentului
        """
        count = 0
        for lab in self.__lab:
            if lab.get_student_id_lab() == idstud and lab.get_nota() != 0:
                count += 1
        return count