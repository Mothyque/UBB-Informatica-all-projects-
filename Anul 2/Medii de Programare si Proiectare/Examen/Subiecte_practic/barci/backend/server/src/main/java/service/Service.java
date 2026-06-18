package service;

import domain.Joc;
import domain.MeciActiv;

import java.util.List;

public interface Service
{
    MeciActiv pornesteJoc(String alias);
    MeciActiv efectueazaAlegere(Long idJoc, Integer linie, Integer coloana);
    List<Joc> getClasament();
    List<Joc> getJocuriCuHit(String alias);
    void adaugaBarca(String pozitii);

}

