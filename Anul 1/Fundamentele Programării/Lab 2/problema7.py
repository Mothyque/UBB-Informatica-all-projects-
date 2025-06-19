m = int(input("m = "))

prod = 0

for x in range (2, m // 2 + 1):
    if m % x == 0:
        if prod == 0:
            prod += 1
        prod *= x

print(prod)