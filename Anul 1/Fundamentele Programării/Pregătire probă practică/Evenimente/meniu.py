import datetime


class Meniu:

    def __init__(self, controller):
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        print("1. Adauga eveniment")
        print("2. Schimba ziua de afisare")
        print("3. Exporta evenimente")
        print("E. Exit")

    def ruleaza_meniu(self):
        zi = datetime.date.today()
        self.__controller.afiseaza_evenimente(zi)
        while True:
            try:
                self.meniu_principal()
                print("Introdu instructiunea:")
                optiune = input(">>> ")
                if optiune == "1":
                    print("Introdu data ora si descriere eveniment:")
                    date = input(">>> ")
                    data, ora, descriere = date.split(",")
                    self.__controller.adauga_eveniment(data, ora, descriere)
                elif optiune == "2":
                    print("Introdu data de afisare:")
                    data = input(">>> ")
                    ziua, luna, an = data.split(".")
                    zi = datetime.date(int(an), int(luna), int(ziua))
                    self.__controller.afiseaza_evenimente(zi)
                elif optiune == "3":
                    print("Introdu numele fisierului si caracterul:")
                    date = input(">>> ")
                    filename, caracter = date.split(",")
                    self.__controller.exporta_evenimente(filename, caracter)

                elif optiune.lower() == "e":
                    print("Parasire aplicatie...")
                    break
                else:
                    raise ValueError("Optiune invalida")
            except ValueError as e:
                print(e)