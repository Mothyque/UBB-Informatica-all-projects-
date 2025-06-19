from iterativ.rezolva import rezolva


def test_iterativ():
    lista = rezolva(10)
    assert lista == [[5, 5], [3, 7], [2, 3, 5], [2, 2, 3, 3], [2, 2, 2, 2, 2]]

    lista = rezolva(5)
    assert lista == [[5], [2, 3]]