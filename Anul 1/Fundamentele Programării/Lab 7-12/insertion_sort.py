def insertion_sort(lst, key = lambda x:x, reverse = False, cmp = lambda x, y: x < y):
    """
    Sorteaza o lista folosind algoritmul de sortare prin insertie.
    :param lst: lista de sortat
    :param key: cheia dupa care se face sortarea
    :param reverse: True daca se doreste sortarea descrescator, False altfel
    :param cmp: functie de comparare
    :return: lista sortata
    """
    n = len(lst)
    if n <= 1:
        return lst

    if not reverse:
        for i in range(1, n):
            val_curent = lst[i]
            j = i - 1
            while j >= 0 >= cmp(key(val_curent), key(lst[j])):
                lst[j + 1] = lst[j]
                j -= 1
            lst[j + 1] = val_curent
    else:
        for i in range(1, n):
            val_curent = lst[i]
            j = i - 1
            while j >= 0 and cmp(key(val_curent), key(lst[j])) >= 0:
                lst[j + 1] = lst[j]
                j -= 1
            lst[j + 1] = val_curent
    return lst