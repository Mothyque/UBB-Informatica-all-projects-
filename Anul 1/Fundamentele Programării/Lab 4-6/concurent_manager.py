from domain.concurent import get_scor_proba, get_cod, creare_concurent, set_scor_proba, set_scor_total


def modificare_scor_proba(lista: list, cod, proba, scor):
    """
    Modifică scorul la o probă a unui concurent
    :param lista: lista de concurenti
    :param cod: codul concurentului
    :param proba: proba la care concurentul a obtinut scorul
    :param scor: scorul pe care l-a obtinut concurentul la proba mentionata
    """
    concurent = lista[cod - 1]

    set_scor_proba(concurent, proba, scor)

    scor_total = sum(concurent[1])
    set_scor_total(concurent, scor_total)


def filtrare_multiplu(lista, multiplu, proba):
    """
    Filtreaza participantii care au scorul multiplu al unui numar dat la o anumita proba
    :param lista: lista de concurenti
    :param multiplu: multiplul
    :param proba: proba
    :return: lista modificata dupa cerinte
    """
    if proba == 't':
        for elem in lista:
            if elem[2] % multiplu != 0:
                for x in range(1, 11):
                    modificare_scor_proba(lista, elem[0], x, 0)
    else:
        proba = int(proba)
        for elem in lista:
            if elem[1][proba - 1] % multiplu != 0:
                modificare_scor_proba(lista, elem[0], proba, 0)

    return lista

def filtrare_scor_maxim(lista, minim, proba):
    """
        Filtreaza participantii care au scorul mai mic ca un numar dat la o anumita proba
        :param lista: lista de concurenti
        :param minim: scorul minim pe care trebuie sa il aiba concurentii
        :param proba: proba
        :return: lista modificata dupa cerinte
        """
    if proba == 't':
        for elem in lista:
            if elem[2] < minim:
                for x in range(1, 11):
                    modificare_scor_proba(lista, elem[0], x, 0)
    else:
        proba = int(proba)
        for elem in lista:
            if elem[1][proba - 1] < minim:
                modificare_scor_proba(lista, elem[0], proba, 0)

    return lista


def sterge_scor_proba(lista, cod, proba):
    modificare_scor_proba(lista, cod, proba, 0)
