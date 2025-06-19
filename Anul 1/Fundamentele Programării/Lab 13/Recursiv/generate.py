from recursiv.prim import prim


def generate(lista, x, lista_solutie, start = 2):
    if sum(lista) == x:
        lista_solutie.append(lista[:])
        return
    elif sum(lista) > x:
        return
    else:
        for i in range(start, x+1):
            if prim(i):
                lista.append(i)
                generate(lista[:],x, lista_solutie, i)
                lista.pop()

