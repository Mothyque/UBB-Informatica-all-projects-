import unittest

from domain.imobil import Imobil
from repository.imobil_repo import ImobilRepoFile
from service.imobil_controller import ImobilController


class TesteDomain:
    def test_domain(self):
        """
        Testeaza functionalitatile din clasa Imobil
        """
        i = Imobil(2, "Str. Branistea nr 15 Iasi", 120000, "vanzare")
        assert i.get_id() == 2
        assert i.get_adresa() == "Str. Branistea nr 15 Iasi"
        assert i.get_pret() == 120000
        assert i.get_oferta() == "vanzare"

        i.set_id(3)
        assert i.get_id() == 3

        i.set_adresa("Str. Pacurari nr 10 Iasi")
        assert i.get_adresa() == "Str. Pacurari nr 10 Iasi"

        i.set_pret(150000)
        assert i.get_pret() == 150000

        i.set_oferta("inchiriere")
        assert i.get_oferta() == "inchiriere"

    def ruleaza_teste(self):
        self.test_domain()


