class Meniu:
    def __init__(self, controller):
        """
        Initializam un obiect de tip Meniu
        :param controller: controller pe care il mosteneste obiectul
        """
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        """
        Meniul principal
        """
        print("A. Afiseaza toate spectacolele.")
        print("1. Adauga spectacol.")
        print("2. Modifica spectacol.")
        print("3. Genereaza spectacole.")
        print("4. Exporta spectacole.")
        print("E. Exit")

    def meniu_1(self):
        """
        Meniul corespunzator instructiunii 1
        """
        print("Introdu titlu, artist, gen, durata: ")
        date = input(">>> ")
        titlu, artist, gen, durata = date.split(",")
        durata = int(durata)
        self.__controller.adauga_spectacol(titlu, artist, gen, durata)
        print("Spectacol adaugat cu succes!")
        print("------------------------------------")

    def meniu_2(self):
        """
        Meniul corespunzator instructiunii 2
        """
        print("Introdu titlu, artist, noul gen si noua durata:")
        date = input(">>> ")
        titlu, artist, gen, durata = date.split(",")
        durata = int(durata)
        self.__controller.modifica_spectacol(titlu, artist, gen, durata)
        print("Spectacol modificat cu succes!")
        print("------------------------------------")

    def meniu_3(self):
        """
        Meniul corespunzator instructiunii 3
        """
        print("Cate spectacole doresti sa generezi?")
        nr = int(input(">>> "))
        self.__controller.genereaza_spectacole(nr)

    def meniu_4(self):
        """
        Meniul corespunzator instructiunii 4
        """
        print("Introdu numele fisierului in care doresti sa exporti datele: ")
        nume_fisier = input(">>> ")
        self.__controller.exporta(nume_fisier)

    def ruleaza_meniu(self):
        """
        Rulam meniul
        :raises ValueError daca instructiunea introdusa nu este valida sau alte date sunt incorecte
        """
        while True:
            try:
                self.meniu_principal()
                print("Introdu instructiune:")
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
                    print("Parasire aplicatie...")
                    break
                else:
                    raise ValueError("Instructiune invalida!")
            except ValueError as e:
                print(e)


