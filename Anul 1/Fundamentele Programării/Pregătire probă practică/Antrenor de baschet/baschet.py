class Baschet:
    def __init__(self, nume, prenume, inaltime, post):
        self.__nume = nume
        self.__prenume = prenume
        self.__inaltime = inaltime
        self.__post = post

    def get_nume(self):
        return self.__nume

    def get_prenume(self):
        return self.__prenume

    def get_inaltime(self):
        return self.__inaltime

    def get_post(self):
        return self.__post

    def set_nume(self, v):
        self.__nume = v

    def set_prenume(self, v):
        self.__prenume = v

    def set_inaltime(self, v):
        self.__inaltime = v

    def set_post(self, v):
        self.__post = v

    def __str__(self):
        return f"Nume: {self.__nume} | Prenume: {self.__prenume} | Inaltime: {self.__inaltime} cm | Post: {self.__post}"

    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return self.__nume == other.__nume and self.__prenume == other.__prenume
