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

for x in range(2, m):
    if e_prim(x) == 1:
        if e_prim(m - x) == 1:
            print(x)
            print(m - x)
            break

