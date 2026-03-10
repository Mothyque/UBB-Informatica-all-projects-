from Domain.eveniment import Eveniment


class Validator:

    @staticmethod
    def valideaza_eveniment(eveniment: Eveniment):
        erori = []
        if len(eveniment.get_data().split(".")) != 3:
            erori.append("Data nu respecta formatul DD.MM.YYYY")
        if len(eveniment.get_ora().split(":")) != 2:
            erori.append("Ora nu respecta formatul HH:MM")
        if type(eveniment.get_descriere()) != str:
            erori.append("Descrierea nu este un sir de caractere")
        ziua, luna, anul = eveniment.get_data().split(".")
        if int(luna) < 1 or int(luna) > 12:
            erori.append("Luna nu este valida")
        if int(luna) in [1, 3, 5, 7, 8, 10, 12]:
            if int(ziua) < 1 or int(ziua) > 31:
                erori.append("Ziua nu este valida")
        elif int(luna) in [4, 6, 9, 11]:
            if int(ziua) < 1 or int(ziua) > 30:
                erori.append("Ziua nu este valida")
        elif int(luna) == 2:
            if int(ziua) < 1 or int(ziua) > 28:
                erori.append("Ziua nu este valida")
        ora, minutul = eveniment.get_ora().split(":")
        if int(ora) < 0 or int(ora) > 23:
            erori.append("Ora nu este valida")
        if int(minutul) < 0 or int(minutul) > 59:
            erori.append("Ora nu este valida")

        if len(erori) > 0:
            errors = '\n'.join(erori)
            raise ValueError(errors)



