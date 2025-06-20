import random

from Domain.baschet import Baschet


class ControllerBaschet:
    def __init__(self, repo, validator):
        self.__repo = repo
        self.__validator = validator

    def afiseaza_jucatori(self):
        return self.__repo.get_all()

    def adauga_jucator(self, nume, prenume, inaltime, post):
        jucator = Baschet(nume, prenume, inaltime, post)
        self.__validator.valideaza_jucator(jucator)
        self.__repo.adauga(jucator)

    def modifica_jucator(self, nume, prenume, inaltime):
        jucator = Baschet(nume, prenume, inaltime, "Post")
        self.__validator.exista_jucator(jucator, self.__repo.get_all())
        self.__repo.modifica(jucator)

    def echipa_inaltime_maxim(self):
        lista = self.__repo.get_all()
        lista_fundasi = [x for x in lista if x.get_post().lower() == "fundas"]
        lista_extreme = [x for x in lista if x.get_post().lower() == "extrema"]
        lista_pivoti = [x for x in lista if x.get_post().lower() == "pivot"]
        lista_fundasi = sorted(lista_fundasi, key = lambda x: int(x.get_inaltime()), reverse = True)
        lista_extreme = sorted(lista_extreme, key = lambda x: int(x.get_inaltime()), reverse = True)
        lista_pivoti = sorted(lista_pivoti, key = lambda x: int(x.get_inaltime()), reverse = True)
        lista_finala = lista_fundasi[:2] + lista_extreme[:2] + lista_pivoti[:1]
        return lista_finala

    def genereaza_jucatori(self, filename):
        pozitii = ["Fundas", "Extrema", "Pivot"]
        with open(filename, mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                nume, prenume = line.split(",")
                inaltime = random.randint(175, 225)
                post = random.choice(pozitii)
                jucator = Baschet(nume, prenume, inaltime, post)
                self.__repo.adauga(jucator)