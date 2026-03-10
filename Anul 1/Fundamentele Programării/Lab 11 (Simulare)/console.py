class Console:
    def __init__(self, controller):
        self.__controller = controller

    @staticmethod
    def meniu_principal():
        """
        Afiseaza meniul principal
        """
        print("A. Afiseaza toate imobilele")
        print("1. Afiseaza media pret dorit")
        print("2. Efectueaza tranzactie")
        print("E. Exit")


    def ruleaza_meniu(self):
        """
        Ruleaza meniul
        """
        while True:
            try:
                self.meniu_principal()
                optiune = input(">>>> ")
                if optiune.lower() == 'a':
                    lista = self.__controller.afiseaza()
                    for elem in lista:
                        print(elem)
                    print("---------------------------------")
                elif optiune == '1':
                    oferta = input("Introdu tipul de oferta: ")
                    print(self.__controller.medie_pret_dorit(oferta))
                    print("---------------------------------")
                elif optiune == '2':
                    idul = int(input("Introdu id: "))
                    pret = int(input("Introdu pretul negociat: "))
                    adresa, comision = self.__controller.tranzactie(idul, pret)
                    print(f"Adresa: {adresa}, Comision: {comision}")
                    print("---------------------------------")
                elif optiune.lower() == 'e':
                    print("Parasire aplicatie....")
                    break
                else:
                    raise ValueError("Instructiune invalida!")
            except ValueError as e:
                print(e)