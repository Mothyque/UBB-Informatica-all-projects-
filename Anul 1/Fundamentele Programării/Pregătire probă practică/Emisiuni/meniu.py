class Meniu:
    def __init__(self, controller):
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        print("A. Afiseaza toate emisiunile.")
        print("1. Sterge emisiune.")
        print("2. Modifica emisiune.")
        print("3. Genereaza program.")
        print("B. Blocheaza emisiune.")
        print("E. Exit")

    def meniu_1(self, blocat):
        print("Instrodu numele si tipul emisiunii, separate de virgula:")
        date = input(">>>> ")
        date = date.strip()
        nume, tip = date.split(",")
        if tip in blocat:
            raise ValueError("Emisiune blocata!")
        self.__controller.sterge_emisiune(nume, tip)

    def meniu_2(self, blocat):
        print("Introdu numele, tipul, durata si descriere emisiunii:")
        date = input(">>>> ")
        date = date.strip()
        nume, tip, durata, descriere = date.split(",")
        durata = int(durata)
        if tip in blocat:
            raise ValueError("Emisiune blocata!")
        self.__controller.modifica_emisiune(nume, tip, durata, descriere)

    def ruleaza_meniu(self):
        blocat = []
        while True:
            try:
                self.meniu_principal()
                print("Introdu instructiune: ")
                instructiune = input(">>>> ")

                if instructiune.lower() == 'a':
                    lista_de_afisat = self.__controller.afiseaza_emisiuni()
                    for elem in lista_de_afisat:
                        print(elem)
                elif instructiune.lower() == 'b':
                    print("Introdu tipul emisiunii pe care vrei sa il blochezi: ")
                    date = input(">>>> ").strip()
                    if date in blocat:
                        raise ValueError("Emisiunea este deja blocata!")
                    blocat.append(date)
                elif instructiune == '1':
                    self.meniu_1(blocat)
                elif instructiune == '2':
                    self.meniu_2(blocat)
                elif instructiune == 'e':
                    print("Parasire aplicatie...")
                    break
                else:
                    raise ValueError("Instructiune invalida!")
            except ValueError as e:
                print(e)

