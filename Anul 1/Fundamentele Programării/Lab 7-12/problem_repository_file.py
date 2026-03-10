from domain.problema import Problema
from repository.problem_repository import ProblemRepository


class ProblemRepositoryFile(ProblemRepository):
    def __init__(self, filename):
        """
        Initalizeaza un nou repository de probleme
        :param filename:
        """
        ProblemRepository.__init__(self)
        ProblemRepository.clear(self)
        self.__filename = filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Incarca datele din fisier
        """
        try:
            with open(self.__filename, "r") as file:
                lines = file.readlines()
                for line in lines:
                    line = line.strip()
                    if line != "":
                        problem = line.split(",")
                        problema = Problema.creeaza_problema(str(problem[0]), problem[1], int(problem[2]))
                        self.adauga_problema(problema)
        except IOError as e:
            raise IOError("Eroare la deschiderea fisierului")

    def __write_to_file(self):
        """
        Scrie datele in fisier
        """
        try:
            with open(self.__filename, "w") as file:
                for problema in self.lista_probleme:
                    file.write(str(problema.get_numar_lab_numar_prob()) + "," + problema.get_descriere() + "," + str(problema.get_deadline()) + "\n")
        except IOError as e:
            raise IOError("Eroare la scrierea in fisier")

    def adauga_problema(self, problema):
        """
        Adauga o problema noua in lista
        :param problema: obiectul Problema de adaugat
        """
        ProblemRepository.adauga_problema(self, problema)
        self.__write_to_file()

    def sterge_problema_numarlab_numarprob(self, nrlabprob: str):
        """
        Sterge problema din lista dupa numarul laboratorului si numarul problemei
        :param nrlabprob: numar laboratorului si al problemei care trebuie stearsa
        :return: True daca problema a fost stearsa, False daca nu a fost gasita
        """
        rezultat = ProblemRepository.sterge_problema_numarlab_numarprob(self, nrlabprob)
        self.__write_to_file()
        return rezultat

    def sterge_problema_deadline(self, deadline: int):
        """
        Sterge toate problemele din lista care au un deadline specificat
        :param deadline: deadline-ul problemelor de sters
        :return: True daca cel putin o problema a fost stearsa, False daca nu a fost gasita niciuna
        """
        rezultat = ProblemRepository.sterge_problema_deadline(self, deadline)
        self.__write_to_file()
        return rezultat

    def modifica_problema_numar_lab_numar_prob(self, numar_curent, numar_nou):
        """
        Modifica numarul laboratorului si al problemei unei probleme specificate
        :param numar_curent: numarul problemei care se doreste a fi modificata
        :param numar_nou: noul numar al problemei
        :return: True daca numarul a fost modificat, False daca problema nu a fost gasita
        """
        rezultat = ProblemRepository.modifica_problema_numar_lab_numar_prob(self, numar_curent, numar_nou)
        self.__write_to_file()
        return rezultat

    def modifica_problema_descriere(self, numar_curent, descriere):
        """
        Modifica descrierea unei probleme specificate
        :param numar_curent: numarul problemei care se doreste a fi modificata
        :param descriere: noua descriere a problemei
        :return: True daca descrierea a fost modificata, False daca problema nu a fost gasita
        """
        rezultat = ProblemRepository.modifica_problema_descriere(self, numar_curent, descriere)
        self.__write_to_file()
        return rezultat
