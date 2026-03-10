def e_prim(n):
    if n < 2:
        return 0
    elif n == 2:
        return 1
    elif n % 2 == 0:
        return 0
    else:
        for x in range (3, n, 2):
            if n % x == 0:
                return 0
        return 1

nr = int(input("n = "))
if nr <= 2:
    print("Nu exista numar prim mai mic decat n")
nr -= 1
while nr != 1:
    if e_prim(nr) == 1:
        print(nr)
        break
    else:
        nr -= 1
