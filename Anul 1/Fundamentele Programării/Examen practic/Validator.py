from Domain.Spectacol import Spectacol


class Validator:
    @staticmethod
    def valideaza_spectacol(spectacol: Spectacol):
        """
        Validam corectitudinea unui spectacol introdus
        :param spectacol: spectacolul pe care dorim sa il verifica
        :raises ValueError daca spectacolul nu respecta conditiile
        """
        erori = []
        if spectacol.get_titlu() == "":
            erori.append("Titlul nu poate fi vid!")
        if spectacol.get_artist() == "":
            erori.append("Artistul nu poate fi vid!")
        if spectacol.get_gen() not in ["Comedie", "Concert", "Balet", "Altele"]:
            erori.append("Gen incorect!")
        if spectacol.get_durata() < 0:
            erori.append("Durata trebuie sa fie intreg pozitiv!")

        if len(erori) > 0:
            afiseaza = "\n".join(erori)
            raise ValueError(afiseaza)

    @staticmethod
    def exista_spectacol(spectacol: Spectacol, lista: list):
        """
        Validam existenta unui spectacol in lista de spectacole
        :param spectacol: spectacolul pe care il cautam
        :param lista: lista de spectacole
        :raises ValueError daca spectacolul nu se afla in lista
        """
        if spectacol not in lista:
            raise ValueError("Nu exista spectacolul!")
