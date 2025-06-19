n = int(input("n = "))

suma = 0

lista = []

for x in range(n):
    l = int(input())
    lista.append(l)

for x in range(n):
    suma += lista[x]

print(suma)