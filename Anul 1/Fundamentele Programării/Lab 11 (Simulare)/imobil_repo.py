from domain.imobil import Imobil


class ImobilRepo:
    def __init__(self):
        """
        Constructorul clasei ImobilRepo
        """
        self.__imobile = []

    def store(self, i: Imobil):
        """
        Adauga un nou obiect de tip imobil in lista
        :param i: noul obiect
        """
        self.__imobile.append(i)

    def get_all(self):
        """
        Returneaza toate obiectele de tip imobil din lista
        :return: toate imobilele
        """
        return self.__imobile

class ImobilRepoFile(ImobilRepo):
    def __init__(self, filename):
        """
        Constructorul clasei ImobilRepoFile
        :param filename: numele fisierului
        """
        super().__init__()
        self.__filename = filename
        self.load_from_file()

    def load_from_file(self):
        """
        Incarca datele din fisier
        """
        with open(self.__filename, mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    idim, adresa, pret, oferta = line.split(",")
                    idim = int(idim)
                    pret = int(pret)
                    i = Imobil(idim, adresa, pret, oferta)
                    super().store(i)

    def medie_oferta(self, oferta):
        """
        Calculeaza media preturilor pentru un anumit tip de oferta
        :param oferta: tipul ofertei
        :return: media preturilor
        """
        suma = 0
        cnt = 0
        for elem in super().get_all():
            if elem.get_oferta() == oferta.lower():
                suma += elem.get_pret()
                cnt += 1
        if cnt != 0:
            return suma / cnt
        else:
            return 0

    def find(self, idim):
        """
        Cauta un imobil dupa id
        :param idim: id-ul imobilului
        :return: elementul cu id-ul dat
        """
        for elem in super().get_all():
            if elem.get_id() == idim:
                return elem


    def write_to_file(self):
        """
        Scrie datele in fisier
        """
        with open(self.__filename, mode = "w") as f:
            for imobil in super().get_all():
                imobil_str = str(imobil.get_id()) + "," + imobil.get_adresa() + "," + str(imobil.get_pret()) + "," + imobil.get_oferta() + '\n'
                f.write(imobil_str)

