from domain.validare import validare_scor, validare_interval, validare_proba, validare_cod, validare_undo


def teste_validare():
    #Test validare_scor
    try:
        validare_scor(5)
    except ValueError:
        assert False

    try:
        validare_scor(11)
        assert False
    except ValueError as e:
        assert str(e) == "Scor incorect. Scorul trebuie sa ia valori intre 1 si 10."


    #Test validare_interval
    lista_concurenti = [1, 2, 3, 4, 5]
    try:
        validare_interval(lista_concurenti, 1, 5)
    except ValueError:
        assert False

    try:
        validare_interval(lista_concurenti, 6, 5)
        assert False
    except ValueError as e:
        assert str(e) == "Interval invalid! Capatul inferior este mai mare decat cel superior!"

    try:
        validare_interval(lista_concurenti, 0, 5)
        assert False
    except ValueError as e:
        assert str(e) == "Interval invalid! Capatul inferior este prea mic!"

    try:
        validare_interval(lista_concurenti, 1, 6)  # Invalid interval, b > len(lista)
        assert False
    except ValueError as e:
        assert str(e) == "Interval invalid! Capatul superior este prea mare!"


    # Test validare_proba
    try:
        validare_proba('5')
    except ValueError:
        assert False

    try:
        validare_proba('t')
    except ValueError:
        assert False

    try:
        validare_proba('11')
        assert False
    except ValueError as e:
        assert str(e) == "Proba incorecta. Proba trebuie sa ia valori intre 1 si 10."

    try:
        validare_proba('abc')
        assert False
    except ValueError as e:
        assert str(e) == "Proba incorecta. Proba trebuie sa fie un numar natural intre 1 si 10."

    #Test validare_cod
    lista_concurenti = [1, 2, 3]
    try:
        validare_cod(lista_concurenti, 2)
    except ValueError:
        assert False

    try:
        validare_cod(lista_concurenti, 0)
        assert False
    except ValueError as e:
        assert str(e) == "Nu exista participant cu acest cod."

    #Test validare_undo
    lista_undo = [1]
    try:
        validare_undo(lista_undo)
    except ValueError:
        assert False

    lista_undo = []
    try:
        validare_undo(lista_undo)
        assert False
    except ValueError as e:
        assert str(e) == "Nu se mai pot face operatii de undo!"
