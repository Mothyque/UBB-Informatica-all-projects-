"""
Lista default de concurenti
"""
lista_concurenti = [
    [1, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 55],
    [2, [5, 6, 8, 10, 9, 2, 3, 4, 5, 6], 58],
    [3, [7, 8, 1, 2, 3, 5, 6, 8, 1, 10], 51]
]

lista_undo = []

def creare_concurent(cod, scoruri:list, scor_total):

    """
    Creeaza un concurent
    :param cod: codul concurentului
    :param scoruri: scorurile obtinute la cele 10 probe
    :param scor_total: scorul total format din suma scorurilor de la cele 10 probe
    :return: concurentul nou
    """
    scor_total = sum(scoruri)
    return [cod, scoruri, scor_total]

def get_cod(concurent:list):
    return concurent[0]

def get_scor_proba(concurent:list, proba):
    return concurent[1][proba - 1]

def get_scor_total(concurent:list):
    return concurent[2]

def set_cod(concurent:list, cod):
    concurent[0] = cod

def set_scor_proba(concurent:list, proba, scor):
    concurent[1][proba - 1] = scor

def set_scor_total(concurent:list, scor_total):
    concurent[2] = scor_total

