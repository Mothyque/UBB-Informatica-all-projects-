from Domain.Validator import Validator
from Repository.RepositorySpectacole import RepositorySpectacoleFile
from Service.ControllerSpectacole import ControllerSpectacole
from Ui.meniu import Meniu

repo = RepositorySpectacoleFile("Fisier\\spectacole.txt")
validator = Validator()
controller = ControllerSpectacole(repo, validator)
meniu = Meniu(controller)
meniu.ruleaza_meniu()
