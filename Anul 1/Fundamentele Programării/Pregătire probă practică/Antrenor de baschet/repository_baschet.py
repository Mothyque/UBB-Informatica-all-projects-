from Domain.baschet import Baschet


class RepositorBaschet:
    def __init__(self):
        self.__jucatori = []

    def adauga(self, jucator: Baschet):
        self.__jucatori.append(jucator)

    def get_all(self):
        return self.__jucatori

    def modifica(self, jucator_nou: Baschet):
        for jucator in self.get_all():
            if jucator == jucator_nou:
                jucator.set_inaltime(jucator_nou.get_inaltime())

class RepositoryBaschetFile(RepositorBaschet):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self.load_from_file()

    def load_from_file(self):
        with open(self.__filename, mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                nume, prenume, inaltime, post = line.split(",")
                inaltime = int(inaltime)
                b = Baschet(nume, prenume, inaltime, post)
                super().adauga(b)

    def adauga(self, jucator: Baschet):
        super().adauga(jucator)
        self.store_to_file()

    def modifica(self, jucator_nou: Baschet):
        super().modifica(jucator_nou)
        self.store_to_file()

    def store_to_file(self):
        with open(self.__filename, mode = "w") as f:
            for b in self.get_all():
                f.write(f"{b.get_nume()},{b.get_prenume()},{b.get_inaltime()},{b.get_post()}\n")