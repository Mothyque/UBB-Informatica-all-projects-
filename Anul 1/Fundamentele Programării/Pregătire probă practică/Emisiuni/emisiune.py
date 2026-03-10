class Emisiune:
    def __init__(self, nume, tip, durata, descriere):
        self.__nume = nume
        self.__tip = tip
        self.__durata = durata
        self.__descriere = descriere

    def get_nume(self):
        return self.__nume

    def get_tip(self):
        return self.__tip

    def get_durata(self):
        return self.__durata

    def get_descriere(self):
        return self.__descriere

    def set_nume(self, v):
        self.__nume = v

    def set_tip(self, v):
        self.__tip = v

    def set_durata(self, v):
        self.__durata = v

    def set_descriere(self, v):
        self.__descriere = v

    def __eq__(self, other):
        return self.__nume == other.get_nume() and self.__tip == other.get_tip()

    def __str__(self):
        return f"Nume: {self.__nume}, Tip: {self.__tip}, Durata: {self.__durata} ore, Descirere: {self.__descriere}."