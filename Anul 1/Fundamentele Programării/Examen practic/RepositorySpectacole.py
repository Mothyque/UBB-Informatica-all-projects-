from Domain.Spectacol import Spectacol


class RepositorySpectacole:
    def __init__(self):
        """
        Initializam un obiect de tip RepositorySpectacole
        """
        self.__lista_spectacole = []

    def get_all(self):
        """
        Facem rost de lista de spectacole
        :return: lista de spectacole
        """
        return self.__lista_spectacole

    def adauga(self, spectacol: Spectacol):
        """
        Adaugam un spectacol nou in lista
        :param spectacol: spectacolul pe care dorim sa il adaugam
        """
        self.__lista_spectacole.append(spectacol)

    def modifica(self, spectacol: Spectacol):
        """
        Modificam un spectacol din lista
        :param spectacol: spectacolul pe care dorim sa il modificam
        :return: True daca spectacolul a fost modificat, False altfel
        """
        for s in self.__lista_spectacole:
            if s == spectacol:
                s.set_gen(spectacol.get_gen())
                s.set_durata(spectacol.get_durata())
                return True
        return False

class RepositorySpectacoleFile(RepositorySpectacole):
    def __init__(self, filename):
        """
        Initializam un obiect de tip RepositorySpectacole
        :param filename: numele fisierului
        """
        super().__init__()
        self.__filename = filename
        self.load_from_file()

    def load_from_file(self):
        """
        Incarcam datele din fisier
        """
        with open(self.__filename, mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    titlu, artist, gen, durata = line.split(",")
                    durata = int(durata)
                    s = Spectacol(titlu, artist, gen, durata)
                    super().adauga(s)

    def store_to_file(self):
        """
        Salvam datele in fisier
        """
        with open(self.__filename, mode = "w") as f:
            for spectacol in self.get_all():
                f.write(spectacol.get_titlu() + "," + spectacol.get_artist() + "," + spectacol.get_gen() + "," + str(spectacol.get_durata()) + "\n")

    def adauga(self, spectacol:Spectacol):
        """
        Redefinim adauga pentru a salva modificarile in fisier
        :param spectacol: spectacolul pe care dorim sa il adaugam
        """
        super().adauga(spectacol)
        self.store_to_file()

    def modifica(self, spectacol: Spectacol):
        """
        Redefinim modifica pentru a salva modificarile in fisier
        :param spectacol: spectacolul pe care dorim sa il modificam
        """
        super().modifica(spectacol)
        self.store_to_file()