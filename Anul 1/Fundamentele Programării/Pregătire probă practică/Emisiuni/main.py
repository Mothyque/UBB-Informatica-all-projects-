from Domain.validator import ValidatorEmisiune
from Repository.repository_emisiuni import RepositoryFileEmisiuni
from Services.controller_emisiuni import ControllerEmisiuni
from Tests.tests import Teste
from UI.meniu import Meniu

teste = Teste()
teste.ruleaza_teste()

repo = RepositoryFileEmisiuni("Fisiere\\emisiuni.txt")
validator = ValidatorEmisiune
controller = ControllerEmisiuni(repo, validator)
meniu = Meniu(controller)
meniu.ruleaza_meniu()