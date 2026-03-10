
from Domain.emisiune import Emisiune


class ValidatorEmisiune:
    @staticmethod
    def valideaza_exista_emisiune(emisiune: Emisiune, lista_de_emisiuni):
        ok = 0
        for elem in lista_de_emisiuni:
            if emisiune == elem:
                ok = 1
        if ok == 0:
            raise ValueError("Emisiunea nu exista in lista!")