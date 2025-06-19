from domain.concurent import lista_concurenti, lista_undo
from ui.console import afiseaza_concurent
from ui.meniu import meniu, meniu1, meniu2, meniu3, meniu4, meniu5, meniu6, meniu_nou, meniu_secundar
from utils.erori.eroare_g import eroare_g
from utils.erori.eroare_s import eroare_s


def run():
    while True:
        meniu_nou()
        optiunem = input(">>>> ").strip()
        if optiunem.lower() == 'a':
            while True:
                meniu()
                optiune = input("Introdu instructiunea: ").strip()
                print()
                if optiune.lower() == 'a':
                    afiseaza_concurent(lista_concurenti)
                    print('-------------------------------------------------------')

                elif optiune.lower() == 'u':
                    for elem in lista_undo:
                        print(elem)
                    print('-------------------------------------------------------')


                elif optiune == '1':
                    meniu1()

                elif optiune == '2':
                    meniu2()

                elif optiune == '3':
                    meniu3()

                elif optiune == '4':
                    meniu4()

                elif optiune == '5':
                    meniu5()

                elif optiune == '6':
                    meniu6()

                elif optiune.lower() == 'b':
                    break

                elif optiune.lower() == 'skibidi':
                    eroare_s()

                elif optiune.lower() == 'gabi' or optiune.lower() == 'gabi mircea' or optiune.lower() == 'mircea':
                    eroare_g()
                else:
                    print("Eroare! Instructiune invalida! ")
                    print('-------------------------------------------------------')
        elif optiunem.lower() == 'b':
            meniu_secundar()
        elif optiunem.lower() == 'e':
            print("Parasire aplicatie...")
            break