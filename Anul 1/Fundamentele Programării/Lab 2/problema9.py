n = int(input("n = "))

rez = 0

cifre = []
while n != 0:
    cifre.append(n % 10)
    n //= 10

sortat = 0

p = 1
for x in range (len(cifre) - 1, 0, - 1):
    rez += cifre[x] * p
    p *= 10
rez += cifre[0] * p

print(rez)
