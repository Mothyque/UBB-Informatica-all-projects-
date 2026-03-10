def rezolvare(matrice):
    m = len(matrice)
    n = len(matrice[0])
    poz = n
    rez = -1
    for i in range(m):
        for j in range(n):
            if matrice[i][j] == 1:
                if poz > j:
                    poz = j
                    rez = i
                break
    return rez + 1

def rezolvare_ai(matrice):
    if not matrice or not matrice[0]:
        return -1  # Matrice goală

    n = len(matrice)      # Numărul de linii
    m = len(matrice[0])   # Numărul de coloane
    
    index_linie_max = -1
    
    # Începem din colțul dreapta-sus
    linie = 0
    coloana = m - 1
    
    while linie < n and coloana >= 0:
        if matrice[linie][coloana] == 1:
            # Am găsit un 1. Actualizăm linia cu maximul găsit.
            index_linie_max = linie
            # Ne mutăm la stânga pentru a căuta și mai multe 1-uri
            coloana -= 1
        else:
            # Am dat de un 0, mergem pe linia următoare pentru a încerca 
            # să batem recordul actual (nu ne întoarcem la dreapta)
            linie += 1
            
    return index_linie_max + 1

def main():
    matrice_test = [
    [0,0,0,1,1],
    [0,1,1,1,1],
    [0,0,1,1,1]
    ]
    assert rezolvare(matrice_test) == 2
    assert rezolvare([[1, 1, 1], [0, 0, 1], [0, 1, 1]]) == 1
    assert rezolvare([[0, 0], [0, 0]]) == 0
    assert rezolvare([[0, 0, 0], [0, 0, 0], [0, 0, 1]]) == 3
    assert rezolvare_ai(matrice_test) == 2
    assert rezolvare_ai([[1, 1, 1], [0, 0, 1], [0, 1, 1]]) == 1
    assert rezolvare_ai([[0, 0], [0, 0]]) == 0
    assert rezolvare_ai([[0, 0, 0], [0, 0, 0], [0, 0, 1]]) == 3
    print(rezolvare(matrice_test))
    print(rezolvare_ai(matrice_test))

main()