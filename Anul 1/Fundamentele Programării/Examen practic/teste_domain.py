import unittest

from Domain.Spectacol import Spectacol


class TesteDomain(unittest.TestCase):

    def setUp(self):
        """
        Facem set up
        """

    def teste_domain(self):
        """
        Testam functiile din domain
        """
        s = Spectacol("Titlu1", "Artist1", "Comedie", 2)
        self.assertEqual(s.get_titlu(), "Titlu1")
        self.assertEqual(s.get_artist(), "Artist1")
        self.assertEqual(s.get_gen(), "Comedie")
        self.assertEqual(s.get_durata(), 2)

    def tearDown(self):
        """
        Facem tear down
        """
        pass



