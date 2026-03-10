class Meniu:
    def __init__(self, controller):
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        print("A. Afiseaza jucatorii")
        print("1. Adauga jucator")
        print("2. Modifica inaltimea unui jucator")
        print("3. Genereaza cea mai inalta echipa")
        print("4. Genereaza jucatori")
        print("E. Exit")

    def meniu_1(self):
        print("Introduceti datele jucatorului (nume, prenume, inaltime, post): ")
        date = input(">>> ")
        nume, prenume, inaltime, post = date.split(",")
        inaltime = int(inaltime)
        self.__controller.adauga_jucator(nume, prenume, inaltime, post)

    def meniu_2(self):
        print("Introdu nume, prenume, inaltimea noua: ")
        date = input(">>> ")
        nume, prenume, inaltime = date.split(",")
        inaltime = int(inaltime)
        self.__controller.modifica_jucator(nume, prenume, inaltime)

    def ruleaza(self):
        while True:
            try:
                self.meniu_principal()
                optiune = input("Optiunea: ")
                if optiune.lower() == "a":
                    lista = self.__controller.afiseaza_jucatori()
                    for jucator in lista:
                        print(jucator)
                elif optiune == "1":
                    self.meniu_1()
                elif optiune == "2":
                    self.meniu_2()
                elif optiune == "3":
                    lista = self.__controller.echipa_inaltime_maxim()
                    for jucator in lista:
                        print(jucator)
                elif optiune == "4":
                    self.__controller.genereaza_jucatori("Fisier\\jucatori_noi.txt")
                elif optiune.lower() == "e":
                    print("Parasire aplicatie...")
                    break
                else:
                    raise ValueError("Optiune invalida!")
            except ValueError as e:
                print(e)