package repository;

import domain.Jucator;

import java.util.Optional;

public interface JucatorRepository
{
    Optional<Jucator> find(String alias);
    void save(Jucator jucator);
}
