import random

from Domain.Spectacol import Spectacol

class ControllerSpectacole:
    def __init__(self, repo, validator):
        """
        Initializam un obiect de tip ControllerSpectacole
        :param repo: repository-ul pe care il mosteneste
        :param validator: validatorul pe care il mosteneste
        """
        self.__repo = repo
        self.__validator = validator

    def afiseaza_spectacole(self):
        """
        Facem rost de lista de spectacole
        :return: lista de spectacole din repository
        """
        return self.__repo.get_all()

    def adauga_spectacol(self, titlu, artist, gen, durata):
        """
        Adaugam un spectacol in lista
        :param titlu: titlul spectacolului
        :param artist: artistul spectacolului
        :param gen: genul spectacolului
        :param durata: durata spectacolului
        """
        s = Spectacol(titlu, artist, gen, durata)
        self.__validator.valideaza_spectacol(s)
        self.__repo.adauga(s)

    def modifica_spectacol(self, titlu, artist, gen, durata):
        """
        Modificam un spectacol din lista
        :param titlu: titlul spectacolului pe care dorim sa il modificam
        :param artist: artistul spectacolului pe care dorim sa il modificam
        :param gen: noul gen
        :param durata: noua durata
        """
        s = Spectacol(titlu, artist, gen, durata)
        self.__validator.valideaza_spectacol(s)
        self.__validator.exista_spectacol(s, self.__repo.get_all())
        self.__repo.modifica(s)

    def genereaza_spectacole(self, n: int):
        """
        Generam aleatoriu spectacole
        :param n: un numar natural
        """
        genuri = ["Comedie", "Concert", "Balet", "Altele"]
        vocale = "aieou"
        consoane = "qwrtypsdfghjklzxcvbnm"
        while n > 0:
            lungime_titlu = random.randint(9, 12)
            prim = lungime_titlu - random.randint(3, 6)
            doi = lungime_titlu - prim - 1
            titlu = ""
            while prim > 0:
                titlu += random.choice(consoane)
                prim -= 1
                if prim == 0:
                    break
                titlu += random.choice(vocale)
                prim -= 1
            titlu += " "
            while doi > 0:
                titlu += random.choice(consoane)
                doi -= 1
                titlu += random.choice(vocale)
                doi -= 1

            lungime_artist = random.randint(9, 12)
            nume = lungime_artist - random.randint(3, 6)
            prenume = lungime_artist - nume - 1
            artist = ""
            while nume > 0:
                artist += random.choice(vocale)
                nume -= 1
                if nume == 0:
                    break
                artist += random.choice(consoane)
                nume -= 1

            artist += " "
            while prenume > 0:
                artist += random.choice(vocale)
                prenume -= 1
                if prenume == 0:
                    break
                artist += random.choice(consoane)
                prenume -= 1
            gen = random.choice(genuri)
            durata = random.randint(1,4)
            s = Spectacol(titlu, artist, gen, durata)
            self.__repo.adauga(s)
            n -= 1

    def exporta(self, nume_fisier):
        """
        Exportam spectacolele sortate dupa artist si titlu intr-un fisier
        :param nume_fisier: numele fisierului in care dorim sa exportam
        """
        lista = self.__repo.get_all()
        lista_sortata = sorted(lista[:], key = lambda x : (x.get_artist(), x.get_titlu()))
        with open(nume_fisier, mode = "w") as f:
            for elem in lista_sortata:
                f.write(elem.get_titlu() + "," + elem.get_artist() + "," + str(elem.get_durata()) + "," + elem.get_gen() + "\n")
