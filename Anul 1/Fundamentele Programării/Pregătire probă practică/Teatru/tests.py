from Domain.teatru import Teatru
from Domain.validator import Validator
from Repository.repository_teatru import RepositoryTeatru
from Services.controller_teatru import ControllerTeatru


class Tests:

    def test_domain(self):
        t = Teatru("Titlu", "Regizor", "Altele", 3)

        assert t.get_titlu() == "Titlu"
        assert t.get_regizor() == "Regizor"
        assert t.get_gen() == "Altele"
        assert t.get_durata() == 3

    def test_repository(self):
        repo = RepositoryTeatru()
        lista = repo.get_all()
        assert len(lista) == 0
        t = Teatru("Titlu", "Regizor", "Altele", 3)
        repo.adauga_teatru(t)
        lista = repo.get_all()
        assert len(lista) == 1

        repo.modifica_teatru(t, Teatru("Titlu", "Regizor", "Altele", 4))
        lista = repo.get_all()
        assert lista[0].get_durata() == 4

    def test_controller(self):
        controller = ControllerTeatru(RepositoryTeatru(), Validator())
        lista = controller.afiseaza_spectacole()
        assert len(lista) == 0

        controller.adauga_spectacol("Titlu", "Regizor", "Altele", 3)
        lista = controller.afiseaza_spectacole()
        assert len(lista) == 1

        controller.modifica_spectacol("Titlu", "Regizor", "Altele", 4)
        lista = controller.afiseaza_spectacole()
        assert lista[0].get_durata() == 4

        controller.genereaza_piese(10)
        lista = controller.afiseaza_spectacole()
        assert len(lista) == 11


    def ruleaza_teste(self):
        self.test_domain()
        self.test_repository()
        self.test_controller()
