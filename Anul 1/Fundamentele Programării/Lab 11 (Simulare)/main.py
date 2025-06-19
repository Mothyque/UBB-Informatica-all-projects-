from repository.imobil_repo import ImobilRepoFile
from service.imobil_controller import ImobilController
from tests.teste_controller import TesteController
from tests.teste_domain import TesteDomain
from tests.teste_repo import TesteRepoFile
from ui.console import Console

repository = ImobilRepoFile("data/imobile.txt")
controller = ImobilController(repository)
meniu = Console(controller)
teste_domain = TesteDomain()
teste_repo = TesteRepoFile()
teste_controller = TesteController()

teste_domain.ruleaza_teste()
teste_repo.ruleaza_teste()
teste_controller.ruleaza_teste()
meniu.ruleaza_meniu()

