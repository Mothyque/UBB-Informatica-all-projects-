from iterativ.rezolva import rezolva
from iterativ.teste import test_iterativ

test_iterativ()


n = int(input("Introdu un numar pozitiv: "))
lista = rezolva(n)
for i in range(len(lista) - 1, -1, -1):
    print(lista[i])
