from Domain.emisiune import Emisiune


class RepositoryEmisiuni:
    def __init__(self):
        self.__lista_emisiune = []

    def get_all(self):
        return self.__lista_emisiune

    def adauga_emisiune(self, emisiune: Emisiune):
        self.__lista_emisiune.append(emisiune)

    def modifica(self, emisiune: Emisiune, durata, descriere):
        for elem in self.get_all():
            if elem  == emisiune:
                elem.set_durata(durata)
                elem.set_descriere(descriere)

    def sterge_emisiune(self, emisiune):
        self.__lista_emisiune.remove(emisiune)


class RepositoryFileEmisiuni(RepositoryEmisiuni):
    def __init__(self, file_name):
        super().__init__()
        self.__file_name = file_name
        self.load_from_file()

    def sterge_emisiune(self, emisiune):
        super().sterge_emisiune(emisiune)
        self.save_to_file()

    def modifica(self, emisiune: Emisiune, durata, descriere):
        super().modifica(emisiune, durata, descriere)
        self.save_to_file()

    def load_from_file(self):
        with open(self.__file_name, mode="r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    nume, tip, durata, descriere = line.split(",")
                    durata = int(durata)
                    e = Emisiune(nume, tip, durata, descriere)
                    super().adauga_emisiune(e)

    def save_to_file(self):
        with open(self.__file_name, "w") as file:
            for emisiune in self.get_all():
                file.write(emisiune.get_nume() + "," + emisiune.get_tip() + "," + str(emisiune.get_durata()) + "," + emisiune.get_descriere() + "\n")
