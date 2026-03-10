from recursiv.generate import generate


def teste_recursiv():
    lista = []
    generate([], 10, lista)
    assert lista == [[2, 2, 2, 2, 2], [2, 2, 3, 3], [2, 3, 5], [3, 7], [5, 5]]

    lista.clear()
    generate([], 7, lista)
    assert lista == [[2, 2, 3], [2, 5], [7]]
