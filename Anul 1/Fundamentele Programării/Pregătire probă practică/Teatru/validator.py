from Domain.teatru import Teatru


class Validator:
    @staticmethod
    def valideaza_teatru(teatru: Teatru):
        genuri = ["Comedie", "Drama", "Satira", "Altele"]
        errors = []
        if teatru.get_titlu() == "":
            errors.append("Titlul nu poate fi vid!")
        if teatru.get_regizor() == "":
            errors.append("Regizorul nu poate fi vid!")
        if teatru.get_gen() not in genuri:
            errors.append("Gen invalid!")
        if teatru.get_durata() < 0 or type(teatru.get_durata()) != int:
            errors.append("Durata trebuie sa fie un intreg pozitiv.")

        if len(errors) > 0:
            erori = '\n'.join(errors)
            raise ValueError(erori)

    @staticmethod
    def exista_teatru(teatru: Teatru, lista: list):
        if teatru not in lista:
            raise ValueError("Nu exista teatrul!")