def rezolvare(sir):
    rez = []
    for nr in sir:
        if nr in rez:
            return nr
        else:
            rez.append(nr)

def rezolvare_ai(sir):
    n = len(sir)
    # Suma numerelor de la 1 la n-1 folosind formula lui Gauss
    suma_asteptata = (n - 1) * n // 2 
    suma_reala = sum(sir)
    
    # Diferența dintre suma reală și cea așteptată este duplicatul
    return suma_reala - suma_asteptata


def main():
    assert rezolvare([1, 2, 3, 4, 2]) == 2
    assert rezolvare([1, 1]) == 1
    assert rezolvare([1, 3, 4, 2, 5, 3]) == 3
    assert rezolvare([4, 3, 2, 1, 4, 5]) == 4
    assert rezolvare_ai([1, 2, 3, 4, 2]) == 2
    assert rezolvare_ai([1, 1]) == 1
    assert rezolvare_ai([1, 3, 4, 2, 5, 3]) == 3
    assert rezolvare_ai([4, 3, 2, 1, 4, 5]) == 4
    print(rezolvare([1,2,3,4,2]))
    print(rezolvare_ai([1,2,3,4,2]))

main()