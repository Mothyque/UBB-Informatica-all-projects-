m = int(input("ziua este "))
n = int(input("anul este "))

bisect = 0

if (n % 4 == 0 and n % 100 != 0) or n % 400 == 0:
    bisect = 1

zilele_lunii = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

if bisect == 1:
    zilele_lunii[1] = 29
i = 0
luna = 1
while m > zilele_lunii[i]:
    m -= zilele_lunii[i]
    luna += 1
    i += 1

print(f"Ziua este {m}")
print(f"Luna este {luna}")
print(f"Anul este {n}")