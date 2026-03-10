def sortare_dupa_scor_proba(lista_aux, proba):
    """
    Sortează o listă de concurenți în ordine crescătoare după scorul la o anumită probă.
    :param lista_aux: lista de concurenți
    :param proba: proba pentru care se sortează
    """
    if proba == 't':
        ok = False
        while not ok:
            ok = True
            for x in range(len(lista_aux) - 1):
                scor_curent = lista_aux[x][2]
                scor_urmator = lista_aux[x + 1][2]
                if scor_curent > scor_urmator:
                    ok = False
                    lista_aux[x], lista_aux[x + 1] = lista_aux[x + 1], lista_aux[x]
    else:
        ok = False
        while not ok:
            ok = True
            for x in range(len(lista_aux) - 1):
                scor_curent = lista_aux[x][1][proba - 1]
                scor_urmator = lista_aux[x + 1][1][proba - 1]
                if scor_curent > scor_urmator:
                    ok = False
                    lista_aux[x], lista_aux[x + 1] = lista_aux[x + 1], lista_aux[x]
