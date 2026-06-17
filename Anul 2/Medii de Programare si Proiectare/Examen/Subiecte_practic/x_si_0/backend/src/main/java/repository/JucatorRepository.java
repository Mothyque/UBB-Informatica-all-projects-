package repository;

import domain.Jucator;

import java.util.Optional;

public interface JucatorRepository
{
    void save(Jucator jucator);
    Optional<Jucator> findByAlias(String alias);
}
