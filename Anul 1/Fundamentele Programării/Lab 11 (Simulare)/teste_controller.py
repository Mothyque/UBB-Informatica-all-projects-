from repository.imobil_repo import ImobilRepoFile
from service.imobil_controller import ImobilController


class TesteController:

    def teste_controller(self):
        """
        Testeaza functionalitatile din clasa ImobilController
        """
        repo = ImobilRepoFile("data/imobile_test.txt")
        controller = ImobilController(repo)

        lista = controller.afiseaza()
        assert len(lista) == 5

        medie = controller.medie_pret_dorit("vanzare")
        assert medie == 85666.66666666667

        adresa, comision = controller.tranzactie(1, 120000)
        assert adresa == "Str. Andrei Muresanu nr. 12 Cluj-Napoca"
        assert comision == 2400

    def ruleaza_teste(self):
        self.teste_controller()
