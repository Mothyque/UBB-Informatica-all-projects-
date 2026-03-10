def suma_div(n):
    sumad = 0
    for x in range(1, n - 1):
        if n % x == 0:
            sumad += x
    return sumad



m = int(input("m = "))
m -= 1
while m > 1:
    if suma_div(m) == m:
        print(m)
        break
    m -= 1

if m == 1:
    print("Nu exista numar perfect mai mic decat m")