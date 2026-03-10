class Spectacol:
    def __init__(self, titlu: str, artist: str, gen: str, durata: int):
        """
        Initializeaza un obiect de tip spectacol
        :param titlu: titlul spectacolului
        :param artist: artistul spectacolului
        :param gen: genul spectacolului
        :param durata: durata spectacolului
        """
        self.__titlu = titlu
        self.__artist = artist
        self.__gen = gen
        self.__durata = durata

    def get_titlu(self):
        """
        Returneaza titlul unui spectacol
        :return: titlul spectacolului
        """
        return self.__titlu

    def get_artist(self):
        """
        Returneaza artistul unui spectacol
        :return: artistul spectacolului
        """
        return self.__artist

    def get_gen(self):
        """
        Returneaza genul unui spectacol
        :return: genul spectacolului
        """
        return self.__gen

    def get_durata(self):
        """
        Returneaza durata unui spectacol
        :return: durata spectacolului
        """
        return self.__durata

    def set_titlu(self, titlu):
        """
        Seteaza titlul unui spectacol
        :param titlu: noul titlu
        """
        self.__titlu = titlu

    def set_artist(self, artist):
        """
        Seteaza artistul unui spectacol
        :param artist: noul artist
        """
        self.__artist = artist

    def set_gen(self, gen):
        """
        Seteaza genul unui spectacol
        :param gen: noul gen
        """
        self.__gen = gen

    def set_durata(self, durata):
        """
        Seteaza durata unui spectacol
        :param durata: noua durata
        """
        self.__durata = durata

    def __str__(self):
        """
        Redefinim str
        :return: Element de forma Titlu: | Artist: | Gen: | Durata:
        """
        return f"Titlu: {self.__titlu} | Artist: {self.__artist} | Gen: {self.__gen} | Durata: {self.__durata} ore."

    def __eq__(self, other):
        """
        Redefinim eq
        :param other: un alt obiect
        :return: True daca cele doua obiecte au acelasi tip, acelasi titlu si acelasi artist, False altfel
        """
        if type(self) != type(other):
            return False
        return self.__titlu == other.get_titlu() and self.__artist == other.get_artist()





