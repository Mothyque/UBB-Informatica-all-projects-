from Domain.eveniment import Eveniment


class RepositoryEvenimente:
    def __init__(self):
        self.__evenimente = []

    def adauga_eveniment(self, eveniment: Eveniment):
        self.__evenimente.append(eveniment)

    def get_all(self):
        return self.__evenimente

class RepositoryEvenimenteFile(RepositoryEvenimente):
    def __init__(self, filename):
        self.__filename = filename
        super().__init__()
        self.load_from_file()

    def load_from_file(self):
        with open(self.__filename, mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    data, ora, descriere = line.split(",")
                    e = Eveniment(data, ora, descriere)
                    super().adauga_eveniment(e)

    def store_to_file(self):
        with open(self.__filename, mode = "w") as f:
            for eveniment in self.get_all():
                f.write(eveniment.get_data() + "," + eveniment.get_ora() + "," + eveniment.get_descriere() + "\n")

    def adauga_eveniment(self, eveniment: Eveniment):
        super().adauga_eveniment(eveniment)
        self.store_to_file()
