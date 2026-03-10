from recursiv.generate import generate
from recursiv.teste import teste_recursiv

teste_recursiv()

n = int(input("Introdu un numar pozitiv: "))
lst = []
lst_solutie = []
generate(lst, n, lst_solutie)
for elem in lst_solutie:
    print(elem)