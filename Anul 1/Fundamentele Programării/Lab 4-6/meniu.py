from domain.concurent import lista_concurenti
from list_manager.concurent_manager import sterge_scor_proba, filtrare_multiplu
from ui.console import adauga_concurent_nou, inserare_scor_participant, sterge_scor_participant, sterge_scor_interval, \
    inlocuieste_scor_participant, tipareste_participanti_scor_maxim, ordoneaza_participanti, \
    ordoneaza_participanti_scor_minim, media_intervalului, scor_minim_interval, interval_multiplu_de_10, \
    filtrare_multiplu_dat, filtrare_scor_maxim_dat, undo
from utils.media_aritmetica_interval_proba import media_aritmetica_interval_proba


def meniu_nou():
    """
    Meniul nou
    """
    print("A. Meniu clasic")
    print("B. Meniu secundar")
    print("E. Exit")


def meniu_secundar():
    instructiune = input("Introdu instructiunile: ").split(";")
    for elem in instructiune:
        elem = elem.strip()

        if not elem:
            continue

        cuvinte = elem.split()

        try:
            if cuvinte[0] == 'Sterge':
                cod = int(cuvinte[1])
                proba = int(cuvinte[2])
                sterge_scor_proba(lista_concurenti, cod, proba)

            elif cuvinte[0] == 'Undo':
                undo()

            elif cuvinte[0] == 'Filtrare':
                multiplu = int(cuvinte[1])
                proba = int(cuvinte[2])
                filtrare_multiplu(lista_concurenti, multiplu, proba)

            elif cuvinte[0] == 'Medie':
                a = int(cuvinte[1])
                b = int(cuvinte[2])
                proba = cuvinte[3]
                n = media_aritmetica_interval_proba(lista_concurenti, a, b, proba)
                print(f"Media aritmetica a intervalului [{a}, {b}] este {n}.")

            else:
                print("Instructiune invalida!")

        except (IndexError, ValueError) as e:
            print(f"Eroare la procesarea instructiunii '{elem}': {e}")

    print('-------------------------------------------------------')


def meniu():
    """
    Meniul principal
    """
    print("A. Afisare lista concurenti")
    print("1. Adauga un scor la un participant")
    print("2. Modificare scor")
    print("3. Tipareste lista de participanti")
    print("4. Operatii pe un subset de participanti")
    print("5. Filtrare")
    print("6. Undo")
    print("B. Exit")

def meniu1():
    """
    Meniul 1
    """
    while True:
        print("1. Adauga scor pentru un nou participant")
        print("2. Adauga scor pentru un participant la o anumita proba")
        print("B. Back")
        optiune1 = input("Introdu instructiune secundara: ")
        print()
        if optiune1 == '1':
            adauga_concurent_nou()
            break
        elif optiune1 == '2':
            inserare_scor_participant()
            break
        elif optiune1.lower() == 'b':
            break
        else:
            print("Eroare! Instructiune invalida! ")

def meniu2():
    """
    Meniul 2
    """
    while True:
        print("1. Sterge scorul pentru un participant dat")
        print("2. Sterge scorul pentru un interval de participanti")
        print("3. Inlocuieste scorul de la un participant")
        print("B. Back")
        optiune2 = input("Introdu instructiune secundara: ")
        print()
        if optiune2 == '1':
            sterge_scor_participant()
            break
        elif optiune2 == '2':
            sterge_scor_interval()
            break
        elif optiune2 == '3':
            inlocuieste_scor_participant()
            break
        elif optiune2.lower() == 'b':
            break
        else:
            print("Eroare! Instructiune invalida! ")

def meniu3():
    """
    Meniul 3
    """
    while True:
        print("1. Tipareste participantii care au scor mai mic decat un scor dat la o anumita proba")
        print("2. Tipareste participantii ordonati dupa scor la o anumita proba")
        print("3. Tipareste participantii cu scor mai mare decat un scor dat la o anumita proba, ordonati dupa scor")
        print("B. Back")
        optiune3 = input("Introdu instructiune secundara: ")
        print()
        if optiune3 == '1':
            tipareste_participanti_scor_maxim()
            break
        elif optiune3 == '2':
            ordoneaza_participanti()
            break
        elif optiune3 == '3':
            ordoneaza_participanti_scor_minim()
            break
        elif optiune3.lower() == 'b':
            break
        else:
            print("Eroare! Instructiune invalida! ")

def meniu4():
    """
    Meniul 4
    """
    while True:
        print("1. Calculeaza media scorurilor la o anumita proba pentru un interval dat")
        print("2. Calculeaza scorul minim pentru un interval de participanti dat la o anumita proba")
        print("3. Tipareste participantii dintr-un interval dat care au scorul multiplu de 10 la o anumita proba")
        print("B. Back")
        optiune4 = input("Introdu instructiune secundara: ")
        print()
        if optiune4 == '1':
            media_intervalului()
            break
        elif optiune4 == '2':
            scor_minim_interval()
            break
        elif optiune4 == '3':
            interval_multiplu_de_10()
            break
        elif optiune4.lower() == 'b':
            break
        else:
            print("Eroare! Instructiune invalida! ")

def meniu5():
    """
    Meniul 5
    """
    while True:
        print("1. Filtrare participanti care au scorul multiplu unui numar dat la o anumita proba")
        print("2. Filtrare participanti care au scorul mai mic decat un scor dat la o anumita proba")
        print("B. Back")
        optiune4 = input("Introdu instructiune secundara: ")
        print()
        if optiune4 == '1':
            filtrare_multiplu_dat()
            break
        elif optiune4 == '2':
            filtrare_scor_maxim_dat()
            break
        elif optiune4.lower() == 'b':
            break
        else:
            print("Eroare! Instructiune invalida! ")

def meniu6():
    """
    Meniul 6
    """
    undo()
