n = int(input("n = "))

rez = 0

cifre = []
while n != 0:
    cifre.append(n % 10)
    n //= 10

sortat = 0

while sortat == 0:
    sortat = 1
    for x in range (0, len(cifre) - 1):
        if cifre[x] < cifre[x + 1]:
            sortat = 0
            aux = cifre[x]
            cifre[x] = cifre[x + 1]
            cifre[x + 1] = aux

p = 1
for x in range (0, len(cifre)):
    rez += cifre[x] * p
    p *= 10

print(rez)
