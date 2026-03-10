from Domain.teatru import Teatru


class RepositoryTeatru:
    def __init__(self):
        self.__lista_teatru = []

    def get_all(self):
        return self.__lista_teatru

    def adauga_teatru(self, teatru: Teatru):
        self.__lista_teatru.append(teatru)

    def modifica_teatru(self, teatru: Teatru, teatru_nou:Teatru):
        for spectacole in self.__lista_teatru:
            if spectacole == teatru:
                spectacole.set_gen(teatru_nou.get_gen())
                spectacole.set_durata(teatru_nou.get_durata())



    def sterge_teatru(self, teatru: Teatru):
        self.__lista_teatru.remove(teatru)


class RepositoryTeatruFile(RepositoryTeatru):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self.load_from_file()

    def adauga_teatru(self, teatru: Teatru):
        super().adauga_teatru(teatru)
        self.store_to_file()

    def modifica_teatru(self, teatru: Teatru, teatru_nou:Teatru):
        super().modifica_teatru(teatru, teatru_nou)
        self.store_to_file()


    def load_from_file(self):
        with open(self.__filename, mode  = "r") as f:
            lines = f.readlines()
            for linie in lines:
                linie = linie.strip()
                titlu, regizor, gen, durata = linie.split(",")
                t = Teatru(titlu, regizor, gen, durata)
                super().adauga_teatru(t)

    def store_to_file(self):
        with open(self.__filename, mode = "w") as file:
            for teatru in self.get_all():
                file.write(teatru.get_titlu() + "," + teatru.get_regizor() + "," + teatru.get_gen() + "," + str(teatru.get_durata()) + "\n")