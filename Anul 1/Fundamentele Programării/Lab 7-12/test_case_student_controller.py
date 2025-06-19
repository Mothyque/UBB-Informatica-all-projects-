import unittest

from repository.student_repository import StudentRepository
from services.student_controller import StudentController


class TestCaseStudentController(unittest.TestCase):
    def setUp(self):
        self.student_controller = StudentController(StudentRepository())
        lista = self.student_controller.student_repository.get_all()
        lista.clear()
        self.assertEqual(len(lista), 0)
        self.student_controller.adauga_student("1", "Ana", 911)
        self.student_controller.adauga_student("3", "Andrei", 912)
        self.student_controller.adauga_student("4", "Cosmin", 913)
        self.student_controller.adauga_student("5", "Paul", 214)
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(len(lista), 4)



    def test_adauga_student(self):
        self.student_controller.adauga_student("2", "Maria", 211)
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(len(lista), 5)


    def test_sterge_student(self):
        self.student_controller.sterge_student("id", "1")
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(len(lista), 3)

        self.student_controller.sterge_student("nume", "Andrei")
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(len(lista), 2)

        self.student_controller.sterge_student("grupa", "913")
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(len(lista), 1)


    def test_modifica_student(self):
        self.student_controller.modifica_student("id", 3, "6")
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(lista[1].get_student_id(), 6)

        self.student_controller.modifica_student("nume", "5", "Ana")
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(lista[3].get_name(), "Ana")

        self.student_controller.modifica_student("grupa", 5, 212)
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(lista[3].get_group(), 212)

    def test_cauta_student(self):
        lista = self.student_controller.cauta_student("id", 1)
        self.assertEqual(len(lista), 1)

        lista = self.student_controller.cauta_student("nume", "Ana")
        self.assertEqual(len(lista), 1)

        lista = self.student_controller.cauta_student("grupa", 214)
        self.assertEqual(len(lista), 1)

    def test_get_lista_iduri(self):
        lista = self.student_controller.get_lista_iduri()
        self.assertEqual(lista, [1, 3, 4, 5])

    def test_random_student(self):
        self.student_controller.random_student(10)
        lista = self.student_controller.student_repository.get_all()
        self.assertEqual(len(lista), 14)

    def tearDown(self):
        lista = self.student_controller.student_repository.get_all()
        lista.clear()