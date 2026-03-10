import unittest

from repository.laboratory_repository import LaboratoryRepository
from repository.laboratory_repository_file import LaboratoryRepositoryFile
from repository.problem_repository_file import ProblemRepositoryFile
from repository.student_repository import StudentRepository
from repository.student_repository_file import StudentRepositoryFile
from services.laboratory_controller import LaboratoryController
from services.problem_controller import ProblemController
from services.student_controller import StudentController
from repository.problem_repository import ProblemRepository
from teste.test_case_laboratory_controller import TestCaseLaboratoryController
from teste.test_case_problem_controller import TestCaseProblemController
from teste.test_case_student_controller import TestCaseStudentController
from ui.meniu import Meniu

def run():
    while True:
        print("Doresti sa folosesti fisiere text?")
        optiune = input("Da / Nu: ")
        if optiune.lower() == "da":
            student_repository = StudentRepositoryFile("utils/studenti.txt")
            problem_repository = ProblemRepositoryFile("utils/probleme.txt")
            laboratory_repository = LaboratoryRepositoryFile("utils/laboratoare.txt")
            print('-------------------------------------------------------')
            break

        elif optiune == "nu":
            student_repository = StudentRepository()
            problem_repository = ProblemRepository()
            laboratory_repository = LaboratoryRepository(student_repository)
            print('-------------------------------------------------------')
            break
        else:
            print("Optiune invalida!")
    student_controller = StudentController(student_repository)
    problem_controller = ProblemController(problem_repository)
    laboratory_controller = LaboratoryController(laboratory_repository, problem_repository, student_repository)
    meniu = Meniu(student_controller, problem_controller, laboratory_controller)
    meniu.meniu_principal()