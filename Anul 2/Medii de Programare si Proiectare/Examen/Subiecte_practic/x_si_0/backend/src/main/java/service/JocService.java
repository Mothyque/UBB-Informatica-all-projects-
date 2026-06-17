package service;

import domain.Joc;
import domain.MeciActiv;

import java.util.List;

public interface JocService
{
    MeciActiv pornesteJoc(String alias);
    MeciActiv efectueazaMutare(Long idJoc, int linie, int coloana);
    List<Joc> getClasament();
    List<Joc> getJocuriCastigate(String alias);
}
