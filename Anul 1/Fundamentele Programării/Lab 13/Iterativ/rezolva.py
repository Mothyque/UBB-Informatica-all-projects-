from iterativ.prim import prim


def rezolva(s):
    lst = [(2, [], s)]
    lista_finala = []
    while lst:
        start, lst_curent, remaining = lst.pop()
        if remaining == 0:
            lista_finala.append(lst_curent)
        elif remaining > 0:
            for i in range(start, remaining + 1):
                if prim(i):
                    lst.append((i, lst_curent + [i], remaining - i))
    return lista_finala