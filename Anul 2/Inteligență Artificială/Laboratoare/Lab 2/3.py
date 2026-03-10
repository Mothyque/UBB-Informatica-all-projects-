import string
import re

with open('data/texts.txt', 'r', encoding = 'utf-8') as f:
    text = f.read()

propozitii = text.split('.')
print(f'Numarul total de propozitii: {len(propozitii)}')

cuvinte_fara_punctuatie = [cuvant.strip('„”«»"\',!?.:') for cuvant in text.split() if cuvant.strip('„”«»"\',!?.:')]

print(f'Numarul total de cuvinte: {len(cuvinte_fara_punctuatie)}')

cuvinte_fara_litere_mari = [cuvant.lower() for cuvant in cuvinte_fara_punctuatie]
cuvinte_unice = set(cuvinte_fara_litere_mari)
print(f'Numarul total de cuvinte unice: {len(cuvinte_unice)}')

cuvinte_curatate_fara_repetitii = set(re.sub(r'(.)\1{2,}', r'\1', cuvant) for cuvant in cuvinte_unice)

lungime_min = min(len(cuvant) for cuvant in cuvinte_curatate_fara_repetitii)
lungime_max = max(len(cuvant) for cuvant in cuvinte_curatate_fara_repetitii)

cuvinte_min = [cuvant for cuvant in cuvinte_curatate_fara_repetitii if len(cuvant) == lungime_min]
cuvinte_max = [cuvant for cuvant in cuvinte_curatate_fara_repetitii if len(cuvant) == lungime_max]

print(f'Cele mai scurte cuvinte (lungime {lungime_min}): {cuvinte_min}')
print(f'Cele mai lungi cuvinte (lungime {lungime_max}): {cuvinte_max}')

diacritice = {
    'ă': 'a',
    'Ă': 'A',
    'ș': 's',
    'Ș': 'S',
    'ț': 't',
    'Ț': 'T',
    'â': 'a',
    'Â': 'A',
    'î': 'i',
    'Î': 'I'
}

tabel_diacritice = text.maketrans(diacritice)
text_fara_diacritice = text.translate(tabel_diacritice)
print("\nTextul fara diacritice:")
print(text_fara_diacritice)

cuvant_lung_ales = cuvinte_max[0]

dictionar_sinonim = {
    "laboratoarele" : ["atelierele", "centrele de cercetare", "institutele de cercetare"],
}

sinonime_cuvant = dictionar_sinonim.get(cuvant_lung_ales, [])
print(f"\nSinonime pentru cuvantul '{cuvant_lung_ales}': {sinonime_cuvant}")

propozitie_test = propozitii[0]
cuvinte_propozitie = re.findall(r'\b\w+\b', propozitie_test.lower())
total_cuvinte_propozitie = len(cuvinte_propozitie)

cuvinte_dict = {}
for cuvant in cuvinte_propozitie:
    if cuvant in cuvinte_dict:
        cuvinte_dict[cuvant] += 1
    else:
        cuvinte_dict[cuvant] = 1

frecvente_normalizate = {}

for cuvant, frecventa in cuvinte_dict.items():
    frecventa_normalizata = frecventa / total_cuvinte_propozitie
    frecvente_normalizate[cuvant] = frecventa_normalizata
    print(f"Cuvant: '{cuvant}', Frecventa: {frecventa}, Frecventa normalizata: {frecventa_normalizata:.4f}")