from Domain.validator import Validator
from Repository.repository_teatru import RepositoryTeatruFile
from Services.controller_teatru import ControllerTeatru
from Ui.meniu import Meniu
from tests.tests import Tests

test = Tests()
test.ruleaza_teste()

repo = RepositoryTeatruFile("fisier/teatru.txt")
validator = Validator()
controller = ControllerTeatru(repo, validator)
meniu = Meniu(controller)

meniu.ruleaza_meniu()