from Domain.eveniment import Eveniment
import datetime

class ControllerEvenimente:

    def __init__(self, repo, validator):
        self.__repo = repo
        self.__validator = validator

    def afiseaza_evenimente(self, ziua_curenta):
        lista = self.__repo.get_all()
        lista = sorted(lista, key = lambda x: x.get_ora(), reverse=False)
        for eveniment in lista:
            ziua, luna, an = eveniment.get_data().split(".")
            data = datetime.date(int(an), int(luna), int(ziua))
            if data == ziua_curenta:
                print(eveniment)

    def adauga_eveniment(self, data, ora, descriere):
        e = Eveniment(data, ora, descriere)
        self.__validator.valideaza_eveniment(e)
        self.__repo.adauga_eveniment(e)

    def exporta_evenimente(self, filename, caracter):
        lista = self.__repo.get_all()
        lista_de_scris = []
        for eveniment in lista:
            if caracter in eveniment.get_descriere():
                lista_de_scris.append(eveniment)
        lista_de_scris = sorted(lista_de_scris, key = lambda x: (x.get_data(), x.get_ora()), reverse=False)
        with open(filename, mode = "w") as f:
            for eveniment in lista_de_scris:
                f.write(eveniment.get_data() + "," + eveniment.get_ora() + "," + eveniment.get_descriere() + "\n")
        return lista_de_scris

