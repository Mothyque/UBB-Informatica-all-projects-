from domain.concurent import lista_concurenti, lista_undo, creare_concurent
from domain.validare import validare_scor, validare_proba, validare_interval, validare_cod, validare_undo
from list_manager.concurent_manager import modificare_scor_proba, sterge_scor_proba, filtrare_scor_maxim, \
    filtrare_multiplu
from utils.cautare_multiplu_de_10 import cautare_multiplu_de_10
from utils.media_aritmetica_interval_proba import media_aritmetica_interval_proba
from utils.scor_minim_int_proba import scor_minim_int_proba
from utils.sortare_dupa_scor_proba import sortare_dupa_scor_proba


def afiseaza_concurent(lista):
    for x in lista:
        print(f"Concurentul cu codul {x[0]} a obtinut scorurile {x[1]}, avand un scor total de {x[2]} puncte.")

def adauga_concurent_nou():
    """
    Adauga scorurile pentru un nou participant
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        cod = len(lista_concurenti) + 1
        citire = input("Scorurile sunt: ").split()
        scoruri = []
        for elem in citire:
            validare_scor(int(elem))
            scoruri.append(int(elem))
        concurent = creare_concurent(cod, scoruri, 0)
        lista_concurenti.append(concurent)
        print(f"A fost adaugat concurentul cu codul {len(lista_concurenti)}, care a obtinut scorul total de {lista_concurenti[len(lista_concurenti) - 1][2]} puncte.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def inserare_scor_participant():
    """
    Adauga scor pentru un participant care nu a obtinut inca un scor la o anumita proba
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        instructiuni = input("Introdu codul, proba si scorul: ").split()
        cod = int(instructiuni[0])
        proba = int(instructiuni[1])
        scor = int(instructiuni[2])
        validare_cod(lista_concurenti, cod)
        validare_proba(proba)
        validare_scor(scor)
        modificare_scor_proba(lista_concurenti, cod, proba, scor)
        print(f"Participantul cu codul {cod} a obtinut scorul de {scor} puncte la proba {proba}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def sterge_scor_participant():
    """
    Șterge scorul pentru un participant dat la o anumita proba.
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        citire = input("Introdu codul concurentului si proba: ").split()
        cod = int(citire[0])
        proba = citire[1]
        validare_cod(lista_concurenti, cod)
        validare_proba(proba)
        if proba == 't':
            for x in range(1, 11):
                sterge_scor_proba(lista_concurenti, cod, x)
            print(f"S-a sters scorul total al concurentului cu codul {cod}.")
        else:
            proba = int(proba)
            sterge_scor_proba(lista_concurenti, cod, proba)
            print(f"S-a sters scorul concurentului cu codul {cod} la proba {proba}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def sterge_scor_interval():
    """
    Șterge scorul pentru un interval de participanți la o anumita proba.
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        interval = input("Introdu intervalul si proba: ").split()
        a = int(interval[0])
        b = int(interval[1])
        validare_proba(interval[2])
        validare_interval(lista_concurenti, a, b)
        proba = int(interval[2])
        for x in range(a - 1, b):
            sterge_scor_proba(lista_concurenti, x, proba)
            print(f"S-a sters scorul concurentului cu codul {x + 1} la proba {proba}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def inlocuieste_scor_participant():
    """
    Înlocuiește scorul de la un participant la o anumita proba.
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        instructiuni = input("Introdu codul, proba si scorul: ").split()
        cod = int(instructiuni[0])
        proba = int(instructiuni[1])
        scor = int(instructiuni[2])
        validare_cod(lista_concurenti, cod)
        validare_proba(proba)
        validare_scor(scor)
        modificare_scor_proba(lista_concurenti, cod, proba, scor)
        print(f"Participantului cu codul {cod} i-a fost modificat scorul la proba {proba}. Noul scor este {scor}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def tipareste_participanti_scor_maxim():
    """
    Tipareste participantii care au scor mai mic decat un scor dat pentru o anumita proba
    """
    try:
        ok = 0
        instructiuni = input("Introduceti proba si scorul: ").split()
        proba = instructiuni[0]
        validare_proba(proba)
        scor_maxim = int(instructiuni[1])
        if proba == 't':
            for x in lista_concurenti:
                if x[2] < scor_maxim:
                    ok = 1
                    print(f"Participantul cu codul {x[0]} a obtinut scorul total de {x[2]}.")
        else:
            proba = int(proba)
            for x in lista_concurenti:
                if x[1][proba - 1] < scor_maxim:
                    print(f"Participantul cu codul {x[0]} a obtinut scorul de {x[1][proba - 1]} la proba {proba}.")
                    ok = 1
        if ok == 0:
            print(f"Niciun participant nu are un scor mai mic decat {scor_maxim}")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def ordoneaza_participanti():
    """
    Tipareste participantii ordonati dupa scor la o anumita proba
    """
    try:
        proba = input("Proba este: ")
        validare_proba(proba)
        if proba == 't':
            lista_aux1 = lista_concurenti.copy()
            sortare_dupa_scor_proba(lista_aux1, proba)
            for y in lista_aux1:
                print(f"Concurentul cu codul {y[0]} a obtinut scorul total de {y[2]} puncte.")
        else:
            proba = int(proba)
            lista_aux = lista_concurenti.copy()
            sortare_dupa_scor_proba(lista_aux, proba)
            for x in lista_aux:
                print(f"Concurentul cu codul {x[0]} a obtinut scorul de {x[1][proba - 1]} puncte la proba {proba}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def ordoneaza_participanti_scor_minim():
    """
    Tipareste participantii cu scor mai mare decat un scor dat, ordonati
    crescator dupa scor la o anumita proba
    """
    try:
        instructiuni = input("Introduceti proba si scorul: ").split()
        proba = instructiuni[0]
        validare_proba(proba)
        scor_minim = int(instructiuni[1])
        lista_aux = []
        if proba == 't':
            for concurent in lista_concurenti:
                if concurent[2]  > scor_minim:
                    lista_aux.append(concurent)
            sortare_dupa_scor_proba(lista_aux, proba)
            for concurent in lista_aux:
                print(f"Concurentul cu codul {concurent[0]} a obtinut scorul total de {concurent[2]}")
            if len(lista_aux) == 0:
                print(f"Nu exista niciun participant cu scor mai mare decat {scor_minim}.")
        else:
            proba = int(proba)
            for concurent in lista_concurenti:
                if concurent[1][proba - 1] > scor_minim:
                    lista_aux.append(concurent)
            sortare_dupa_scor_proba(lista_aux, proba)
            for concurent in lista_aux:
                print(f"Concurentul cu codul {concurent[0]} a obtinut {concurent[1][proba - 1]} puncte la proba {proba}.")
            if len(lista_aux) == 0:
                print(f"Nu exista niciun participant cu scor mai mare decat {scor_minim}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def media_intervalului():
    """
    Calculeaza media scorurilor pentru un interval dat la o anumita proba
    """
    try:
        interval_citit = input("Se da intervalul: ").split()
        a = int(interval_citit[0])
        b = int(interval_citit[1])
        proba = input("Proba este: ")
        validare_proba(proba)
        validare_interval(lista_concurenti, a, b)
        media = media_aritmetica_interval_proba(lista_concurenti, a, b, proba)
        if proba == 't':
            print(f"Media scorurilor totale a concurentilor din intervalul {a}, {b} este {media}.")
        else:
            print(f"Media scorurilor totale a concurentilor din intervalul {a}, {b} la proba {proba} este {media}.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def scor_minim_interval():
    """
    Calculeaza scorul minim la o proba pentru un interval de participanti dat
    """
    try:
        interval_citit = input("Se da intervalul: ").split()
        a = int(interval_citit[0])
        b = int(interval_citit[1])
        proba = input("Proba: ")
        validare_proba(proba)
        validare_interval(lista_concurenti, a, b)
        minim = scor_minim_int_proba(lista_concurenti, a, b, proba)
        if proba == 't':
            print(f"Scorul total minim pentru intervalul {a}, {b} este de {minim} puncte.")
        else:
            print(f"Scorul minim la proba {proba} pentru intervalul {a}, {b} este de {minim} puncte.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def interval_multiplu_de_10():
    """
    Tipareste participantii dintr-un interval dat care au scorul
    multiplu de 10
    """
    try:
        interval_citit = input("Se da intervalul: ").split()
        a = int(interval_citit[0])
        b = int(interval_citit[1])
        proba = input("Proba: ")
        validare_proba(proba)
        validare_interval(lista_concurenti, a, b)
        lista_aux = cautare_multiplu_de_10(lista_concurenti, a, b, proba)
        if len(lista_aux) == 0:
            print(f"Nu exista concurent cu scor multiplu de 10 la proba {proba}.")
        else:
            if proba == 't':
                for concurent in lista_aux:
                    print(f"Concurentul cu codul {concurent[0]} a obtinut scorul total de {concurent[2]} puncte, care este un multiplu de 10.")
            else:
                proba = int(proba)
                for concurent in lista_aux:
                    print(
                        f"Concurentul cu codul {concurent[0]} a obtinut scorul de {concurent[1][proba - 1]} puncte la proba {proba}, care este un multiplu de 10.")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')


def filtrare_multiplu_dat():
    """
    Filtreaza participantii care au scorul multiplu unui numar dat
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        multiplu = int(input("Multiplul este: "))
        proba = input("Proba este: ")
        validare_proba(proba)
        filtrare_multiplu(lista_concurenti, multiplu, proba)
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def filtrare_scor_maxim_dat():
    """
    Filtrare participanți care au scorul mai mic decât un scor dat.
    """
    try:
        lista_undo.append([[concurent[0], concurent[1][:], concurent[2]] for concurent in lista_concurenti])
        scor = int(input("Scorul este: "))
        proba = input("Proba este: ")
        validare_proba(proba)
        filtrare_scor_maxim(lista_concurenti, scor, proba)
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')

def undo():
    try:
        validare_undo(lista_undo)
        lista_concurenti.clear()
        lista_concurenti.extend(lista_undo.pop())
        print("Undo reusit")
    except ValueError as e:
        print(f"Eroare: {e}")
    print('-------------------------------------------------------')
