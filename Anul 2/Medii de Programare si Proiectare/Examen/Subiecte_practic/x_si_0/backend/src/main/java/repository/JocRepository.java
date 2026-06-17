package repository;

import domain.Joc;

import java.util.List;

public interface JocRepository
{
    void save(Joc joc);
    List<Joc> getAllGamesSorted();
    List<Joc> getWonGamesByPlayerAlias(String alias);
}
