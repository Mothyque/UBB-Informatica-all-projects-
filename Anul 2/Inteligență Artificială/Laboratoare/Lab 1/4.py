def rezolvare(text):
    rez = []
    frecv = {}
    cuvinte = text.split(" ")
    for cuvant in cuvinte:
        if cuvant in frecv:
            frecv[cuvant] += 1
        else:
            frecv[cuvant] = 1

    for cuvant, cnt in frecv.items():
        if cnt == 1:
            rez.append(cuvant)
    return rez

from collections import Counter

def rezolvare_ai(text):
    # 1. Împărțim textul în cuvinte (pe baza spațiilor)
    cuvinte = text.split()
    
    # 2. Numărăm frecvența fiecărui cuvânt
    frecvente = Counter(cuvinte)
    
    # 3. Filtrăm doar cuvintele care apar o singură dată
    cuvinte_unice = [cuvant for cuvant, numar in frecvente.items() if numar == 1]
    
    return cuvinte_unice


def main():
    assert rezolvare("ana are ana are mere rosii ana") == ['mere', 'rosii']
    assert rezolvare("un test este un test simplu") == ['este', 'simplu']
    assert rezolvare("toate cuvintele sunt unice") == ['toate', 'cuvintele', 'sunt', 'unice']
    assert rezolvare("a a b b c c") == []
    assert rezolvare_ai("ana are ana are mere rosii ana") == ['mere', 'rosii']
    assert rezolvare_ai("un test este un test simplu") == ['este', 'simplu']
    assert rezolvare_ai("toate cuvintele sunt unice") == ['toate', 'cuvintele', 'sunt', 'unice']
    assert rezolvare_ai("a a b b c c") == []

    print(rezolvare("ana are ana are mere rosii ana"))
    print(rezolvare_ai("ana are ana are mere rosii ana"))

main()