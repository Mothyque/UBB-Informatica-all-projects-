def test():
    assert cerinta2([1, 2, 3, 4, 5, 6]) == [1, 2, 3]
    assert cerinta2([1, 2, 3, 1, 1, 1, 5, 6, 7]) == [1, 2, 3, 1, 1, 1]
    assert cerinta2([1, 1, 1, 1, 1, 1, 1, 1]) == [1, 1, 1, 1, 1, 1, 1, 1]
    assert cerinta2([2, 3, 4]) == [2, 3, 4]
    assert cerinta2([5, 4, 5, 3, 2, 1, 5, 6, 8, 9, 0, 2]) == [5, 4, 5, 3]

    assert cerinta14([12, 1123, 1582, 986, 302]) == [12, 1123, 1582]
    assert cerinta14([67, 987436, 1293]) == [67, 987436, 1293]
    assert cerinta14([1, 1, 1, 1, 1, 1, 1, 1]) == []
    assert cerinta14([123, 234, 345, 456, 567, 678, 789, 890]) == [123, 234, 345, 456, 567, 678, 789, 890]


def meniu():
    print("1. Citire lista de numere intregi")
    print("2. Contine cel mult 3 valori distincte")
    print("3. Oricare doua elemente consecutive au cel putin 2 cifre distincte comune")
    print("4. Contine doar numere prime")
    print("5. Iesire")


def propr2(i, j, lista):
    """
    Verifica daca o sublista de numere din lista are maxim 3 valori distincte
    :param i: indicele de unde incepe sublista
    :param j: indicele unde se termina sublista
    :param lista: sublista de numere
    :return: 1 daca sublista are maxim 3 valori distincte
             0 daca sublista nu are maxim 3 valori distincte
    """

    lista_aux = set(lista[i: j + 1])
    if len(lista_aux) <= 3:
        return 1
    else:
        return 0

def cerinta2(lista):
    """
    Efectueaza cerinta 2, adica se gaseste secventa de lungime maxima
    :param lista: lista de numere
    :return: secventa de lungime maxima cu proprietatea de a contine cel mult 3 valori distincte
    """
    lista_afisare = []
    lista_valabila = []
    k = 0
    lmax = 0
    for x in range(len(lista) - 1):
        if propr2(k, x, lista) == 0:
            lista_valabila.clear()
            lista_valabila = lista[k: x]
            if len(lista_valabila) > lmax:
                lmax = len(lista_valabila)
                lista_afisare.clear()
                lista_afisare = lista_valabila.copy()
            while propr2(k, x, lista) == 0:
                k += 1
    lista_valabila.clear()
    lista_valabila = lista[k: (len(lista))]
    if len(lista_valabila) > lmax and propr2(k, len(lista), lista) == 1:
        lista_afisare.clear()
        lista_afisare = lista_valabila.copy()

    return lista_afisare



def cifre_comune(a, b):
    """
    Verifica daca doua numere au cel putin 2 cifre distincte
    :param a: un numar
    :param b: alt numar
    :return: 1 daca cele doua nunmere au cel putin 2 cifre distincte comune
             0 daca cele doua nunmere au cel putin 2 cifre distincte comune
    """
    fr = [0] * 10

    cnt = 0
    while b != 0:
        ok = 0
        aux = a
        while aux != 0:
            if aux % 10 == b % 10 and fr[aux % 10] == 0:
                fr[aux % 10] += 1
                ok = 1
                break
            aux //= 10
        if ok == 1:
            cnt += 1
        b //= 10
    if cnt >= 2:
        return 1
    else:
        return 0

def cerinta14(lista: list):
    """
    Efectueaza cerinta 14, adica se gaseste cea mai lunga secventa in care oricare doua elemente
    consecutive au cel putin 2 cifre distincte
    :param lista: lista numere
    :return: secventa de lungime maxima in care oricare doua elemente
    consecutive au cel putin 2 cifre distincte
    """
    l = 0
    lmax = 0
    lista_aux = []
    lista_afisare = []
    for x in range(len(lista) - 2):
        if cifre_comune(lista[x], lista[x + 1]) == 1:
            if l == 0:
                l += 2
                lista_aux.append(lista[x])
                lista_aux.append(lista[x + 1])
            else:
                l += 1
                lista_aux.append(lista[x + 1])
        else:
            if l > lmax:
                lista_afisare.clear()
                lmax = l
                lista_afisare = lista_aux.copy()
            lista_aux.clear()
            l = 0
    if l + 1 > lmax and l != 0:
        lista_afisare.clear()
        lista_afisare = lista_aux.copy()
        lista_afisare.append(lista[len(lista) - 1])

    return lista_afisare
def e_prim(n):
    """
    Verifica daca un numar este prim sau nu
    :param n: un numar
    :return: 1 daca este prim
             0 daca nu este prim
    """
    if n < 2:
        return 0
    elif n == 2:
        return 1
    elif n % 2 == 0:
        return 0
    else:
        for x in range (3, n, 2):
            if n % x == 0:
                return 0
        return 1

def cerinta4(lista: list):
    """
    Efectueaza cerinta 4, adica se gaseste secventa maxima care contine doar numere prime
    :param lista: lista de numer
    :return: secventa maxima care contine doar numere prime
    """
    lmax = 0
    lista_aux = []
    lista_afisare = []
    for x in range(0, len(lista)):
        if e_prim(lista[x]) == 1:
            lista_aux.append(lista[x])
        else:
            if len(lista_aux) > lmax:
                lista_afisare.clear()
                lmax = len(lista_aux)
                lista_afisare = lista_aux.copy()
            lista_aux.clear()
    if len(lista_aux) > lmax:
        lista_afisare = lista_aux.copy()
        lista_aux.clear()
    return lista_afisare


def run():
    lista_numere = []
    while True:
        meniu()
        instructiune = input("Introduceti instructiunea: ")
        if instructiune == '1':
            aux_lista = input("Introduceti numerele: ")
            lista_numere = aux_lista.split()
            lista_numere = [int(n) for n in lista_numere]

            print(lista_numere)
        elif instructiune == '2':
            print(cerinta2(lista_numere))
        elif instructiune == '3':
            print(cerinta14(lista_numere))
        elif instructiune == '4':
            print(cerinta4(lista_numere))
        elif instructiune == '5':
            print("Parasire aplicatie...")
            break
        else:
            print("Eroare! Instructiune gresita")

test()
run()
