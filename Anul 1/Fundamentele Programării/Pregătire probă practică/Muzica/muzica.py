class Muzica:
    def __init__(self, titlu, artist, gen, durata):
        self.__titlu = titlu
        self.__artist = artist
        self.__gen = gen
        self.__durata = durata

    def get_titlu(self):
        return self.__titlu

    def get_artist(self):
        return self.__artist

    def get_gen(self):
        return self.__gen

    def get_durata(self):
        return self.__durata

    def set_titlu(self, v):
        self.__titlu = v

    def set_artist(self, v):
        self.__artist = v

    def set_gen(self, v):
        self.__gen = v

    def set_durata(self, v):
        self.__durata = v

    def __str__(self):
        return f"Titlu: {self.__titlu} | Artist: {self.__artist} | Gen: {self.__gen} | Durata: {self.__durata}"

    def __eq__(self, other):
        return self.__titlu == other.get_titlu() and self.__artist == other.get_artist()

