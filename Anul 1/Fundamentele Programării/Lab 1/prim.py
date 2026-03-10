n = int(input("n = "))

ok = 1

if n < 2:
    print("Nu este prim")
if n == 2:
    print("Este prim")
else:
    if n % 2 == 0:
        print("Nu este prim")
    else:
        for x in range(3, n, 2):
            if n % x == 0:
                ok = 0
                break
        if ok == 0:
            print("Nu este prim")
        else:
            print("Este prim")
