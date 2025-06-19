def selection_sort(lista):
    for i in range(len(lista)):
        pivot = i
        for j in range(i + 1, len(lista)):
            if lista[pivot] > lista[j]:
                pivot = j
        lista[pivot], lista[i] = lista[i], lista[pivot]
    return lista

def f(n):
    """
    Functia calculeaza si returneaza al n-lea termen din sirul lui fibonacci
    :param n: numar natural
    :return: al n-lea termen din sirul fibonacci
    :raises: ValueError daca n este negativ
    """
    if n < 0: raise ValueError()
    if n <= 1: return n
    l = [0] * (n + 1)
    l[1] = 1
    for i in range(2, n + 1):
        l[i] = l[i - 1] + l[i - 2]
    return l[n]

def test_f():
    assert f(1) == 1
    assert f(2) == 1
    assert f(3) == 2
    assert f(4) == 3
    assert f(5) == 5
    assert f(6) == 8
    assert f(13) == 233

    try:
        assert f(-2148)
    except ValueError:
        print("Numar negativ")

test_f()

def divide_et_impera(lista):
    if len(lista) == 0:
        return None
    if len(lista) == 1:
        return lista[0]

    return max(divide_et_impera(lista[:len(lista)//2]), divide_et_impera(lista[len(lista)//2:]))


print(divide_et_impera([2,8,4,1,4,5,6,7]))
