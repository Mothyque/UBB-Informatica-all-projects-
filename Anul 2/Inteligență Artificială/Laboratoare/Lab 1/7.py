def rezolvare(sir, poz):
    sir.sort(reverse = True)
    return sir[poz - 1]

def rezolvare_ai(sir, k):
    # Sortăm șirul descrescător
    sir_sortat = sorted(sir, reverse=True)
    # Returnăm elementul de pe poziția k-1
    return sir_sortat[k - 1]


def main():
    assert rezolvare([7, 4, 6, 3, 9, 1], 2) == 7
    assert rezolvare([10, 20, 30, 40, 50], 1) == 50
    assert rezolvare([1, 2, 3, 4, 5], 5) == 1
    assert rezolvare([5, 5, 5, 5, 5], 3) == 5
    assert rezolvare_ai([7, 4, 6, 3, 9, 1], 2) == 7
    assert rezolvare_ai([10, 20, 30, 40, 50], 1) == 50
    assert rezolvare_ai([1, 2, 3, 4, 5], 5) == 1
    assert rezolvare_ai([5, 5, 5, 5, 5], 3) == 5
    print(rezolvare([7,4,6,3,9,1], 2))
    print(rezolvare_ai([7,4,6,3,9,1], 2))

main()