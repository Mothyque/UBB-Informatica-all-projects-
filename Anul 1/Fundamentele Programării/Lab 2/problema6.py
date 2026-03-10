n = int(input("n = "))

f0 = 1
f1 = 1
ok = 1
while ok == 1:
    aux = f0
    f0 = f1
    f1 = f0 + aux
    if f1 > n:
        ok = 0
        print(f1)