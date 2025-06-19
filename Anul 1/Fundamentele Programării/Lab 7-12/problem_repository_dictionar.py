from domain.problema import Problema

class ProblemRepositoryDictionar:
    def __init__(self):
        """
        Inițializează o nouă instanță a clasei ProblemRepository și adaugă o listă de probleme implicită sub formă de dicționar.
        """
        self.dictionar_probleme = {
            "1_3": Problema("1_3", "Media aritmetica a n numere naturale.", 2),
            "3_5": Problema("3_5", "Cauta secventa de lungime maxima care contine doar numere prime.", 4),
            "1_5": Problema("1_5", "Cel mai mare divizor comun a doua numere.", 2)
        }

    def get_all(self):
        """
        Returnează lista completă de probleme.
        :return: lista de obiecte Problema
        """
        return list(self.dictionar_probleme.values())

    def adauga_problema(self, problema):
        """
        Adaugă o problemă nouă în dicționar.
        :param problema: obiectul Problema de adăugat
        """
        self.dictionar_probleme[problema.get_numar_lab_numar_prob()] = problema

    def sterge_problema_numarlab_numarprob(self, nrlabprob: str):
        """
        Șterge problema din dicționar după numărul laboratorului și numărul problemei.
        :param nrlabprob: numărul laboratorului și al problemei sub forma unui string
        :return: True dacă problema a fost ștearsă, False dacă nu a fost găsită
        """
        if nrlabprob in self.dictionar_probleme:
            del self.dictionar_probleme[nrlabprob]
            return True
        return False

    def sterge_problema_deadline(self, deadline: int):
        """
        Șterge toate problemele din dicționar care au un deadline specificat.
        :param deadline: deadline-ul problemelor de șters
        :return: True dacă cel puțin o problemă a fost ștearsă, False dacă nu a fost găsită niciuna
        """
        chei_de_sters = [cheie for cheie, problema in self.dictionar_probleme.items() if problema.get_deadline() == deadline]
        for cheie in chei_de_sters:
            del self.dictionar_probleme[cheie]
        return len(chei_de_sters) > 0

    def modifica_problema_numar_lab_numar_prob(self, numar_curent, numar_nou):
        """
        Modifică numărul laboratorului și al problemei unei probleme specificate.
        :param numar_curent: numărul curent al laboratorului și al problemei
        :param numar_nou: noul număr al laboratorului și al problemei
        :return: True dacă numărul a fost modificat, False dacă problema nu a fost găsită
        """
        if numar_curent in self.dictionar_probleme:
            problema = self.dictionar_probleme.pop(numar_curent)
            problema.set_numar_lab_numar_prob(numar_nou)
            self.dictionar_probleme[numar_nou] = problema
            return True
        return False

    def modifica_problema_descriere(self, numar_curent, descriere):
        """
        Modifică descrierea unei probleme specificate.
        :param numar_curent: numărul curent al laboratorului și al problemei
        :param descriere: noua descriere a problemei
        :return: True dacă descrierea a fost modificată, False dacă problema nu a fost găsită
        """
        if numar_curent in self.dictionar_probleme:
            self.dictionar_probleme[numar_curent].set_descriere(descriere)
            return True
        return False

    def modifica_problema_deadline(self, numar_curent, deadline: int):
        """
        Modifică deadline-ul unei probleme specificate.
        :param numar_curent: numărul curent al laboratorului și al problemei
        :param deadline: noul deadline al problemei
        :return: True dacă deadline-ul a fost modificat, False dacă problema nu a fost găsită
        """
        if numar_curent in self.dictionar_probleme:
            self.dictionar_probleme[numar_curent].set_deadline(deadline)
            return True
        return False

    def cauta_problema_laborator(self, laborator):
        """
        Caută și returnează o listă de probleme care aparțin unui laborator specificat.
        :param laborator: numărul laboratorului
        :return: lista de obiecte Problema din laboratorul specificat
        """
        return [problema for problema in self.dictionar_probleme.values() if problema.get_numar_lab() == laborator]

    def cauta_problema_problema(self, prob):
        """
        Caută și returnează o listă de probleme cu un număr specificat.
        :param prob: numărul problemei căutate
        :return: lista de obiecte Problema cu numărul specificat
        """
        return [problema for problema in self.dictionar_probleme.values() if problema.get_numar_lab_numar_prob() == prob]

    def cauta_problema_deadline(self, deadline):
        """
        Caută și returnează o listă de probleme care au un deadline specificat.
        :param deadline: deadline-ul problemelor căutate
        :return: lista de obiecte Problema cu deadline-ul specificat
        """
        return [problema for problema in self.dictionar_probleme.values() if problema.get_deadline() == deadline]

    def get_lista_probleme(self):
        """
        Returnează lista de identificatori ai tuturor problemelor.
        :return: lista de identificatori (numar_lab_numar_prob) ca string-uri
        """
        return list(self.dictionar_probleme.keys())
