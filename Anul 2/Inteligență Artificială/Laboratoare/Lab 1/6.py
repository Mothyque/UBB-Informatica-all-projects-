def rezolvare(sir):
    n = len(sir)
    if(n == 1):
        return sir[0]
    d = {}
    for nr in sir:
        if nr in d:
            if d[nr] >= n // 2:
                return nr
            else:
                d[nr] += 1
        else:
            d[nr] = 1

def rezolvare_ai(nums):
    candidat = None
    contor = 0

    # Pasul 1: Identificarea candidatului
    for num in nums:
        if contor == 0:
            candidat = num
        
        if num == candidat:
            contor += 1
        else:
            contor -= 1
            
    # Pasul 2: Verificarea (necesară doar dacă NU este garantat că există un element majoritar)
    # Pentru exemplul dat, știm că există, deci acest pas validează rezultatul.
    if nums.count(candidat) > len(nums) // 2:
        return candidat
    else:
        return "Nu există un element majoritar"


def main():
    assert rezolvare([2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]) == 2
    assert rezolvare([5]) == 5
    assert rezolvare([1, 1, 2, 2, 2]) == 2
    assert rezolvare([4, 3, 4, 1, 4]) == 4
    assert rezolvare_ai([2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]) == 2
    assert rezolvare_ai([5]) == 5
    assert rezolvare_ai([1, 1, 2, 2, 2]) == 2
    assert rezolvare_ai([4, 3, 4, 1, 4]) == 4
    print(rezolvare([2,8,7,2,2,5,2,3,1,2,2]))
    print(rezolvare_ai([2,8,7,2,2,5,2,3,1,2,2]))

main()