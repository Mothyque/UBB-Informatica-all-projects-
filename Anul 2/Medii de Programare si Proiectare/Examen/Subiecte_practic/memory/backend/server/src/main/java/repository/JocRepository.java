package repository;

import domain.Joc;

import java.util.List;

public interface JocRepository
{
    void save(Joc joc);
    Joc getJoc(Long idJoc);
    List<Joc> getClasament();
}
