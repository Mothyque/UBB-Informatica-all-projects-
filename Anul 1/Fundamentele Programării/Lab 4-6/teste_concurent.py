from domain.concurent import creare_concurent, get_cod, get_scor_proba, get_scor_total, set_cod, set_scor_proba, \
    set_scor_total


def teste_concurent():
    #Test creare
    assert creare_concurent(4, [3, 4, 5, 6, 7, 8, 9, 2, 1, 3], 0) == [4, [3, 4, 5, 6, 7, 8, 9, 2, 1, 3], 48]

    #Test get_cod
    concurent = creare_concurent(4, [3, 4, 5, 6, 7, 8, 9, 2, 1, 3], 0)
    assert get_cod(concurent) == 4

    #Teste get_scor_proba
    assert get_scor_proba(concurent, 1) == 3
    assert get_scor_proba(concurent, 5) == 7

    #Test get_scor_total
    assert get_scor_total(concurent) == 48

    #Test set_cod
    set_cod(concurent, 5)
    assert get_cod(concurent) == 5

    #Test set_scor_proba
    set_scor_proba(concurent, 2, 6)
    assert get_scor_proba(concurent, 2) == 6

    #Test set_scor_total
    set_scor_total(concurent, 52)
    assert get_scor_total(concurent) == 52
