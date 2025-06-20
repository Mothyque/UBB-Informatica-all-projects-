
from Domain.muzica import Muzica


class RepositoryMuzica:
    def __init__(self):
        self.__muzica = []

    def adauga_muzica(self, muzica: Muzica):
        self.__muzica.append(muzica)

    def modifica_muzica(self, muzica: Muzica):
        for piesa in self.__muzica:
            if piesa == muzica:
                piesa.set_gen(muzica.get_gen())
                piesa.set_durata(muzica.get_durata())

    def get_all(self):
        return self.__muzica

class RepositoryFileMuzica(RepositoryMuzica):
    def __init__(self, filename):
        self.__filename = filename
        super().__init__()
        self.load_from_file()

    def adauga_muzica(self, muzica):
        super().adauga_muzica(muzica)
        self.store_to_file()

    def modifica_muzica(self, muzica: Muzica):
        super().modifica_muzica(muzica)
        self.store_to_file()

    def load_from_file(self):
        with open(self.__filename, mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    titlu, artist, gen, durata = line.split(",")
                    m = Muzica(titlu, artist, gen, durata)
                    super().adauga_muzica(m)

    def store_to_file(self):
        with open(self.__filename, mode = "w") as f:
            for m in self.get_all():
                f.write(m.get_titlu() + "," + m.get_artist() + "," + m.get_gen() + "," + str(m.get_durata()) + "\n")