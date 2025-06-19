a = int(input("a = "))
b = int(input("b = "))

if b == 0:
    print(a)
if a == 0:
    print(b)
else:
    while a != b:
        if a > b:
            a -= b
        else:
            b -= a
    print(a)