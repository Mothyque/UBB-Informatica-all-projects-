class Problema:
    def __init__(self, numarlab_numarprob: str, descriere: str, deadline: int):
        """
        Initializează o nouă instanță de tip Problema.
        :param numarlab_numarprob: Numarul laboratorului și al problemei sub formă de string în formatul "NumarLab_NumarProb"
        :param descriere: Descrierea problemei
        :param deadline: Deadline-ul problemei exprimat ca număr întreg
        """
        self.__numarlab_numarprob = numarlab_numarprob
        self.__descriere = descriere
        self.__deadline = deadline

    def get_numar_lab_numar_prob(self):
        """
        Returnează numărul laboratorului și al problemei.
        :return: numărul laboratorului și al problemei ca string în formatul "NumarLab_NumarProb"
        """
        return self.__numarlab_numarprob

    def set_numar_lab_numar_prob(self, v):
        """
        Modifică numărul laboratorului și al problemei.
        :param v: noul număr al laboratorului și al problemei sub formă de string în formatul "NumarLab_NumarProb"
        """
        self.__numarlab_numarprob = v

    def get_numar_lab(self):
        """
        Returnează numărul laboratorului.
        :return: numărul laboratorului ca string
        """
        n = self.__numarlab_numarprob.split("_")
        return n[0]

    def set_numar_lab(self, v):
        """
        Modifică numărul laboratorului.
        :param v: noul număr al laboratorului sub formă de string
        """
        n = self.__numarlab_numarprob.split("_")
        n[0] = v
        self.__numarlab_numarprob = str(n[0]) + "_" + str(n[1])

    def get_numar_prob(self):
        """
        Returnează numărul problemei.
        :return: numărul problemei ca string
        """
        n = self.__numarlab_numarprob.split("_")
        return n[1]

    def set_numar_proba(self, v):
        """
        Modifică numărul problemei.
        :param v: noul număr al problemei sub formă de string
        """
        n = self.__numarlab_numarprob.split("_")
        n[1] = v
        self.__numarlab_numarprob = str(n[0]) + "_" + str(n[1])

    def get_descriere(self):
        """
        Returnează descrierea problemei.
        :return: descrierea problemei ca string
        """
        return self.__descriere

    def set_descriere(self, v):
        """
        Modifică descrierea problemei.
        :param v: noua descriere a problemei sub formă de string
        """
        self.__descriere = v

    def get_deadline(self):
        """
        Returnează deadline-ul problemei.
        :return: deadline-ul problemei ca număr întreg
        """
        return self.__deadline

    def set_deadline(self, v):
        """
        Modifică deadline-ul problemei.
        :param v: noul deadline al problemei ca număr întreg
        """
        self.__deadline = v

    def __eq__(self, other):
        """
        Verifică egalitatea între două obiecte de tip Problema.
        :param other: obiectul de comparat
        :return: True dacă obiectele sunt egale, False altfel
        """
        if isinstance(other, Problema):
            return self.__numarlab_numarprob == other.__numarlab_numarprob and self.__descriere == other.__descriere and self.__deadline == other.__deadline
        return False

    @classmethod
    def creeaza_problema(cls, numar_lab_numar_proba: str, descriere: str, deadline: int):
        """
        Creează și returnează o nouă instanță a clasei Problema.
        :param numar_lab_numar_proba: Numarul laboratorului și al problemei sub formă de string în formatul "NumarLab_NumarProb"
        :param descriere: Descrierea problemei
        :param deadline: Deadline-ul problemei exprimat ca număr întreg
        :return: o instanță a clasei Problema
        """
        return cls(numar_lab_numar_proba, descriere, deadline)


    def __str__(self):
        """
        Returnează o reprezentare sub formă de string a obiectului Problema.
        :return: o descriere detaliată a problemei sub formă de string
        """
        problema = self.get_numar_prob()
        laborator = self.get_numar_lab()
        cerinta = self.get_descriere()
        deadline = self.get_deadline()
        return f"Problema {problema} de la laboratorul {laborator} are urmatoarea cerinta: {cerinta} Aceasta problema are termenul de preadare: saptamana {deadline}."
