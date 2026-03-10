def rezolvare(n):
    def binar(n):
        if n == 0: 
            return ""
        return binar(n // 2) + str(n % 2) 
    rez = []
    for i in range(1, n + 1):
        rez.append(binar(i))
    return rez

def rezolvare_ai(n):
    """
    Generează o listă de șiruri de caractere reprezentând 
    numerele de la 1 la n în format binar.
    """
    return [f"{i:b}" for i in range(1, n + 1)]

def main():
    assert rezolvare(4) == ['1', '10', '11', '100']
    assert rezolvare(1) == ['1']
    assert rezolvare(2) == ['1', '10']
    assert rezolvare(5) == ['1', '10', '11', '100', '101']
    assert rezolvare_ai(4) == ['1', '10', '11', '100']
    assert rezolvare_ai(1) == ['1']
    assert rezolvare_ai(2) == ['1', '10']
    assert rezolvare_ai(5) == ['1', '10', '11', '100', '101']
    print(rezolvare(4))
    print(rezolvare_ai(4))

main()