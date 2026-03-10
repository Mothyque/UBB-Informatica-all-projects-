def rezolva(matrice, submatrici):
    rez = []

    for submatrice in submatrici:
        start = submatrice[0]
        stop = submatrice[1]

        suma = 0

        for i in range(start[0], stop[0] + 1):
            for j in range(start[1], stop[1] + 1):
                suma += matrice[i][j]
        rez.append(suma)

    return rez

def rezolva_ai(matrice, interogari):
    if not matrice or not matrice[0]:
        return []

    n = len(matrice)
    m = len(matrice[0])

    # 1. Construim matricea de sume parțiale (cu dimensiuni n+1 și m+1)
    # P[i][j] va conține suma elementelor de la (0,0) la (i-1, j-1) din matricea originală
    P = [[0] * (m + 1) for _ in range(n + 1)]

    for i in range(1, n + 1):
        for j in range(1, m + 1):
            P[i][j] = (matrice[i-1][j-1] + 
                       P[i-1][j] + 
                       P[i][j-1] - 
                       P[i-1][j-1])

    rezultate = []

    # 2. Procesăm fiecare interogare din listă
    for (r1, c1), (r2, c2) in interogari:
        # Trecem la indecși 1-based pentru a folosi matricea precalculată P
        r1, c1 = r1 + 1, c1 + 1
        r2, c2 = r2 + 1, c2 + 1

        # Aplicăm formula includerii-excluderii
        suma_submatrice = P[r2][c2] - P[r1-1][c2] - P[r2][c1-1] + P[r1-1][c1-1]
        rezultate.append(suma_submatrice)

    return rezultate

def main():
    matrice_test = [
    [0, 2, 5, 4, 1],
    [4, 8, 2, 3, 7],
    [6, 3, 4, 6, 2],
    [7, 3, 1, 8, 3],
    [1, 5, 7, 9, 4]
    ]

    assert rezolva(matrice_test, [((1, 1), (3, 3)), ((2, 2), (4, 4))]) == [38, 44]
    assert rezolva(matrice_test, [((0, 0), (0, 0))]) == [0]
    assert rezolva(matrice_test, [((0, 0), (4, 4))]) == [105]
    assert rezolva(matrice_test, [((2, 1), (3, 2))]) == [11]
    assert rezolva_ai(matrice_test, [((1, 1), (3, 3)), ((2, 2), (4, 4))]) == [38, 44]
    assert rezolva_ai(matrice_test, [((0, 0), (0, 0))]) == [0]
    assert rezolva_ai(matrice_test, [((0, 0), (4, 4))]) == [105]
    assert rezolva_ai(matrice_test, [((2, 1), (3, 2))]) == [11]
    print(rezolva(matrice_test, [((1, 1), (3, 3)), ((2, 2), (4, 4))]))
    print(rezolva_ai(matrice_test, [((1, 1), (3, 3)), ((2, 2), (4, 4))]))

main()