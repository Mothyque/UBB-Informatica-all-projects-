def media_aritmetica_interval_proba(lista, a, b, proba):
    """
    Calculeaza media aritmetica a elementelor dintr-o lista
    :param lista: o lista cu numere
    :param a: capatul inferior al intervalului
    :param b: capatul superior al intervalului
    :param proba: proba la care facem media aritmetica a scorurilor
    :return: media aritmetica a numerelor
    """
    suma = 0
    cnt = 0
    if proba == 't':
        for element in lista:
            if a <= element[0] <= b:
                suma += element[2]
                cnt += 1
    else:
        proba = int(proba)
        for element in lista:
            if a <= element[0] <= b:
                suma += element[1][proba - 1]
                cnt += 1

    return suma / cnt
