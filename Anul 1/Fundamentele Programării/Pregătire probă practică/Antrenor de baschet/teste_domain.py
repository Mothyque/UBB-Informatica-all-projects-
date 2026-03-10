import unittest

from Domain.baschet import Baschet


class TesteDomain(unittest.TestCase):
    def setUp(self):
        pass

    def test_domain(self):
        jucator = Baschet("Anthony", "Davis", 208, "pivot")
        self.assertEqual(jucator.get_nume(), "Anthony")
        self.assertEqual(jucator.get_prenume(), "Davis")
        self.assertEqual(jucator.get_inaltime(), 208)
        self.assertEqual(jucator.get_post(), "pivot")


    def tearDown(self):
        pass
