import unittest

from repository.problem_repository import ProblemRepository
from services.problem_controller import ProblemController


class TestCaseProblemController(unittest.TestCase):
    def setUp(self):
        self.problem_controller = ProblemController(ProblemRepository())
        lista = self.problem_controller.problem_repository.get_all()
        lista.clear()
        self.problem_controller.adauga_problema("1_1", "Cauta numar prim", 2)
        self.problem_controller.adauga_problema("2_1", "Cauta numar par", 3)
        self.problem_controller.adauga_problema("1_2", "Cauta numar impar", 4)
        self.problem_controller.adauga_problema("3_1", "Cauta numar perfect", 5)
        self.assertEqual(len(lista), 4)

    def test_adauga_problema(self):
        self.problem_controller.adauga_problema("3_2", "Cauta numar prim", 6)
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(len(lista), 5)

    def test_sterge_problema(self):
        self.problem_controller.sterge_problema("numar", "1_1")
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(len(lista), 3)

        self.problem_controller.sterge_problema("deadline", "4")
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(len(lista), 2)

    def test_modifica_problema(self):
        self.problem_controller.modifica_problema("numar", "2_1", "3_3")
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(lista[1].get_numar_lab_numar_prob(), "3_3")

        self.problem_controller.modifica_problema("descriere", "3_3", "Cauta numar impar")
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(lista[1].get_descriere(), "Cauta numar impar")

        self.problem_controller.modifica_problema("deadline", "3_3", 4)
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(lista[1].get_deadline(), 4)

    def test_cauta_problema(self):
        lista = self.problem_controller.cauta_problema("problema", "3_1")
        self.assertEqual(len(lista), 1)

        lista = self.problem_controller.cauta_problema("laborator", "3")
        self.assertEqual(len(lista), 1)

        lista = self.problem_controller.cauta_problema("deadline", 5)
        self.assertEqual(len(lista), 1)

    def test_get_lista_probleme(self):
        lista = self.problem_controller.get_lista_probleme()
        self.assertEqual(len(lista), 4)

    def test_random_problem(self):
        self.problem_controller.random_problem(2)
        lista = self.problem_controller.problem_repository.get_all()
        self.assertEqual(len(lista), 6)

    def tearDown(self):
        lista = self.problem_controller.problem_repository.get_all()
        lista.clear()
