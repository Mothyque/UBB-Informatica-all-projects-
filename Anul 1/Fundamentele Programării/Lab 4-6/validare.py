from multiprocessing.managers import Value


def validare_scor(scor):
    """
    Valideaza scorul introdus la o proba
    :param scor: scorul obtinut la proba
    :return: -
    :raises: Value error cu mesajul corespunzator in cazul in care scorul nu este valid
    """
    if  scor < 1 or scor > 10:
        raise ValueError("Scor incorect. Scorul trebuie sa ia valori intre 1 si 10.")

def validare_interval(lista, a, b):
    """
    Valideaza intervalul dintr-o lista
    :param lista: lista in care se afla intervalul
    :param a: capatul inferior al intervalului
    :param b: capatul superior al intervalului
    :return: -
    :raises: Value error cu mesajul corespunzator in cazul in care intervalul nu este valid
    """
    if a > b:
        raise ValueError("Interval invalid! Capatul inferior este mai mare decat cel superior!")
    if a < 1:
        raise ValueError("Interval invalid! Capatul inferior este prea mic!")
    if b > len(lista):
        raise ValueError("Interval invalid! Capatul superior este prea mare!")

def validare_proba(proba):
    """
    Valideaza daca proba exista (este cuprinsa intre 1 si 10)
    :param proba: proba
    :return: -
    :raises: ValueError cu mesajul corespunzator in cazul in care proba nu este valida
    """
    if proba == 't':
        return
    elif proba.isdigit():
        proba = int(proba)
        if proba < 1 or proba > 10:
            raise ValueError("Proba incorecta. Proba trebuie sa ia valori intre 1 si 10.")
    else:
        raise ValueError("Proba incorecta. Proba trebuie sa fie un numar natural intre 1 si 10.")


def validare_cod(lista, cod):
    """
    Valideaza daca codul exista
    :param lista: lista participantilor
    :param cod: codul participantului
    :return: -
    :raises: Value error cu mesajul corespunzator in cazul in care codul nu este valid
    """
    if cod < 1 or cod > len(lista) - 1:
        raise ValueError("Nu exista participant cu acest cod.")

def validare_undo(lista):
    """
    Valideaza daca mai sunt posibile operatii de undo
    :param lista: lista de undo
    :return:
    :raises: Value error daca lista de undo este goala
    """
    if len(lista) == 0:
        raise ValueError("Nu se mai pot face operatii de undo!")