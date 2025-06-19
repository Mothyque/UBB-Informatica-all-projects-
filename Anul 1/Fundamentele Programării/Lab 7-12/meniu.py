from domain.validator import Validator


class Meniu:
    def __init__(self, student_controller, problem_controller, laboratory_controller):
        self.laborator_controller = laboratory_controller
        self.student_controller = student_controller
        self.problem_controller = problem_controller

    def meniu_principal(self):
        """
        Meniul principal de start
        """
        while True:
            print("Meniu principal:")
            print("A. Afiseaza studenti")
            print("P. Afiseaza probleme")
            print("L. AFiseaza laboratoare")
            print("R. Genereaza random studenti sau probleme")
            print("S. Sorteaza crescator dupa grupa si invers dupa nume")
            print("1. Adauga student sau problema")
            print("2. Sterge student sau problema")
            print("3. Modifica student sau problema")
            print("4. Cauta student sau problema")
            print("5. Asignare / notare laborator")
            print("6. Creare statistici")
            print("E. Iesire")

            optiune = input(">>>> ")

            if optiune.lower() == 'a':
                lista_studenti = self.student_controller.afiseaza_studenti()
                for student in lista_studenti:
                    print(student)
                print('-------------------------------------------------------')
            elif optiune.lower() == 'p':
                lista_probleme = self.problem_controller.afiseaza_probleme()
                for problema in lista_probleme:
                    print(problema)
                print('-------------------------------------------------------')
            elif optiune.lower() == 'l':
                self.laborator_controller.afiseaza_laboratoare()
                print('-------------------------------------------------------')
            elif optiune.lower() == 'r':
                self.genereaza_random()
            elif optiune.lower() == 's':
                lista = self.student_controller.sorteaza_grupa_crescator_nume_descrescator()
                for elem in lista:
                    print(elem)
                print('-------------------------------------------------------')
            elif optiune == '1':
                self.adauga()
            elif optiune == '2':
                self.sterge()
            elif optiune == '3':
                self.modifica()
            elif optiune == '4':
                self.cauta()
            elif optiune == '5':
                self.laborator()
            elif optiune == '6':
                self.statistici()
            elif optiune.lower() == 'e':
                print("Parasire aplicatie...")
                break
            else:
                print("Instructiune invalida!")
                print('-------------------------------------------------------')
    def adauga(self):
        """
        Meniul pentru operatia de adaugare
        """
        print("Adaugi student sau problema?")
        print("B. Back")
        optiune = input(">>>> ")
        if optiune.lower() == 'student':
            try:
                date = input("Introdu id, nume si grupa: ").split()
                Validator.valideaza_date(date)
                studid = date[0]
                nume = date[1]
                grupa = date[2]
                self.student_controller.adauga_student(studid, nume, grupa)
                print('Student adăugat cu succes!')
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'problema':
            try:
                date = input("Introdu numarul laboratorului si problema (Numarlab_Numarprob), descrierea si deadline-ul: ").split()
                Validator.valideaza_date(date)
                numar_lab_numar_problema = date[0]
                descriere = date[1]
                deadline = int(date[2])
                self.problem_controller.adauga_problema(numar_lab_numar_problema, descriere, deadline)
                print('Problema adăugată cu succes!')
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'b':
            print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def sterge(self):
        """
        Meniul pentru operatia de stergere
        """
        print("Stergi student sau problema?")
        print("B. Back")
        optiune = input(">>>> ")
        if optiune.lower() == 'student':
            optiune2 = input("Doresti sa stergi dupa id, nume sau grupa? ").lower()
            if optiune2 == 'id':
                try:
                    studid = int(input("Introdu id-ul studentului: "))
                    rezultat = self.student_controller.sterge_student('id', studid)
                    self.laborator_controller.sterge_laborator_id(studid)
                    if rezultat:
                        print(f"S-a sters studentul cu id-ul {studid}.")
                        print('-------------------------------------------------------')
                    else:
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif optiune2 == 'nume':
                try:
                    nume = input("Introdu numele studentului: ")
                    rezultat = self.student_controller.sterge_student('nume', nume)
                    if rezultat:
                        print(f"S-a sters studentul cu numele {nume}.")
                        print('-------------------------------------------------------')
                    else:
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif optiune2 == 'grupa':
                try:
                    grupa = int(input("Introdu grupa studentului: "))
                    rezultat = self.student_controller.sterge_student('grupa', grupa)
                    if rezultat:
                        print(f"S-au sters studentii din grupa {grupa}.")
                        print('-------------------------------------------------------')
                    else:
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            else:
                print("Instructiune invalida!")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'problema':
            criteriu_stergere = input("Stergi problema dupa numar sau deadline? ").lower()
            if criteriu_stergere == 'numar':
                try:
                    numar_prob = input("Introdu numarul problemei: ")
                    numar_lab = input("Introdu numarul laboratorului: ")
                    numar = str(numar_lab) + "_" + str(numar_prob)
                    rezultat = self.problem_controller.sterge_problema('numar', numar)
                    self.laborator_controller.sterge_laborator_numar_lab_numar_problema(numar)
                    if rezultat:
                        print(f"S-a sters problema {numar_prob}, laborator {numar_lab}.")
                        print('-------------------------------------------------------')
                    else:
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif criteriu_stergere == 'deadline':
                try:
                    deadline = input("Introdu deadline-ul: ")
                    rezultat = self.problem_controller.sterge_problema('deadline', deadline)
                    if rezultat:
                        print(f"S-au sters problemele cu deadline-ul {deadline}.")
                        print('-------------------------------------------------------')
                    else:
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            else:
                print("Instructiune invalida!")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'b':
                print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def modifica(self):
        """
        Meniul pentru operatia de modificare
        """
        print("Modifici student sau problema?")
        print("B. Back")
        optiune = input(">>>> ")
        if optiune.lower() == 'student':
            comanda = input("Doresti sa modifici id, nume sau grupa? ")
            if comanda.lower() == 'id':
                try:
                    studid = input("Introdu id-ul studentului curent: ")
                    id_nou = input("Introdu noul id: ")
                    rezultat = self.student_controller.modifica_student('id', studid, id_nou)
                    if rezultat:
                        print(f"S-a modificat id-ul studentului de la {studid} la {id_nou}.")
                        print('-------------------------------------------------------')
                    else:
                        print(f"Nu s-a reusit modificarea! Id inexistent.")
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif comanda.lower() == 'nume':
                try:
                    studid = input("Introdu id-ul studentului: ")
                    nume_nou = input("Introdu noul nume: ")
                    rezultat = self.student_controller.modifica_student('nume', studid, nume_nou)
                    if rezultat:
                        print(f"S-a modificat numele studentului cu id-ul {studid} in {nume_nou}.")
                        print('-------------------------------------------------------')
                    else:
                        print(f"Nu s-a reusit modificarea! Nume inexistent.")
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif comanda.lower() == 'grupa':
                try:
                    studid = input("Introdu id-ul studentului: ")
                    grupa_noua = input("Introdu noua grupa: ")
                    rezultat = self.student_controller.modifica_student('grupa', studid, grupa_noua)
                    if rezultat:
                        print(f"S-a modificat grupa studentului cu id-ul {studid} in {grupa_noua}.")
                        print('-------------------------------------------------------')
                    else:
                        print(f"Nu s-a reusit modificarea! Grupa inexistentă.")
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif comanda.lower() == 'b':
                print('-------------------------------------------------------')
        elif optiune.lower() == 'problema':
            comanda = input("Doresti sa modifici numarul problemei, al laboratorului, descrierea sau deadline-ul? ").lower()
            if comanda == 'numar problema':
                try:
                    numar_prob = input("Introdu numarul problemei curente: ")
                    numar_lab = input("Introdu numarul laboratorului: ")
                    numar_curent = str(numar_lab) + "_" + str(numar_prob)
                    numar_prob_nou = input("Introdu noul numar al problemei: ")
                    numar_nou = str(numar_lab) + "_" + str(numar_prob_nou)
                    rezultat = self.problem_controller.modifica_problema('numar', numar_curent, numar_nou)
                    if rezultat:
                        print(f"S-a modificat numarul problemei de la {numar_prob} la {numar_prob_nou}.")
                        print('-------------------------------------------------------')
                    else:
                        print(f"Nu s-a reusit modificarea! Problema inexistentă.")
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif comanda == 'descriere':
                try:
                    numar_prob = input("Introdu numarul problemei: ")
                    numar_lab = input("Introdu numarul laboratorului: ")
                    numar = str(numar_lab) + "_" + str(numar_prob)
                    descriere_noua = input("Introdu noua descriere: ")
                    rezultat = self.problem_controller.modifica_problema('descriere', numar, descriere_noua)
                    if rezultat:
                        print(f"S-a modificat descrierea problemei {numar_prob}, laborator {numar_lab}.")
                        print('-------------------------------------------------------')
                    else:
                        print(f"Nu s-a reusit modificarea! Problema inexistentă.")
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif comanda == 'deadline':
                try:
                    numar_prob = input("Introdu numarul problemei: ")
                    numar_lab = input("Introdu numarul laboratorului: ")
                    numar = str(numar_lab) + "_" + str(numar_prob)
                    deadline_nou = input("Introdu noul deadline: ")
                    rezultat = self.problem_controller.modifica_problema('deadline', numar, deadline_nou)
                    if rezultat:
                        print(f"S-a modificat deadline-ul problemei {numar_prob}, laborator {numar_lab}.")
                        print('-------------------------------------------------------')
                    else:
                        print(f"Nu s-a reusit modificarea! Problema inexistentă.")
                        print('-------------------------------------------------------')
                except ValueError as e:
                    print(f"Eroare! {e}")
                    print('-------------------------------------------------------')
            elif comanda == 'b':
                print('-------------------------------------------------------')
            else:
                print("Instructiune invalida!")
                print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def cauta(self):
        """
        Meniul pentru operatia de cautare
        """
        print("Cauti student sau problema?")
        print("B. Back")
        optiune = input(">>>> ")

        if optiune.lower() == 'b':
            return
        elif optiune.lower() == 'student':
            self.cauta_student()
        elif optiune.lower() == 'problema':
            self.cauta_problema()
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def cauta_student(self):
        """
        Meniul pentru operatia de cautare student
        """
        print("Cauti student dupa id, nume sau grupa?")
        print("B. Back")
        optiune = input(">>>> ")

        if optiune.lower() == 'b':
            return
        elif optiune.lower() == 'id':
            try:
                studid = input("Introdu id-ul studentului: ")
                lista = self.student_controller.cauta_student('id', studid)
                for student in lista:
                    print(student)
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'nume':
            try:
                nume = input("Introdu numele studentului: ")
                lista  = self.student_controller.cauta_student('nume', nume)
                for student in lista:
                    print(student)
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'grupa':
            try:
                grupa = input("Introdu grupa studentului: ")
                lista  = self.student_controller.cauta_student('grupa', grupa)
                for student in lista:
                    print(student)
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def cauta_problema(self):
        """
        Meniul pentru operatia de adaugare
        """
        print("Cauti problema dupa numar laborator, numar problema sau deadline?")
        print("B. Back")
        optiune = input(">>>> ")

        if optiune.lower() == 'b':
            return
        elif optiune.lower() == 'laborator':
            try:
                lab = input("Introdu numar laborator: ")
                lista = self.problem_controller.cauta_problema('laborator', lab)
                for problema in lista:
                    print(problema)
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'problema':
            try:
                prob = input("Introdu numar laborator si numar problema, separate de _: ")
                lista = self.problem_controller.cauta_problema('problema', prob)
                for problema in lista:
                    print(problema)
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'deadline':
            try:
                deadline = int(input("Introdu deadline-ul: "))
                lista = self.problem_controller.cauta_problema('deadline', deadline)
                for problema in lista:
                    print(problema)
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def laborator(self):
        """
        Meniul pentru operatia de asignare/notare laborator
        """
        print("Doresti sa asignezi sau sa notezi un laborator?")
        print("B. Back")
        optiune = input(">>>> ")
        if optiune.lower() == 'b':
            return
        elif optiune.lower() == 'asigneaza':
            print("Introdu id-ul studentului si numarul laboratorului si al problemei:")
            try:
                studid = int(input("Introdu id-ul studentului: "))
                numar_lab_numar_prob = input("Introdu numarul laboratorului si al problemei: ")
                self.laborator_controller.asigneaza_laborator(studid, numar_lab_numar_prob)
                print('Laborator asignat cu succes!')
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('------------------------------------------------')
        elif optiune.lower() == 'noteaza':
            print("Introdu id-ul studentului si numarul laboratorului si al problemei:")
            try:
                studid = int(input("Introdu id-ul studentului: "))
                numar_lab_numar_prob = input("Introdu numarul laboratorului si al problemei: ")
                nota = int(input("Introdu nota: "))
                self.laborator_controller.noteaza_laborator(studid, numar_lab_numar_prob, nota)
                print('Laborator notat cu succes!')
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def genereaza_random(self):
        print("Doresti sa generezi studenti sau probleme?")
        print("B. Back")
        optiune = input(">>>> ")
        if optiune.lower() == 'studenti':
            try:
                n = int(input("Introdu numarul de studenti: "))
                self.student_controller.random_student(n)
                print(f"{n} studenti generati cu succes!")
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'probleme':
            try:
                n = int(input("Introdu numarul de probleme: "))
                self.problem_controller.random_problem(n)
                print(f"{n} probleme generate cu succes!")
                print('-------------------------------------------------------')
            except ValueError as e:
                print(f"Eroare! {e}")
                print('-------------------------------------------------------')
        elif optiune.lower() == 'b':
            print('-------------------------------------------------------')
        else:
            print("Instructiune invalida!")
            print('-------------------------------------------------------')

    def statistici(self):
        try:
            print("Doresti sa generezi lista de studenti si notele lor, cei cu media laboratoarelor mai mica decat 5, sau studentii ordonati dupa numarul de laboratoare rezolvate?")
            print("B. Back")
            optiune = input(">>>> ")
            if optiune.lower() == 'studenti':
                ordonare = input("Doresti sa ordonezi lista dupa nume sau nota? ").lower()
                numar_lab_numar_prob = input("Introdu numarul laboratorului si al problemei: ")
                lista = self.laborator_controller.lista_studenti_ordonata(ordonare, numar_lab_numar_prob)
                for elem in lista:
                    print(f"Nume: {elem[0]}, Nota: {elem[1]}")
                print('-------------------------------------------------------')
            elif optiune.lower() == 'medie':
                lista = self.laborator_controller.lista_studenti_sub_5()
                for elem in lista:
                    print(f"Nume: {elem[0]}, Medie: {elem[1]}")
                print('-------------------------------------------------------')
            elif optiune.lower() == 'laboratoare':
                lista = self.laborator_controller.lista_studenti_activi()
                for elem in lista:
                    print(f"Nume: {elem[0]}, Numar laboratoare: {elem[1]}")
                print('-------------------------------------------------------')
            elif optiune.lower() == 'b':
                print('-------------------------------------------------------')
            else:
                print("Instructiune invalida!")
                print('-------------------------------------------------------')
        except ValueError as e:
            print(f"Eroare! {e}")
            print('-------------------------------------------------------')