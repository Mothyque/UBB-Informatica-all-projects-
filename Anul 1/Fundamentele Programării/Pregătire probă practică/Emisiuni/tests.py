from Domain.emisiune import Emisiune
from Domain.validator import ValidatorEmisiune
from Repository.repository_emisiuni import RepositoryEmisiuni
from Services.controller_emisiuni import ControllerEmisiuni


class Teste:
    def teste_domain(self):
        e = Emisiune("nume", "tip", 30, "descriere")
        assert e.get_nume() == "nume"
        assert e.get_tip() == "tip"
        assert e.get_durata() == 30
        assert e.get_descriere() == "descriere"

        e.set_nume("nume2")
        assert e.get_nume() == "nume2"

        e.set_tip("tip2")
        assert e.get_tip() == "tip2"

        e.set_durata(40)
        assert e.get_durata() == 40

        e.set_descriere("descriere2")
        assert e.get_descriere() == "descriere2"

    def teste_repo(self):
        repo = RepositoryEmisiuni()
        lista = repo.get_all()
        repo.adauga_emisiune(Emisiune("nume", "tip", 30, "descriere"))

        assert len(lista) == 1
        assert lista[0].get_nume() == "nume"
        assert lista[0].get_tip() == "tip"
        assert lista[0].get_durata() == 30
        assert lista[0].get_descriere() == "descriere"

    def teste_controller(self):
        repo = RepositoryEmisiuni()
        controller = ControllerEmisiuni(RepositoryEmisiuni(), ValidatorEmisiune)
        lista = repo.get_all()
        repo.adauga_emisiune(Emisiune("nume", "tip", 30, "descriere"))
#        controller.sterge_emisiune("nume", "tip")

        assert len(lista) == 0


    def ruleaza_teste(self):
        self.teste_domain()
        self.teste_repo()
        self.teste_controller()