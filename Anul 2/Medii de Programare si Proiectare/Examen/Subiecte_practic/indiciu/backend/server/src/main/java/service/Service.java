package service;

import domain.Joc;
import domain.MeciActiv;

import java.util.List;

public interface Service
{
    MeciActiv pornesteJoc(String alias);
    MeciActiv efectueazaAlegere(Long idJoc, Integer linie, Integer coloana);
    List<Joc> getClasament();
    Joc getJoc(String alias);
    void modificaConfiguratie(Long idConfiguratie, Integer linie, Integer coloana, String text);
}
