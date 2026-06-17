package service;

import domain.Joc;
import domain.MeciActiv;

import java.util.List;

public interface Service
{
    MeciActiv pornesteJoc(String alias);
    MeciActiv efectueazaMutare(Long idJoc, int pozitie1, int pozitie2);
    List<Joc> getClasament();
    Joc getJoc(Long idJoc);
    void modificaConfiguratie(Long idConfiguratie, List<String> elementeNoi);
}
