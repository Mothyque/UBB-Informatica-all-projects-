import random

from Domain.teatru import Teatru


class ControllerTeatru:
    def __init__(self, repository, validator):
        self.__repository = repository
        self.__validator = validator

    def afiseaza_spectacole(self):
        return self.__repository.get_all()

    def adauga_spectacol(self, titlu, regizor, gen, durata):
        t = Teatru(titlu, regizor, gen, durata)
        self.__validator.valideaza_teatru(t)
        self.__repository.adauga_teatru(t)

    def modifica_spectacol(self, titlu, regizor, gen, durata):
        verifica = Teatru(titlu, regizor, "", 0)
        self.__validator.exista_teatru(verifica, self.__repository.get_all())
        teatru_nou = Teatru(titlu, regizor, gen, durata)
        self.__validator.valideaza_teatru(teatru_nou)
        self.__repository.modifica_teatru(verifica, teatru_nou)

    def genereaza_piese(self, numar):
        genuri = ["Comedie", "Altul", "Satira", "Drama"]
        vocale = "aeiou"
        consoane = "qwrtypsdfghjklzxcvbnm"
        for x in range(numar):
            lungime_titlu = random.randint(8, 12)
            lungime_regizor = random.randint(8, 12)
            spatiu_film = lungime_titlu // 2  + 1
            spatiu_regizor = lungime_regizor // 2
            titlu = ""
            regizor = ""
            while lungime_titlu > 0:
                if lungime_titlu == spatiu_film:
                    titlu += " "
                    lungime_titlu -= 1
                titlu += random.choice(vocale)
                lungime_titlu -= 1
                if lungime_titlu == spatiu_film:
                    titlu += " "
                    lungime_titlu -= 1
                titlu += random.choice(consoane)
                lungime_titlu -= 1

            while lungime_regizor > 0:
                if lungime_regizor == spatiu_regizor:
                    regizor += " "
                    lungime_regizor -= 1
                regizor += random.choice(consoane)
                lungime_regizor -= 1
                if lungime_regizor == spatiu_regizor:
                    regizor += " "
                    lungime_regizor -= 1
                regizor += random.choice(vocale)
                lungime_regizor -= 1

            gen = random.choice(genuri)
            durata = random.randint(1, 4)
            teatru_nou = Teatru(titlu, regizor, gen, durata)
            self.__repository.adauga_teatru(teatru_nou)

    def scrie_in_fisier(self, nume_fisier):
        lista = self.__repository.get_all()
        lista_de_scris = lista[:]
        lista_de_scris = sorted(lista_de_scris, key = lambda x: (x.get_regizor(), x.get_titlu()))
        with open(nume_fisier, mode  = "w") as f:
            for element in lista_de_scris:
                f.write(element.get_titlu() + "," + element.get_regizor() + "," + str(element.get_durata()) + "," + element.get_gen() + "\n")
        return lista_de_scris