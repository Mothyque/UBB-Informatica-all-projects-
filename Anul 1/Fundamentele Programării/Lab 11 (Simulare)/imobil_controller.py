class ImobilController:
    def __init__(self, repo):
        self.__repo = repo

    def afiseaza(self):
        """
        Returneaza toate imobilele
        :return: toate imobilele
        """
        return self.__repo.get_all()

    def medie_pret_dorit(self, oferta):
        """
        Returneaza media preturilor pentru o anumita oferta
        :param oferta: tipul ofertei
        :return: media preturilor
        """
        if oferta.lower() != "vanzare" and oferta.lower() != "inchiriere":
            raise ValueError("Tip de oferata invalid!")
        else:
            return self.__repo.medie_oferta(oferta)

    def tranzactie(self, idim, pret):
        """
        Efectueaza o tranzactie
        :param idim: id-ul imobilului
        :param pret: pretul negociat
        :return: adresa imobilului, comisionul
        """
        imobil = self.__repo.find(idim)
        tip_tranzactie = imobil.get_oferta()
        adresa = imobil.get_adresa()
        comision = 0
        if tip_tranzactie == "vanzare":
            comision = 0.02 * pret
        elif tip_tranzactie == "inchiriere":
            comision = 0.5 * pret
        return adresa, comision