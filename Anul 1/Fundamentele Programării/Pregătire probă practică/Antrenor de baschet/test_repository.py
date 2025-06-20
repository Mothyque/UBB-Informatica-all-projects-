import unittest

from Domain.baschet import Baschet
from Repository.repository_baschet import RepositorBaschet


class TestRepository(unittest.TestCase):
    def setUp(self):
        self.__repo = RepositorBaschet()

    def test_adauga(self):
        self.assertEqual(len(self.__repo.get_all()), 0)
        self.__repo.adauga(Baschet("Nume", "Prenume", 190, "Post"))
        self.assertEqual(len(self.__repo.get_all()), 1)
        self.__repo.adauga(Baschet("Nume", "Prenume", 190, "Post"))
        self.assertEqual(len(self.__repo.get_all()), 2)

    def test_modifica(self):
        self.__repo.adauga(Baschet("Nume", "Prenume", 190, "Post"))
        self.__repo.modifica(Baschet("Nume", "Prenume", 200, "Post"))
        self.assertEqual(self.__repo.get_all()[0].get_inaltime(), 200)

    def test_get_all(self):
        self.assertEqual(len(self.__repo.get_all()), 0)
        self.__repo.adauga(Baschet("Nume", "Prenume", 190, "Post"))
        self.assertEqual(len(self.__repo.get_all()), 1)
        self.__repo.adauga(Baschet("Nume", "Prenume", 190, "Post"))
        self.assertEqual(len(self.__repo.get_all()), 2)
        
    def tearDown(self):
        pass