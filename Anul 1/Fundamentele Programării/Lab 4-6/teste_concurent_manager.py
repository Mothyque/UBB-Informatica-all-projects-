from list_manager.concurent_manager import modificare_scor_proba, filtrare_multiplu, filtrare_scor_maxim, \
    sterge_scor_proba


def teste_concurent_manager():
    #Test modificare_scor_proba
    lista_concurenti = [
        [1, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 55],
        [2, [5, 6, 8, 10, 9, 2, 3, 4, 5, 6], 58],
        [3, [7, 8, 1, 2, 3, 5, 6, 8, 1, 10], 51]
    ]
    modificare_scor_proba(lista_concurenti, 1, 2, 5)
    assert lista_concurenti[0][1][1] == 5
    assert lista_concurenti[0][2] == sum(lista_concurenti[0][1])

    #Test filtrare_multiplu
    lista_concurenti = [
        [1, [2, 4, 6, 8, 10, 2, 4, 6, 8, 10], 60],
        [2, [3, 6, 9, 6, 9, 3, 6, 9, 6, 9], 66],
        [3, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1], 10]
    ]
    filtrare_multiplu(lista_concurenti, 3, 2)
    assert lista_concurenti[0][1][1] == 0
    assert lista_concurenti[1][1][1] == 6
    assert lista_concurenti[2][1][1] == 0

    #Test filtrare_multiplu_total_score
    lista_concurenti = [
        [1, [2, 4, 6, 8, 10, 2, 4, 6, 8, 10], 60],
        [2, [3, 6, 9, 6, 9, 3, 6, 9, 6, 9], 66],
        [3, [1, 1, 1, 1, 1, 1, 1, 1, 1, 1], 10]
    ]
    lista_aux = filtrare_multiplu(lista_concurenti, 5, 't')
    assert lista_aux[0][1] == [2, 4, 6, 8, 10, 2, 4, 6, 8, 10]
    assert lista_aux[1][1] == [0] * 10
    assert lista_aux[2][1] == [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]

    #Test filtrare_scor_maxim
    lista_concurenti = [
        [1, [1, 3, 5, 7, 9, 2, 4, 6, 8, 10], 55],
        [2, [2, 4, 6, 8, 5, 7, 3, 6, 8, 10], 59],
        [3, [3, 3, 3, 3, 3, 3, 3, 3, 3, 3], 30]
    ]
    lista_aux = filtrare_scor_maxim(lista_concurenti, 6, 3)
    assert lista_aux[0][1][2] == 0
    assert lista_aux[1][1][2] == 6
    assert lista_aux[2][1][2] == 0

    #Test filtrare_scor_maxim_total_score
    lista_concurenti = [
        [1, [1, 3, 5, 7, 9, 2, 4, 6, 8, 10], 55],
        [2, [2, 4, 6, 8, 5, 7, 3, 6, 8, 10], 59],
        [3, [3, 3, 3, 3, 3, 3, 3, 3, 3, 3], 30]
    ]
    filtrare_scor_maxim(lista_concurenti, 40, 't')
    assert lista_concurenti[0][1] == [1, 3, 5, 7, 9, 2, 4, 6, 8, 10]
    assert lista_concurenti[1][1] == [2, 4, 6, 8, 5, 7, 3, 6, 8, 10]
    assert lista_concurenti[2][1] == [0] * 10

    #Test sterge_scor_proba
    sterge_scor_proba(lista_concurenti, 1, 5)
    assert lista_concurenti[0][1][4] == 0

