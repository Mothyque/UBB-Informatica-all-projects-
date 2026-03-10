class Eveniment:
    def __init__(self, data, ora, descriere):
        self.__data = data
        self.__ora = ora
        self.__descriere = descriere

    def get_data(self):
        return self.__data

    def get_ora(self):
        return self.__ora

    def get_descriere(self):
        return self.__descriere

    def set_data(self, data):
        self.__data = data

    def set_ora(self, ora):
        self.__ora = ora

    def set_descriere(self, descriere):
        self.__descriere = descriere

    def __str__(self):
        return f"Data: {self.__data} | Ora: {self.__ora} | Descriere: {self.__descriere}"

