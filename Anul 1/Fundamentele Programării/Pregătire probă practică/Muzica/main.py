from Domain.validator import Validator
from Repository.repository_muzica import RepositoryFileMuzica
from Service.controller_muzica import ControllerMuzica
from Ui.meniu import Meniu

repo = RepositoryFileMuzica("fisier\\muzica.txt")
validator = Validator()
controller = ControllerMuzica(repo, validator)
meniu = Meniu(controller)
meniu.ruleaza_meniu()