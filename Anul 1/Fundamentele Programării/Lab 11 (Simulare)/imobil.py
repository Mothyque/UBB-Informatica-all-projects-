class Imobil:
    def __init__(self, idim, adresa, pret_dorit, tip_oferta):
        """
        Constructorul clasei Imobil
        """
        self.__idim = idim
        self.__adresa = adresa
        self.__pret_dorit = pret_dorit
        self.__tip_oferta = tip_oferta

    def get_id(self):
        """
        Returneaza id-ul unui imobil
        :return: id-ul unui imobil
        """
        return self.__idim

    def get_adresa(self):
        """
        Returneaza adresa unui imobil
        :return: adresa unui imobil
        """
        return self.__adresa

    def get_pret(self):
        """
        Returneaza pretul unui imobil
        :return: pretul unui imobil
        """
        return self.__pret_dorit

    def get_oferta(self):
        """
        Returneaza tipul de oferta al unui imobil
        :return: tipul de oferta al unui imobil
        """
        return self.__tip_oferta

    def set_id(self, v):
        """
        Seteaza id-ul unui imobil
        :param v: noul id
        """
        self.__idim = v

    def set_adresa(self, v):
        """
        Seteaza adresa unui imobil
        :param v: noua adresa
        """
        self.__adresa = v

    def set_pret(self, v):
        """
        Seteaza pretul unui imobil
        :param v: noul pret
        """
        self.__pret_dorit = v

    def set_oferta(self, v):
        """
        Seteaza tipul de oferta al unui imobil
        :param v: noul tip de oferta
        """
        self.__tip_oferta = v

    def __str__(self):
        """
        Suprascrie stringul
        :return: Obiectul de tip imobil sub formatul dorit
        """
        return f"ID: {self.__idim}, Adresa: {self.__adresa}, Pret: {self.__pret_dorit}, Tip Oferta: {self.__tip_oferta}."

    def __eq__(self, other):
        """
        Suprascrie egalitatea dintre doua obiecte de tip imobil
        :param other: un alt obiect de tip imobil
        :return: True, daca obiectele au acelasi id, False altfel
        """
        return self.__idim == other.__idim
