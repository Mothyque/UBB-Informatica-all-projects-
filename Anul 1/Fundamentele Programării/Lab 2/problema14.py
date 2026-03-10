def suma_div(n):
    sumad = 0
    for x in range(1, n - 1):
        if n % x == 0:
            sumad += x
    return sumad



m = int(input("m = "))
m += 1
ok = 0

while ok == 0:
    if suma_div(m) == m:
        ok = 1
        print(m)
    m += 1