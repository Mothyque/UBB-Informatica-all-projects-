def cmp_grupa_asc_nume_desc(x, y):
    """
    Compara doi studenti in functie de grupa si nume.
    :param x: primul student
    :param y: al doilea student
    :return: -1 daca x < y, 0 daca x == y, 1 daca x > y
    """
    if x.get_group() < y.get_group():
        return -1
    elif x.get_group() > y.get_group():
        return 1
    elif x.get_group() == y.get_group():
        if x.get_name() > y.get_name():
            return -1
        elif x.get_name() < y.get_name():
            return 1
        else:
            return 0
