import unittest

from Domain.validator import Validator
from Repository.repository_muzica import RepositoryMuzica
from Service.controller_muzica import ControllerMuzica


class TesteController(unittest.TestCase):
    def setUp(self):
        test_repo = RepositoryMuzica()
        self.controller = ControllerMuzica(test_repo, Validator)
        self.controller.adauga_melodie("Test", "Test", "Rock", 1)
        self.controller.adauga_melodie("Test1", "Test1", "Altele", 2)

    def test_afiseaza_melodii(self):
        self.assertEqual(len(self.controller.afiseaza_melodii()), 2)

    def test_adauga_melodie(self):
        self.controller.adauga_melodie("Test2", "Test2", "Rock", 3)
        self.assertEqual(len(self.controller.afiseaza_melodii()), 3)

    def test_modifica_melodie(self):
        self.controller.modifica_melodie("Test", "Test", "Pop", 4)
        self.assertEqual(self.controller.afiseaza_melodii()[0].get_durata(), 4)

    def test_genereaza_melodii(self):
        self.controller.genereaza_melodii(3, ["Test", "Test1"], ["Test", "Test1"])
        self.assertEqual(len(self.controller.afiseaza_melodii()), 5)


    def tearDown(self):
        pass