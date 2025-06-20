import unittest

from Domain.validator import Validator
from Repository.repository_baschet import RepositorBaschet
from Services.controller_baschet import ControllerBaschet


class TestController(unittest.TestCase):
    def setUp(self):
        repo = RepositorBaschet()
        validator = Validator()
        self.__controller = ControllerBaschet(repo, validator)

    def test_adauga_jucator(self):
        self.assertEqual(len(self.__controller.afiseaza_jucatori()), 0)
        self.__controller.adauga_jucator("Nume", "Prenume", 190, "Pivot")
        self.assertEqual(len(self.__controller.afiseaza_jucatori()), 1)
        self.__controller.adauga_jucator("Nume1", "Prenume1", 190, "Extrema")
        self.assertEqual(len(self.__controller.afiseaza_jucatori()), 2)

        self.assertRaises(ValueError, self.__controller.adauga_jucator, "Nume", "Prenume", 190, "Post")
        self.assertRaises(ValueError, self.__controller.adauga_jucator, "Nume", "Prenume", -190, "Pivot")
        self.assertRaises(ValueError, self.__controller.adauga_jucator, "Nume", "", 190, "Pivot")
        self.assertRaises(ValueError, self.__controller.adauga_jucator, "", "Prenume", 190, "Pivot")

    def test_modifica_jucator(self):
        self.__controller.adauga_jucator("Nume", "Prenume", 190, "Pivot")
        self.__controller.modifica_jucator("Nume", "Prenume", 200)
        self.assertEqual(self.__controller.afiseaza_jucatori()[0].get_inaltime(), 200)

        self.assertRaises(ValueError, self.__controller.modifica_jucator, "Nume", "Andrei", 200)
        self.assertRaises(ValueError, self.__controller.modifica_jucator, "Andrei", "Prenume", 200)

    def test_echipa_inaltime_maxim(self):
        self.__controller.adauga_jucator("Nume", "Prenume", 190, "Pivot")
        self.__controller.adauga_jucator("Nume1", "Prenume1", 200, "Pivot")
        self.__controller.adauga_jucator("Nume2", "Prenume2", 180, "Pivot")
        self.__controller.adauga_jucator("Nume3", "Prenume3", 170, "Extrema")
        self.__controller.adauga_jucator("Nume4", "Prenume4", 160, "Extrema")
        self.__controller.adauga_jucator("Nume5", "Prenume5", 150, "Extrema")
        self.__controller.adauga_jucator("Nume6", "Prenume6", 140, "Fundas")
        self.__controller.adauga_jucator("Nume7", "Prenume7", 130, "Fundas")
        self.__controller.adauga_jucator("Nume8", "Prenume8", 120, "Fundas")

        lista = self.__controller.echipa_inaltime_maxim()
        self.assertEqual(len(lista), 5)
        self.assertEqual(lista[0].get_inaltime(), 140)
        self.assertEqual(lista[1].get_inaltime(), 130)
        self.assertEqual(lista[2].get_inaltime(), 170)
        self.assertEqual(lista[3].get_inaltime(), 160)
        self.assertEqual(lista[4].get_inaltime(), 200)

    def tearDown(self):
        pass