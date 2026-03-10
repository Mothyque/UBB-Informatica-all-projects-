def rezolvare(v1, v2):
    suma = 0
    if len(v1) > len(v2):
        v1, v2 = v2, v1
        
    for i in range(len(v2)):
        if(v1[i] != 0 and v2[i] != 0):
            suma += v1[i] * v2[i]
    return suma

def rezolvare_ai(vec1, vec2):
    # Funcție internă pentru a converti o listă într-un dicționar rar
    def transforma_in_rar(vector):
        return {index: valoare for index, valoare in enumerate(vector) if valoare != 0}

    # Transformăm vectorii clasici în reprezentări rare
    dict1 = transforma_in_rar(vec1)
    dict2 = transforma_in_rar(vec2)

    # Optimizare: iterăm întotdeauna prin dicționarul mai mic
    if len(dict1) > len(dict2):
        dict1, dict2 = dict2, dict1

    produs_scalar = 0
    
    # Calculăm produsul scalar doar pentru indecșii comuni
    for index, valoare in dict1.items():
        if index in dict2:
            produs_scalar += valoare * dict2[index]

    return produs_scalar

def main():
    assert rezolvare([1, 0, 2, 0, 3], [1, 2, 0, 3, 1]) == 4
    assert rezolvare([0, 0, 0], [0, 0, 0]) == 0
    assert rezolvare([1, 2, 3], [4, 5, 6]) == 32
    assert rezolvare([1, 0], [0, 1]) == 0
    assert rezolvare_ai([1, 0, 2, 0, 3], [1, 2, 0, 3, 1]) == 4
    assert rezolvare_ai([0, 0, 0], [0, 0, 0]) == 0
    assert rezolvare_ai([1, 2, 3], [4, 5, 6]) == 32
    assert rezolvare_ai([1, 0], [0, 1]) == 0
    print(rezolvare([1,0,3,4,5], [0,1,2,0,4]))
    print(rezolvare_ai([1,0,3,4,5], [0,1,2,0,4]))

main()