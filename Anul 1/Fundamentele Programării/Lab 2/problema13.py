def e_prim(n):
    if n < 2:
        return 0
    elif n == 2:
        return 1
    elif n % 2 == 0:
        return 0
    else:
        for j in range (3, n, 2):
            if n % j == 0:
                return 0
        return 1


x = int(input("n este: "))
aux = 1
while x > 0:
    scadem1 = 0
    if e_prim(aux) or aux == 1:
        scadem1 = 1
    if aux % 2 == 0 and e_prim(aux) != 1:
        x -= 2
        if x <= 0:
            print(2)
            break

    for i in range(3, aux, 2):
        if e_prim(i) and aux % i == 0:
            x -= i
            if x <= 0:
                print(i)
                break
    if scadem1 == 1:
        x -= 1
        if x <= 0:
            print(aux)
            break
    aux += 1