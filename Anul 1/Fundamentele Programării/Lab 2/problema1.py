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


m = int(input("m = "))
m += 1
ok = 0

while ok == 0:
    if e_prim(m):
        print(m)
        ok = 1

    m += 1