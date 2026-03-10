from domain.laborator import Laborator
from repository.laboratory_repository import LaboratoryRepository
from repository.student_repository_file import StudentRepositoryFile


class LaboratoryRepositoryFile(LaboratoryRepository):
    def __init__(self, file_name):
        """
        Initializeaza un nou repository de laboratoare care stocheaza datele in fisier
        :param file_name: numele fisiereului in care se vor salva datele
        """
        LaboratoryRepository.__init__(self, StudentRepositoryFile("utils/studenti.txt"))
        LaboratoryRepository.clear(self)
        self.__file_name = file_name
        self.__load_from_file()

    def __load_from_file(self):
        """
        Incarca datele din fisierul specificat in repository
        """
        with open(self.__file_name, "r") as file:
            lines = file.readlines()
            for line in lines:
                if line.strip() == "":
                    continue
                parts = line.strip().split(",")
                student_id = int(parts[0])
                numar_lab_numar_prob = parts[1]
                nota = int(parts[2])
                lab = Laborator(student_id, numar_lab_numar_prob, nota)
                super().asignare_laborator(lab)

    def __write_to_file(self):
        """
        Scrie datele in fisier
        """
        with open(self.__file_name, "w") as file:
            for lab in super().get_all():
                file.write(str(lab.get_student_id_lab()) + "," + lab.get_numar_lab_numar_prob_lab() + "," + str(lab.get_nota()) + "\n")

    def asignare_laborator(self, lab):
        """
        Asignare laborator
        :param lab: laboratorul de asignat
        """
        super().asignare_laborator(lab)
        self.__write_to_file()

    def notare_laborator(self, lab):
        """
        Notare laborator
        :param lab: laboratorul de notat
        :return True daca laboratorul a fost notat, False altfel
        """
        result = super().notare_laborator(lab)
        self.__write_to_file()
        return result

    def ordoneaza_studenti_nota(self, numar_lab_numar_prob):
        """
        Ordoneaza studentii dupa nota
        :param numar_lab_numar_prob: problema la care se doreste ordonarea
        :return: lista de studenti ordonata
        """
        return super().ordoneaza_studenti_nota(numar_lab_numar_prob)

    def media_sub_5(self):
        """
        Creeaza o lista de studenti care au media sub 5
        :return: lista de studenti cu media sub 5
        """
        return super().media_sub_5()

    def sterge_lab_id(self, student_id):
        """
        Sterge un laborator dupa id-ul studentului
        :param student_id: id-ul studentului al carui laborator trebuie sters
        :return: True daca laboratorul a fost sters, False altfel
        """
        result = super().sterge_lab_id(student_id)
        self.__write_to_file()
        return result

    def sterge_lab_numar_lab_numar_probleme(self, numar_lab_numar_prob):
        """
        Sterge toate laboratoarele dupa numarul laboratorului si numarul problemei
        :param numar_lab_numar_prob: problema dupa care se doreste stergerea
        :return: True daca laboratorul a fost sters, False altfel
        """
        result = super().sterge_lab_numar_lab_numar_probleme(numar_lab_numar_prob)
        self.__write_to_file()
        return result