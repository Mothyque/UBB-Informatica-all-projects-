import unittest

from Domain.Spectacol import Spectacol
from Repository.RepositorySpectacole import RepositorySpectacole


class TesteRepository(unittest.TestCase):
    def setUp(self):
        """
        Facem set up
        """
        self.__repo = RepositorySpectacole()
        self.__repo.adauga(Spectacol("Titlu1", "Artist1", "Comedie", 1))

    def test_get_all(self):
        """
        Testam functia get_all
        """
        self.assertEqual(len(self.__repo.get_all()), 1)

    def test_adauga(self):
        """
        Testam functia adauga
        """
        self.__repo.adauga(Spectacol("Titlu2", "Artist2", "Altele", 2))
        self.assertEqual(len(self.__repo.get_all()), 2)

    def test_modifica(self):
        """
        Testam functia modifica
        """
        self.assertEqual(self.__repo.get_all()[0].get_gen(), "Comedie")
        self.assertEqual(self.__repo.get_all()[0].get_durata(), 1)

        self.__repo.modifica(Spectacol("Titlu1", "Artist1", "Balet", 2))

        self.assertEqual(self.__repo.get_all()[0].get_gen(), "Balet")
        self.assertEqual(self.__repo.get_all()[0].get_durata(), 2)

    def tearDown(self):
        """
        Facem tear down
        """
        pass
