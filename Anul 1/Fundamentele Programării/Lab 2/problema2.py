import datetime
from datetime import datetime

def numar_zile(data_nasterii):
    data_nasterii_cast = datetime.strptime(data_nasterii, "%d/%m/%Y").date()
    nr_zile = datetime.today().date() - data_nasterii_cast

    return nr_zile.days

ziua_nastere = input("Data nasterii: ")
print(numar_zile(ziua_nastere))