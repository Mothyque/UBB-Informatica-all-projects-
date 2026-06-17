package repository;

import domain.Joc;

import java.util.List;
import java.util.Optional;

public interface JocRepository
{
    Optional<Joc> save(Joc joc);
    List<Joc> getClasament();
    List<Joc> getJocuri(String alias);
}
