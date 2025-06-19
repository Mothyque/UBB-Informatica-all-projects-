from teste.teste_utils import test_utils
from teste.teste_concurent import teste_concurent
from teste.teste_concurent_manager import teste_concurent_manager
from teste.teste_validare import teste_validare


def teste():
    teste_concurent()
    teste_concurent_manager()
    test_utils()
    teste_validare()