from utils.cautare_multiplu_de_10 import cautare_multiplu_de_10
from utils.media_aritmetica_interval_proba import media_aritmetica_interval_proba
from utils.scor_minim_int_proba import scor_minim_int_proba
from utils.sortare_dupa_scor_proba import sortare_dupa_scor_proba


def test_utils():
    #Test cautare_multiplu_de_10_proba
    lista_concurenti = [
        [1, [10, 3, 5, 7, 9, 2, 4, 10, 8, 10], 68],
        [2, [2, 4, 10, 8, 10, 7, 3, 6, 10, 10], 70],
        [3, [3, 3, 3, 3, 3, 3, 3, 3, 3, 3], 30]
    ]
    result = cautare_multiplu_de_10(lista_concurenti, 1, 3, 1)
    assert result == [[1, [10, 3, 5, 7, 9, 2, 4, 10, 8, 10], 68]]

    result = cautare_multiplu_de_10(lista_concurenti, 1, 3, 3)
    assert result == [[2, [2, 4, 10, 8, 10, 7, 3, 6, 10, 10], 70]]

    result = cautare_multiplu_de_10(lista_concurenti, 1, 3, 't')
    assert result == [[2, [2, 4, 10, 8, 10, 7, 3, 6, 10, 10], 70], [3, [3, 3, 3, 3, 3, 3, 3, 3, 3, 3], 30]]


    #Test media_aritmetica_interval_proba
    lista_concurenti = [
        [1, [7, 8, 5, 10, 6, 9, 4, 10, 8, 6], 73],
        [2, [5, 6, 10, 8, 7, 10, 6, 9, 10, 8], 79],
        [3, [10, 9, 8, 7, 6, 5, 4, 3, 2, 1], 55]
    ]
    result = media_aritmetica_interval_proba(lista_concurenti, 1, 2, 3)
    assert result == (5 + 10) / 2

    result = media_aritmetica_interval_proba(lista_concurenti, 1, 3, 4)
    assert result == (10 + 8 + 7) / 3

    result = media_aritmetica_interval_proba(lista_concurenti, 1, 2, 't')
    assert result == (73 + 79) / 2

    result = media_aritmetica_interval_proba(lista_concurenti, 1, 3, 't')
    assert result == (73 + 79 + 55) / 3


    #Test scor_minim_int_proba
    lista_concurenti = [
        [1, [6, 8, 5, 10, 6, 9, 4, 10, 8, 6], 72],
        [2, [5, 6, 10, 8, 7, 10, 6, 9, 10, 8], 79],
        [3, [10, 9, 8, 7, 6, 5, 4, 3, 2, 1], 55]
    ]
    result = scor_minim_int_proba(lista_concurenti, 1, 3, 3)
    assert result == 5  # Minimum score at probe 3 in the interval [1, 3]

    result = scor_minim_int_proba(lista_concurenti, 1, 2, 4)
    assert result == 8  # Minimum score at probe 4 in the interval [1, 2]

    result = scor_minim_int_proba(lista_concurenti, 1, 3, 't')
    assert result == 55  # Minimum total score in the interval [1, 3]

    result = scor_minim_int_proba(lista_concurenti, 2, 3, 't')
    assert result == 55  # Minimum total score in the interval [2, 3]

    #Test sortare_dupa_scor_proba
    lista_concurenti = [
        [1, [6, 8, 5, 10, 6, 9, 4, 10, 8, 6], 72],
        [2, [5, 6, 10, 8, 7, 10, 6, 9, 10, 8], 79],
        [3, [10, 9, 8, 7, 6, 5, 4, 3, 2, 1], 55]
    ]
    sortare_dupa_scor_proba(lista_concurenti, 3)
    assert lista_concurenti == [
        [1, [6, 8, 5, 10, 6, 9, 4, 10, 8, 6], 72],
        [3, [10, 9, 8, 7, 6, 5, 4, 3, 2, 1], 55],
        [2, [5, 6, 10, 8, 7, 10, 6, 9, 10, 8], 79]
    ]

    sortare_dupa_scor_proba(lista_concurenti, 't')
    assert lista_concurenti == [
        [3, [10, 9, 8, 7, 6, 5, 4, 3, 2, 1], 55],
        [1, [6, 8, 5, 10, 6, 9, 4, 10, 8, 6], 72],
        [2, [5, 6, 10, 8, 7, 10, 6, 9, 10, 8], 79]
    ]
