import math
def rezolvare(X, Y):
    return math.sqrt(((Y[0] - X[0]) * (Y[0] - X[0])) + ((Y[1] - X[1]) * (Y[1] - X[1])))

def rezolvare_ai(p1, p2):
    # p1 și p2 sunt tupluri sau liste de forma (x, y)
    x1, y1 = p1
    x2, y2 = p2
    
    # Aplicăm formula
    distanta = math.sqrt((x2 - x1)**2 + (y2 - y1)**2)
    return distanta

def main():
    assert rezolvare((1, 5), (4, 1)) == 5.0
    assert rezolvare((0, 0), (0, 0)) == 0.0
    assert rezolvare((-2, 1), (1, 5)) == 5.0
    assert rezolvare((3, 4), (3, 4)) == 0.0
    assert rezolvare_ai((1, 5), (4, 1)) == 5.0
    assert rezolvare_ai((0, 0), (0, 0)) == 0.0
    assert rezolvare_ai((-2, 1), (1, 5)) == 5.0
    assert rezolvare_ai((3, 4), (3, 4)) == 0.0
    print(rezolvare((1,5), (4,1)))
    print(rezolvare_ai((1,5), (4,1)))

main()