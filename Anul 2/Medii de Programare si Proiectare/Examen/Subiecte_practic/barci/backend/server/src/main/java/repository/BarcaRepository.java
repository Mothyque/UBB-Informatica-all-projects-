package repository;

import domain.Barca;

import java.util.List;
import java.util.Optional;

public interface BarcaRepository
{
    List<Barca> findAll();
    Optional<Barca> save(Barca barca);
}
