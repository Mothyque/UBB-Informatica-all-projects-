from Domain.muzica import Muzica


class Validator:

    @staticmethod
    def valideaza_melodie(melodie: Muzica):
        erori = []
        genuri = ["Rock", "Pop", "Jazz", "Altele"]
        if melodie.get_titlu() == "":
            erori.append("Titlul nu poate fi vid!")
        if melodie.get_artist() == "":
            erori.append("Artistul nu poate fi vid!")
        if type(melodie.get_durata()) != int or melodie.get_durata() <= 0:
            erori.append("Durata trebuie sa fie un numar intreg pozitiv!")
        if melodie.get_gen() not in genuri:
            erori.append("Genul trebuie sa fie Rock, Pop, Jazz sau Altele!")

        if len(erori) > 0:
            erorrs = "\n".join(erori)
            raise ValueError(erorrs)

    @staticmethod
    def exista_melodie(melodie: Muzica, lista):
        if melodie not in lista:
            raise ValueError("Melodia nu exista!")