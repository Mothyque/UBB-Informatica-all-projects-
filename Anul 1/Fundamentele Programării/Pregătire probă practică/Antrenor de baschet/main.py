from Domain.validator import Validator
from Repository.repository_baschet import RepositoryBaschetFile
from Services.controller_baschet import ControllerBaschet
from Ui.meniu import Meniu

repo = RepositoryBaschetFile("Fisier\\jucatori.txt")
validator = Validator()
controller = ControllerBaschet(repo, validator)
meniu = Meniu(controller)
meniu.ruleaza()
