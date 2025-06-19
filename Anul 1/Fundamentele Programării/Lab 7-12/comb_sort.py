def comb_sort(lst, key=lambda x:x, reverse=False, cmp=lambda x, y: x < y):
    """
    Sorteaza o lista folosind algoritmul de sortare comb.
    :param lst: lista de sortat
    :param key: cheia dupa care se face sortarea
    :param reverse: True daca se doreste sortarea descrescator, False altfel
    :param cmp: functie de comparare
    :return: lista sortata
    """
    n = len(lst)
    if n <= 1:
        return lst
    gap = n
    swapped = True
    if not reverse:
        while gap != 1 or swapped:
            gap = int((gap * 10) // 13)
            if gap < 1:
                gap = 1
            swapped = False
            for i in range(n - gap):
                if cmp(key(lst[i + gap]), key(lst[i])) <= 0:
                    lst[i], lst[i + gap] = lst[i + gap], lst[i]
                    swapped = True
    else:
        while gap != 1 or swapped:
            gap = int((gap * 10) // 13)
            if gap < 1:
                gap = 1
            swapped = False
            for i in range(n - gap):
                if cmp(key(lst[i]), key(lst[i + gap])) >= 0:
                    lst[i], lst[i + gap] = lst[i + gap], lst[i]
                    swapped = True
    return lst