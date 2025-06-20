from Domain.baschet import Baschet


class Validator:
    @staticmethod
    def valideaza_jucator(jucator: Baschet):
        erori = []
        if jucator.get_nume() == "":
            erori.append("Numele nu poate fi vid!")
        if jucator.get_prenume() == "":
            erori.append("Prenumele nu poate fi vid!")
        if jucator.get_inaltime() < 0:
            erori.append("Inaltimea nu poate fi negativa!")
        if jucator.get_post().lower() not in ["fundas", "pivot", "extrema"]:
            erori.append("Postul nu este valid!")

        if len(erori) > 0:
            errors = "\n".join(erori)
            raise ValueError(errors)

    @staticmethod
    def exista_jucator(jucator: Baschet, lista):
        if jucator not in lista:
            raise ValueError("Jucatorul nu exista!")
