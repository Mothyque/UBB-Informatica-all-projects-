package repository;

import domain.Configuratie;

import java.util.Optional;

public interface ConfiguratieRepository
{
    void save(Configuratie configuratie);
    boolean exists(String text);
    void update(Configuratie configuratie);
    Optional<Configuratie> find(Long id);
}
