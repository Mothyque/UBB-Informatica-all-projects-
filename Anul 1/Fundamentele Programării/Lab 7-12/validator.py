class Validator:

    @staticmethod
    def valideaza_id(id_curent):
        """
        Validează un ID.
        :param id_curent: ID-ul de validat
        :raises ValueError: dacă ID-ul nu este un număr întreg
        """
        if not isinstance(id_curent, int) and not (isinstance(id_curent, str) and id_curent.isdigit()):
            raise ValueError("ID-ul trebuie să fie un număr întreg.")
    @staticmethod
    def valideaza_id_existent(id_curent, id_existente):
        """
        Validează existența unui ID într-o listă de ID-uri.
        :param id_curent: id-ul de validat
        :param id_existente: lista de ID-uri existente
        :raises ValueError: dacă ID-ul nu există
        """
        if id_curent not in id_existente:
            raise ValueError("ID-ul nu există.")

    @staticmethod
    def valideaza_id_duplicat(id_curent, id_existente):
        """
        Validează un ID duplicat.
        :param id_curent: id de validat
        :param id_existente: lista de ID-uri existente
        :raises ValueError: dacă ID-ul există deja
        """
        id_curent = int(id_curent) if isinstance(id_curent, str) else id_curent
        if id_curent in id_existente:
            raise ValueError("ID-ul există deja.")

    @staticmethod
    def valideaza_nume(nume):
        """
        Validează un nume.
        :param nume: numele de validat
        :raises ValueError: dacă numele nu este un șir de caractere
        """
        if not isinstance(nume, str):
            raise ValueError("Numele trebuie să fie un șir de caractere.")

    @staticmethod
    def valideaza_grupa(grupa):
        """
        Validează o grupă.
        :param grupa: grupa de validat
        :raises ValueError: dacă grupa nu este un număr întreg
        """
        if not isinstance(grupa, int) and not (isinstance(grupa, str) and grupa.isdigit()):
            raise ValueError("Grupa trebuie să fie un număr întreg.")

    @staticmethod
    def valideaza_numar_lab_numar_problema(numar_lab_numar_problema):
        """
        Validează un număr de laborator și un număr de problemă.
        :param numar_lab_numar_problema: numărul laboratorului și al problemei
        :raises ValueError: dacă numărul laboratorului și al problemei nu este în formatul corect
        """
        parts = numar_lab_numar_problema.split("_")
        if len(parts) != 2 or not all(part.isdigit() for part in parts):
            raise ValueError("Datele introduse trebuie să fie în formatul 'Numarlab(int)_Numarprob(int)'.")

    @staticmethod
    def valideaza_numar_lab(numar_lab):
        """
        Validează un număr de laborator.
        :param numar_lab: numarul laboratorului
        :raises ValueError: dacă numărul laboratorului nu este un număr natural
        """
        if not(numar_lab.isdigit()):
            raise ValueError("Numarul laboratorului trebuie sa fie un numar natural.")

    @staticmethod
    def valideaza_numar_lab_numar_problema_existent(numar_lab_numar_problema, probleme_existente):
        """
        Validează existența unui număr de laborator și a unei probleme într-o listă de probleme.
        :param numar_lab_numar_problema: numarul laboratorului și al problemei
        :param probleme_existente: lista de probleme existente
        :raises ValueError: dacă problema nu există
        """
        if numar_lab_numar_problema not in probleme_existente:
            raise ValueError("Problema nu există.")

    @staticmethod
    def valideaza_numar_lab_numar_problema_duplicat(numar_lab_numar_problema, probleme_existente):
        """
        Validează un număr de laborator și o problemă duplicate.
        :param numar_lab_numar_problema: numar laborator și numar problema
        :param probleme_existente: lista de probleme existente
        :raises ValueError: dacă problema există deja în listă
        """
        if numar_lab_numar_problema in probleme_existente:
            raise ValueError("Problema există deja în listă.")

    @staticmethod
    def valideaza_deadline(deadline):
        """
        Validează un deadline.
        :param deadline: deadline-ul de validat
        :raises ValueError: dacă deadline-ul nu este un număr întreg
        """
        if not isinstance(deadline, int) and not (isinstance(deadline, str) and deadline.isdigit()):
            raise ValueError("Deadline-ul trebuie să fie un număr întreg.")

    @staticmethod
    def valideaza_date(date:list):
        """
        Valideaza o lista de date.
        :param date: datele de validat
        :raises ValueError: daca datele nu sunt complete
        """
        if len(date) != 3:
            raise ValueError("Date introduse incomplet!")

    @staticmethod
    def valideaza_nota(nota):
        """
        Validează o notă.
        :param nota: nota de validat
        :raises ValueError: dacă nota nu este un număr întreg sau nu este între 1 și 10
        """
        if not isinstance(nota, int) and not (isinstance(nota, str) and nota.isdigit()):
            raise ValueError("Nota trebuie să fie un număr întreg.")
        if int(nota) < 1 or int(nota) > 10:
            raise ValueError("Nota trebuie să fie între 1 și 10.")

    @staticmethod
    def valideaza_exista_laborator(studid, numar_lab_numar_prob, laboratoare):
        """
        Validează existența unui laborator.
        :param studid: id-ul studentului
        :param numar_lab_numar_prob: numarul laboratorului și al problemei
        :param laboratoare: laboratoarele existente
        :raises ValueError: dacă laboratorul nu există
        """
        ok = 0
        for laborator in laboratoare:
            if laborator.get_student_id_lab() == int(studid) and laborator.get_numar_lab_numar_prob_lab() == numar_lab_numar_prob:
                ok = 1
        if ok == 0:
            raise ValueError(f"Studentului cu id-ul {studid} nu ii este atribuita problema cu numarul {numar_lab_numar_prob}.")

    @staticmethod
    def valideaza_laborator_nenotat(studid, numar_lab_numar_prob, laboratoare):
        """
        Valideaaza daca un laborator a fost notat sau nu.
        :param studid: id student
        :param numar_lab_numar_prob: numarul laboratorului si al problemei
        :param laboratoare: laboratoarele existente
        :raises ValueError: daca laboratorul a fost deja notat
        """
        for laborator in laboratoare:
            if laborator.get_student_id_lab() == int(studid) and laborator.get_numar_lab_numar_prob_lab() == numar_lab_numar_prob and laborator.get_nota() != 0:
                raise ValueError("Laboratorul a fost deja notat.")

    @staticmethod
    def valideaza_laborator_duplicate(lab, laboratoare):
        """
        Valideaza daca un laborator este duplicat.
        :param lab: laboratorul de validat
        :param laboratoare: lista de laboratoare
        :raises ValueError: daca laboratorul este duplicat
        """
        if lab in laboratoare:
            raise ValueError("Studentului ii este deja asignat acest laborator.")

    @staticmethod
    def valideaza_lista(lista):
        """
        Valideaza o lista.
        :param lista: lista de validat
        :raises ValueError: daca lista este goala
        """
        if not lista:
            raise ValueError("Lista este goală.")
