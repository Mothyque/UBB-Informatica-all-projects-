import random

from Domain.muzica import Muzica


class ControllerMuzica:
    def __init__(self, repository, validator):
        self.__repository = repository
        self.__validator = validator

    def afiseaza_melodii(self):
        return self.__repository.get_all()

    def adauga_melodie(self, titlu, artist, gen, durata):
        m = Muzica(titlu, artist, gen, durata)
        self.__validator.valideaza_melodie(m)
        self.__repository.adauga_muzica(m)


    def modifica_melodie(self, titlu, artist, gen, durata):
        m = Muzica(titlu, artist, gen, durata)
        self.__validator.valideaza_melodie(m)
        self.__validator.exista_melodie(m, self.__repository.get_all())
        self.__repository.modifica_muzica(m)

    def genereaza_melodii(self, n, titluri, artisti):
        for i in range(n):
            titlu = random.choice(titluri)
            artist = random.choice(artisti)
            gen = random.choice(["Rock", "Pop", "Jazz", "Altele"])
            durata = random.randint(1, 10)
            m = Muzica(titlu, artist, gen, durata)
            self.__repository.adauga_muzica(m)

    def exporta_melodii(self, filename):
        lista = self.__repository.get_all()
        lista_aux = lista[:]
        lista_aux = sorted(lista_aux, key = lambda x :(x.get_artist(), x.get_titlu()))
        with open(filename, mode = "w") as f:
            for m in lista_aux:
                f.write(m.get_titlu() + "," + m.get_artist() + "," +  str(m.get_durata()) + "," + m.get_gen() + "\n")