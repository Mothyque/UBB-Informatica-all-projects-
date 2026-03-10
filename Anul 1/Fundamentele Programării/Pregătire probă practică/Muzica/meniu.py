class Meniu:
    def __init__(self, controller):
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        print("A. Afisați melodii.")
        print("1. Modifica melodie.")
        print("2. Genereaza melodii.")
        print("3. Exporteaza melodie in fisier.")
        print("E. Ieșire.")

    def meniu_1(self):
        print("Introdu titlu, artist, gen, durata:")
        date = input(">>> ")
        date = date.strip()
        titlu, artist, gen, durata = date.split(",")
        durata = int(durata)
        self.__controller.modifica_melodie(titlu, artist, gen, durata)

    def meniu_2(self):
        print("Introdu numarul de melodii:")
        n = int(input(">>> "))
        titluri = input(f"Introdu titluri:")
        titluri = titluri.split(",")
        artisti = input(f"Introdu artisti:")
        artisti = artisti.split(",")
        self.__controller.genereaza_melodii(n, titluri, artisti)
        print(f"S-au generat {n} melodii!")

    def meniu_3(self):
        print("Introdu numele fisierului:")
        filename = input(">>> ")
        self.__controller.exporta_melodii(filename)

    def ruleaza_meniu(self):
        while True:
            try:
                self.meniu_principal()
                optiune = input(">>> ")
                if optiune.lower() == 'a':
                    lista = self.__controller.afiseaza_melodii()
                    for m in lista:
                        print(m)
                elif optiune == '1':
                    self.meniu_1()
                elif optiune == '2':
                    self.meniu_2()
                elif optiune == '3':
                    self.meniu_3()
                elif optiune.lower() == 'e':
                    print("Parasire aplicatie ...")
                    break
                else:
                    raise ValueError("Optiune invalida!")
            except ValueError as e:
                print(e)

