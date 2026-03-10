import unittest

from Domain.muzica import Muzica
from Repository.repository_muzica import RepositoryMuzica


class TestRepository(unittest.TestCase):
    def setUp(self):
        self.repo = RepositoryMuzica()
        self.repo.adauga_muzica(Muzica("Titlu", "Artist", "Rock", 3))
        self.repo.adauga_muzica(Muzica("Titlu2", "Artist2", "Pop", 4))

    def test_get_all(self):
        lista = self.repo.get_all()
        self.assertEqual(len(lista), 2)

    def test_adauga_muzica(self):
        self.repo.adauga_muzica(Muzica("Titlu3", "Artist3", "Rock", 5))
        lista = self.repo.get_all()
        self.assertEqual(len(lista), 3)

    def test_modifica_muzica(self):
        self.repo.modifica_muzica(Muzica("Titlu", "Artist", "Altele", 5))
        lista = self.repo.get_all()
        self.assertEqual(lista[0].get_durata(), 5)
        self.assertEqual(lista[0].get_gen(), "Altele")


    def tearDown(self):
        pass
