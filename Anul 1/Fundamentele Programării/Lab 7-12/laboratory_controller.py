from domain.laborator import Laborator
from domain.validator import Validator
from sortari.comb_sort import comb_sort
from sortari.insertion_sort import insertion_sort


class LaboratoryController:
    def __init__(self, laboratory_repository, problem_repository, student_repository):
        """
        Inițializează o nouă instanță a clasei LaboratoryController.
        :param laboratory_repository: obiect de tip LaboratorRepository
        """
        self.laboratory_repository = laboratory_repository
        self.problem_repository = problem_repository
        self.student_repository = student_repository

    def afiseaza_laboratoare(self):
        """
        Returnează lista de laboratoare.
        :return: lista de laboratoare
        """
        lista_probleme =  self.laboratory_repository.get_all()
        for problema in lista_probleme:
            print(problema)

    def asigneaza_laborator(self, studid, numar_lab_numar_prob):
        """
        Asignează un laborator.
        :param studid: id-ul studentului
        :param numar_lab_numar_prob: numărul laboratorului și al problemei
        """
        Validator.valideaza_id(studid)
        Validator.valideaza_id_existent(studid, self.student_repository.get_lista_iduri())
        Validator.valideaza_numar_lab_numar_problema(numar_lab_numar_prob)
        Validator.valideaza_numar_lab_numar_problema_existent(numar_lab_numar_prob, self.problem_repository.get_lista_probleme())
        lab = Laborator.creeaza_laborator(studid, numar_lab_numar_prob, 0)
        Validator.valideaza_laborator_duplicate(lab, self.get_lista_laboratoare())
        self.laboratory_repository.asignare_laborator(lab)

    def noteaza_laborator(self, studid, numar_lab_numar_prob, nota):
        """
        Notează un laborator.
        :param studid: id-ul studentului
        :param numar_lab_numar_prob: numărul laboratorului și al problemei
        :param nota: nota
        """
        Validator.valideaza_id(studid)
        Validator.valideaza_id_existent(studid, self.student_repository.get_lista_iduri())
        Validator.valideaza_numar_lab_numar_problema(numar_lab_numar_prob)
        Validator.valideaza_numar_lab_numar_problema_existent(numar_lab_numar_prob, self.problem_repository.get_lista_probleme())
        Validator.valideaza_nota(nota)
        Validator.valideaza_exista_laborator(studid, numar_lab_numar_prob, self.get_lista_laboratoare())
        Validator.valideaza_laborator_nenotat(studid, numar_lab_numar_prob, self.get_lista_laboratoare())
        lab = Laborator.creeaza_laborator(studid, numar_lab_numar_prob, nota)
        self.laboratory_repository.notare_laborator(lab)

    def get_lista_iduri(self):
        """
        Returnează lista de id-uri a studenților.
        :return: lista de id-uri a studenților
        """
        lista = []
        for lab in self.laboratory_repository.get_all():
            lista.append(lab.get_student_id_lab())
        Validator.valideaza_lista(lista)
        return lista

    def get_lista_laboratoare(self):
        """
        Returnează lista de laboratoare.
        :return: lista de laboratoare
        """
        return self.laboratory_repository.get_all()

    def sterge_laborator_id(self, studid):
        """
        Șterge un laborator după id-ul studentului.
        :param studid: id-ul studentului
        """
        self.laboratory_repository.sterge_lab_id(studid)

    def sterge_laborator_numar_lab_numar_problema(self, numar_lab_numar_prob):
        """
        Șterge un laborator după numărul laboratorului și numărul problemei.
        :param numar_lab_numar_prob: numărul laboratorului și numărul problemei
        """
        self.laboratory_repository.sterge_lab_numar_lab_numar_probleme(numar_lab_numar_prob)

    def lista_studenti_ordonata(self, ordonare, numar_lab_numar_prob):
        """
        Returnează lista de studenți ordonată după numele acestora.
        :param ordonare: ordinea de sortare
        :param numar_lab_numar_prob: numărul laboratorului și al problemei
        :return: lista de studenți ordonată
        """
        Validator.valideaza_numar_lab_numar_problema(numar_lab_numar_prob)
        Validator.valideaza_numar_lab_numar_problema_existent(numar_lab_numar_prob, self.problem_repository.get_lista_probleme())
        if ordonare == 'nume':
            return self.laboratory_repository.ordoneaza_studenti_nume(numar_lab_numar_prob)
        elif ordonare == 'nota':
            return self.laboratory_repository.ordoneaza_studenti_nota(numar_lab_numar_prob)
        else:
            raise ValueError("Ordinea de sortare nu este valida!")

    def lista_studenti_sub_5(self):
        """
        Returnează lista de studenți care au media notelor sub 5.
        :return: lista de studenți sub forma de liste [nume, media]
        """
        lista = self.laboratory_repository.media_sub_5()
        lista_studenti = []
        for elem in lista:
            nume = self.laboratory_repository.nume_laborator(elem[0])
            lista_studenti.append([nume, elem[1]])
        lista_studenti = insertion_sort(lista_studenti, key=lambda x: x[1], reverse=True)
        return lista_studenti

    def lista_studenti_activi(self):
        """
        Returneaza lista de studenti ordonata dupa numarul de probleme rezolvate
        :return: returneaza o lista de studenti ordonata in functie de numarul de probleme rezolvate
        """
        lista_studenti = []
        for student in self.student_repository.get_all():
            numar_probleme = self.laboratory_repository.numar_probleme_student(student.get_student_id())
            if  numar_probleme != 0:
                lista_studenti.append([student.get_name(), numar_probleme])
        lista_studenti = comb_sort(lista_studenti, key=lambda x: x[1], reverse=True)
        return lista_studenti