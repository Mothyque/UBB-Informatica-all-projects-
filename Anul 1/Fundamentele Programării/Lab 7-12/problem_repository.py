from domain.problema import Problema

class ProblemRepository:
    def __init__(self):
        """
        Inițializează o nouă instanță a clasei ProblemRepository și adaugă o listă de probleme implicită.
        """
        self.lista_probleme = [
            Problema("1_3", "Media aritmetica a n numere naturale.", 2),
            Problema("3_5", "Cauta secventa de lungime maxima care contine doar numere prime.", 4),
            Problema("1_5", "Cel mai mare divizor comun a doua numere.", 2),
            Problema("2_1", "Suma cifrelor unui numar.", 3),
            Problema("3_3", "Verifica daca un numar este palindrom.", 7),
            Problema("5_2", "Verifica daca un numar este prim.", 8),
        ]

    def get_all(self):
        """
        Returnează lista completă de probleme.
        :return: lista de obiecte Problema
        """
        return self.lista_probleme

    def adauga_problema(self, problema):
        """
        Adaugă o problemă nouă în listă.
        :param problema: obiectul Problema de adăugat
        """
        self.lista_probleme.append(problema)

    def sterge_problema_numarlab_numarprob(self, nrlabprob: str):
        """
        Șterge problema din listă după numărul laboratorului și numărul problemei.
        :param nrlabprob: numărul laboratorului și al problemei sub forma unui string
        :return: True dacă problema a fost ștearsă, False dacă nu a fost găsită
        """
        for problema in self.lista_probleme:
            numarlab_numarproba = problema.get_numar_lab_numar_prob()
            if numarlab_numarproba == nrlabprob:
                self.lista_probleme.remove(problema)
                return True
        return False

    def sterge_problema_deadline(self, deadline: int):
        """
        Șterge toate problemele din listă care au un deadline specificat.
        :param deadline: deadline-ul problemelor de șters
        :return: True dacă cel puțin o problemă a fost ștearsă, False dacă nu a fost găsită niciuna
        """
        initial_length = len(self.lista_probleme)
        self.lista_probleme = [problema for problema in self.lista_probleme if int(problema.get_deadline()) != deadline]
        return len(self.lista_probleme) < initial_length

    def modifica_problema_numar_lab_numar_prob(self, numar_curent, numar_nou):
        """
        Modifică numărul laboratorului și al problemei unei probleme specificate.
        :param numar_curent: numărul curent al laboratorului și al problemei
        :param numar_nou: noul număr al laboratorului și al problemei
        :return: True dacă numărul a fost modificat, False dacă problema nu a fost găsită
        """
        for problema in self.lista_probleme:
            numarlab = problema.get_numar_lab_numar_prob()
            if numarlab == numar_curent:
                problema.set_numar_lab_numar_prob(numar_nou)
                return True
        return False

    def modifica_problema_descriere(self, numar_curent, descriere):
        """
        Modifică descrierea unei probleme specificate.
        :param numar_curent: numărul curent al laboratorului și al problemei
        :param descriere: noua descriere a problemei
        :return: True dacă descrierea a fost modificată, False dacă problema nu a fost găsită
        """
        for problema in self.lista_probleme:
            numarlab = problema.get_numar_lab_numar_prob()
            if numarlab == numar_curent:
                problema.set_descriere(descriere)
                return True
        return False

    def modifica_problema_deadline(self, numar_curent, deadline: int):
        """
        Modifică deadline-ul unei probleme specificate.
        :param numar_curent: numărul curent al laboratorului și al problemei
        :param deadline: noul deadline al problemei
        :return: True dacă deadline-ul a fost modificat, False dacă problema nu a fost găsită
        """
        for problema in self.lista_probleme:
            numarlab = problema.get_numar_lab_numar_prob()
            if numarlab == numar_curent:
                problema.set_deadline(deadline)
                return True
        return False

    def cauta_problema_laborator(self, laborator):
        """
        Caută și returnează o listă de probleme care aparțin unui laborator specificat.
        :param laborator: numărul laboratorului
        :return: lista de obiecte Problema din laboratorul specificat
        """
        lista = []
        for problema in self.lista_probleme:
            laborator_curent = problema.get_numar_lab()
            if laborator_curent == laborator:
                lista.append(problema)
        return lista

    def cauta_problema_problema(self, prob):
        """
        Caută și returnează o listă de probleme cu un număr specificat.
        :param prob: numărul problemei căutate
        :return: lista de obiecte Problema cu numărul specificat
        """
        lista = []
        for problema in self.lista_probleme:
            numar_curent = problema.get_numar_lab_numar_prob()
            if numar_curent == prob:
                lista.append(problema)
        return lista

    def cauta_problema_deadline(self, deadline):
        """
        Caută și returnează o listă de probleme care au un deadline specificat.
        :param deadline: deadline-ul problemelor căutate
        :return: lista de obiecte Problema cu deadline-ul specificat
        """
        lista = []
        for problema in self.lista_probleme:
            deadline_curent = problema.get_deadline()
            if deadline_curent == deadline:
                lista.append(problema)
        return lista

    def get_lista_probleme(self, index=0, lista_prob=None):
        """
        Returnează lista de identificatori ai tuturor problemelor.
        :return: lista de identificatori (numar_lab_numar_prob) ca string-uri
        """
        if lista_prob is None:
            lista_prob = []
        if index >= len(self.lista_probleme):
            return lista_prob
        lista_prob.append(self.lista_probleme[index].get_numar_lab_numar_prob())
        return self.get_lista_probleme(index + 1, lista_prob)
        #recursive function

    def clear(self):
        """
        Șterge toate problemele din listă.
        """
        self.lista_probleme.clear()