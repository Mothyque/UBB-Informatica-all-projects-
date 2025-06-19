n = int(input("Primul numar este: "))
m = int(input("Al doilea numar este: "))

frn = ([0])
frn *= 10

frm = [0] * 10

while n > 0:
    frn[n % 10] += 1
    n //= 10

while m > 0:
    frm[m % 10] += 1
    m //= 10

ok = 1

for x in range (0, 9):
    if frn[x] == 0:
        if frm[x] != 0:
            ok = 0
    if frm[x] == 0:
        if frn[x] != 0:
            ok = 0
if ok == 1:
    print("Numerele au proprietatea 🅿️")
else:
    print("Numerele nu au proprietatea 🅿️")