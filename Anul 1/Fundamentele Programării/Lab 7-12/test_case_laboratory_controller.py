import unittest

from repository.laboratory_repository import LaboratoryRepository
from repository.problem_repository import ProblemRepository
from repository.student_repository import StudentRepository
from services.laboratory_controller import LaboratoryController


class TestCaseLaboratoryController(unittest.TestCase):
    def setUp(self):
        self.problem_repository = ProblemRepository()
        self.student_repository = StudentRepository()
        self.laboratory_repository = LaboratoryRepository(self.student_repository)
        self.laboratory_controller = LaboratoryController(self.laboratory_repository, self.problem_repository, self.student_repository)
        lista = self.laboratory_controller.laboratory_repository.get_all()
        lista.clear()
        self.assertEqual(len(lista), 0)
        self.laboratory_controller.asigneaza_laborator(1, "1_5")
        self.laboratory_controller.asigneaza_laborator(3, "2_1")
        self.laboratory_controller.asigneaza_laborator(4, "1_5")
        self.laboratory_controller.asigneaza_laborator(5, "3_3")
        self.assertEqual(len(lista), 4)

    def test_asigneaza_laborator(self):
        self.laboratory_controller.asigneaza_laborator(2, "1_5")
        lista = self.laboratory_controller.laboratory_repository.get_all()
        self.assertEqual(len(lista), 5)

    def test_asigneaza_laborator_black_box(self):
        self.laboratory_controller.asigneaza_laborator(6, "1_5")
        lista = self.laboratory_controller.laboratory_repository.get_all()
        lab_assigned = any(lab.get_student_id_lab() == 4 and lab.get_numar_lab_numar_prob_lab() == "1_5" for lab in lista)
        self.assertTrue(lab_assigned)

    def test_noteaza_laborator(self):
        self.laboratory_controller.noteaza_laborator(1, "1_5", 10)
        lista = self.laboratory_controller.laboratory_repository.get_all()
        self.assertEqual(lista[0].get_nota(), 10)

    def test_get_lista_iduri(self):
        lista = self.laboratory_controller.get_lista_iduri()
        self.assertEqual(lista, [1, 3, 4, 5])

    def test_get_lista_laboratoare(self):
        lista = self.laboratory_controller.get_lista_laboratoare()
        self.assertEqual(len(lista), 4)

    def test_sterge_lab_id(self):
        self.laboratory_controller.sterge_laborator_id(1)
        lista = self.laboratory_controller.laboratory_repository.get_all()
        self.assertEqual(len(lista), 3)

    def test_sterge_laborator_numar_lab_numar_problema(self):
        self.laboratory_controller.sterge_laborator_numar_lab_numar_problema("1_5")
        lista = self.laboratory_controller.laboratory_repository.get_all()
        self.assertEqual(len(lista), 2)

    def test_lista_studenti_ordonata(self):
        self.laboratory_controller.noteaza_laborator(1, "1_5", 8)
        self.laboratory_controller.noteaza_laborator(4, "1_5", 9)
        lista_noua = self.laboratory_controller.lista_studenti_ordonata("nota", "1_5")
        self.assertEqual(lista_noua, [['Mihai', 9], ['Andrei', 8]])

        lista_noua2 = self.laboratory_controller.lista_studenti_ordonata("nume", "1_5")
        self.assertEqual(lista_noua2, [['Andrei', 8], ['Mihai', 9]])

    def test_lista_sub_5(self):
        self.laboratory_controller.noteaza_laborator(1, "1_5", 4)
        self.laboratory_controller.noteaza_laborator(3, "2_1", 4)
        lista_noua = self.laboratory_controller.lista_studenti_sub_5()
        self.assertEqual(lista_noua, [['Andrei', 4], ['Paul', 4]])

    def test_lista_studenti_activi(self):
        self.laboratory_controller.noteaza_laborator(1, "1_5", 4)
        self.laboratory_controller.noteaza_laborator(3, "2_1", 4)
        self.laboratory_controller.asigneaza_laborator(1, "2_1")
        self.laboratory_controller.noteaza_laborator(1, "2_1", 4)
        lista_noua = self.laboratory_controller.lista_studenti_activi()
        self.assertEqual(lista_noua, [['Andrei', 2], ['Paul', 1]])

    def tearDown(self):
        self.laboratory_controller.laboratory_repository.clear()