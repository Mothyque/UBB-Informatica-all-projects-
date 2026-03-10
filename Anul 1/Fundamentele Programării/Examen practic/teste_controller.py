import unittest

from Domain.Spectacol import Spectacol
from Domain.Validator import Validator
from Repository.RepositorySpectacole import RepositorySpectacole
from Service.ControllerSpectacole import ControllerSpectacole


class TesteController(unittest.TestCase):
    def setUp(self):
        """
        Facem set up
        """
        repo = RepositorySpectacole()
        validator = Validator()
        self.__controller = ControllerSpectacole(repo, validator)
        self.__controller.adauga_spectacol("Titlu1", "Artist1", "Comedie", 3)

    def test_afiseaza_spectacole(self):
        """
        Testam functia afiseaza_spectacole
        """
        self.assertEqual(len(self.__controller.afiseaza_spectacole()),1)

    def test_adauga_spectacol(self):
        """
        Testam functia adauga_spectacol
        """
        self.__controller.adauga_spectacol("Titlu2", "Artist2", "Altele", 3)
        self.assertEqual(len(self.__controller.afiseaza_spectacole()), 2)

        self.__controller.adauga_spectacol("Titlu3", "Artist3", "Concert", 3)
        self.assertEqual(len(self.__controller.afiseaza_spectacole()), 3)

    def test_modifica_spectacol(self):
        """
        Testam functia modifica_spectacol
        """
        self.assertEqual(self.__controller.afiseaza_spectacole()[0].get_gen(), "Comedie")
        self.assertEqual(self.__controller.afiseaza_spectacole()[0].get_durata(), 3)

        self.__controller.modifica_spectacol("Titlu1", "Artist1", "Altele", 1)

        self.assertEqual(self.__controller.afiseaza_spectacole()[0].get_gen(), "Altele")
        self.assertEqual(self.__controller.afiseaza_spectacole()[0].get_durata(), 1)

    def test_genereaza_spectacole(self):
        """
        Testam functia genereaza_spectacole
        """
        self.assertEqual(len(self.__controller.afiseaza_spectacole()), 1)
        self.__controller.genereaza_spectacole(4)
        self.assertEqual(len(self.__controller.afiseaza_spectacole()), 5)

    def test_exporta(self):
        """
        Testam functia exporta
        """
        self.__controller.adauga_spectacol("Titlu2", "Artist2", "Altele", 3)
        self.__controller.adauga_spectacol("Titlu3", "Artist1", "Comedie", 1)
        self.__controller.adauga_spectacol("Titlu4", "Artist2", "Concert", 2)
        self.__controller.adauga_spectacol("Titlu5", "Artist1", "Altele", 3)

        self.__controller.exporta("teste.txt")
        lista_verificare = []
        with open("teste.txt", mode = "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    titlu, artist, durata, gen = line.split(",")
                    durata = int(durata)
                    s = Spectacol(titlu, artist, gen, durata)
                    lista_verificare.append(s)
        self.assertEqual(lista_verificare[0], Spectacol("Titlu1", "Artist1", "Comedie", 3))
        self.assertEqual(lista_verificare[1], Spectacol("Titlu3", "Artist1", "Comedie", 1))
        self.assertEqual(lista_verificare[2], Spectacol("Titlu5", "Artist1", "Altele", 3))
        self.assertEqual(lista_verificare[3], Spectacol("Titlu2", "Artist2", "Altele", 3))



    def tearDown(self):
        """
        Facem tear down
        """
        pass
