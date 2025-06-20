from Domain.validator import Validator
from Repository.repository_evenimente import RepositoryEvenimenteFile
from Services.controller_evenimente import ControllerEvenimente
from Ui.meniu import Meniu

repo = RepositoryEvenimenteFile("Files\\evenimente.txt")
validator = Validator()
controller = ControllerEvenimente(repo, validator)
meniu = Meniu(controller)
meniu.ruleaza_meniu()
