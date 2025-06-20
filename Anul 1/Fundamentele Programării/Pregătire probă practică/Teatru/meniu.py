class Meniu:
    def __init__(self, controller):
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        print("A. Afiseaza toate spectacolele.")
        print("1. Adauga piesa.")
        print("2. Modifica piesa.")
        print("3. Genereaza random piese.")
        print("4. Scrie in fisier.")
        print("E. Exit")

    def meniu_1(self):
        print("Introdu titlu, regizor, gen, durata:")
        date = input(">>> ")
        date = date.strip()
        titlu, regizor, gen, durata = date.split(",")
        durata = int(durata)
        self.__controller.adauga_spectacol(titlu, regizor, gen, durata)

    def meniu_2(self):
        print("Introdu, titlu, regizor, gen, durata:")
        date =input(">>> ")
        date = date.strip()
        titlu, regizor, gen, durata = date.split(",")
        durata = int(durata)
        self.__controller.modifica_spectacol(titlu, regizor, gen, durata)

    def meniu_3(self):
        print("Introdu numarul de piese pe care doresti sa le generezi:")
        numar = int(input(">>> "))
        self.__controller.genereaza_piese(numar)

    def meniu_4(self):
        print("Introdu numele fisierului: ")
        nume_fisier = input(">>> ")
        self.__controller.scrie_in_fisier(nume_fisier)

    def ruleaza_meniu(self):
        while True:
            try:
                self.meniu_principal()
                instructiune = input(">>> ")
                if instructiune.lower() == 'a':
                    lista = self.__controller.afiseaza_spectacole()
                    for elem in lista:
                        print(elem)
                elif instructiune == '1':
                    self.meniu_1()
                elif instructiune == '2':
                    self.meniu_2()
                elif instructiune == '3':
                    self.meniu_3()
                elif instructiune == '4':
                    self.meniu_4()
                elif instructiune.lower() == 'e':
                    print("Parasire aplicatie")
                    break
                else:
                    raise ValueError("Instructiune invalida!")
            except ValueError as e:
                print(e)
