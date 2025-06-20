import unittest

from Domain.muzica import Muzica


class TestDomain(unittest.TestCase):
    def setUp(self) -> None:
        pass

    def test_player(self):
        m = Muzica("Titlu", "Artist", "Gen", 3)
        self.assertEqual(m.get_titlu(), "Titlu")
        self.assertEqual(m.get_artist(), "Artist")
        self.assertEqual(m.get_gen(), "Gen")
        self.assertEqual(m.get_durata(), 3)
        
    def tearDown(self) -> None:
        pass