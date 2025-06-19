def scor_minim_int_proba(lista, a, b, proba):
    """
    Afla scorul minim la o anumita proba pe un interval de concurenti
    :param lista: lista de concurenti
    :param a: capatul superior al intervalului
    :param b: capatul inferior al intervalului
    :param proba: proba
    :return: minimul
    """
    if proba == 't':
        minim = 101
        for concurent in lista:
            if a <= concurent[0] <= b:
                if minim > concurent[2]:
                    minim = concurent[2]
    else:
        proba = int(proba)
        minim = 11
        for concurent in lista:
            if a <= concurent[0] <= b:
                if minim > concurent[1][proba - 1]:
                    minim = concurent[1][proba - 1]

    return minim