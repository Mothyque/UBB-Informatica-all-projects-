class Teatru:
    def __init__(self, titlu, regizor, gen, durata):
        self.__titlu = titlu
        self.__regizor = regizor
        self.__gen = gen
        self.__durata = durata

    def get_titlu(self):
        return self.__titlu

    def get_regizor(self):
        return self.__regizor

    def get_gen(self):
        return self.__gen

    def get_durata(self):
        return self.__durata

    def set_titlu(self, v):
        self.__titlu = v

    def set_regizor(self, v):
        self.__regizor = v

    def set_gen(self, v):
        self.__gen = v

    def set_durata(self, v):
        self.__durata = v

    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return self.__titlu == other.get_titlu() and self.__regizor == other.get_regizor()

    def __str__(self):
        return f"Titlu: {self.__titlu}, Regizor: {self.__regizor}, Gen: {self.__gen}, Durata: {self.__durata} ore."
