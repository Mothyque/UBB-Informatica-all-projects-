import random

from Domain.emisiune import Emisiune


class ControllerEmisiuni:
    def __init__(self, repo, validator):
        self.__repo = repo
        self.__validator = validator

    def afiseaza_emisiuni(self):
        return self.__repo.get_all()

    def sterge_emisiune(self, nume, tip):
        emisiune_de_sters = Emisiune(nume, tip, 0, "")
        self.__validator.valideaza_exista_emisiune(emisiune_de_sters, self.__repo.get_all())
        self.__repo.sterge_emisiune(emisiune_de_sters)

    def modifica_emisiune(self, nume, tip, durata, descriere):
        emisiune_de_validat = Emisiune(nume, tip, 0, "")
        self.__validator.valideaza_exista_emisiune(emisiune_de_validat, self.__repo.get_all())
        self.__repo.modifica(emisiune_de_validat, durata, descriere)

