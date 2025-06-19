from repository.imobil_repo import ImobilRepoFile


class TesteRepoFile:

    def teste_repo(self):
        """
        Testeaza functionalitatile din clasa ImobilRepoFile
        """
        repo = ImobilRepoFile("data/imobile_test.txt")

        lista = repo.get_all()
        assert len(lista) == 5

        imobil = repo.find(1)
        assert imobil.get_adresa() == "Str. Andrei Muresanu nr. 12 Cluj-Napoca"
        assert imobil.get_pret() == 100000
        assert imobil.get_oferta() == "vanzare"

        medie = repo.medie_oferta("vanzare")
        assert medie == 85666.66666666667

    def ruleaza_teste(self):
        self.teste_repo()