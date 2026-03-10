def cautare_multiplu_de_10(lista, a, b, proba):
    """
    Cauta toate scorurile multiple de 10 si le salveaza intr-o lista
    :param lista: lista de concurenti
    :param a: capatul inferior al intervalului
    :param b: capatul superior al intervalului
    :param proba: proba
    :return: Lista cu concurentii care au scorul la o proba multiplu de 10
    """
    lista_aux = []
    if proba == 't':
        for concurent in lista:
            if a - 1 <= concurent[0] <= b:
                if concurent[2] % 10 == 0:
                    lista_aux.append(concurent)
    else:
        proba = int(proba)
        for concurent in lista:
            if a - 1 <= concurent[0] <= b:
                if concurent[1][proba - 1] % 10 == 0 and concurent[1][proba - 1] != 0:
                    lista_aux.append(concurent)
    return lista_aux