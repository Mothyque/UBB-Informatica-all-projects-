def rezolvare(text):
    ultimul = ""
    cuvinte = text.split(" ")
    for cuvant in cuvinte:
        if cuvant > ultimul: 
            ultimul = cuvant
    return ultimul

def rezolvare_ai(text):
    # Împărțim textul în cuvinte folosind spațiul ca separator
    cuvinte = text.split()
    
    # Verificăm dacă textul nu este gol pentru a evita erorile
    if not cuvinte:
        return None
    
    # Funcția max() returnează valoarea maximă (ultima alfabetic)
    ultimul_cuvant = max(cuvinte)
    
    return ultimul_cuvant

def main():
    assert rezolvare("Ana are mere rosii si galbene") == "si"
    assert rezolvare("Zimbru") == "Zimbru"
    assert rezolvare("acesta este un Test") == "un"
    assert rezolvare("a b c B A") == "c"
    assert rezolvare_ai("Ana are mere rosii si galbene") == "si"
    assert rezolvare_ai("Zimbru") == "Zimbru"
    assert rezolvare_ai("acesta este un Test") == "un"
    assert rezolvare_ai("a b c B A") == "c"
    print(rezolvare("Ana are sase mere mari"))
    print(rezolvare_ai("Ana are sase mere mari"))

main()