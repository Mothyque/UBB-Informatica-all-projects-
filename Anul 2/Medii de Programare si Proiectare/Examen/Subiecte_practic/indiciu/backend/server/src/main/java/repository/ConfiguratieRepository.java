package repository;

import domain.Configuratie;

import java.util.List;
import java.util.Optional;

public interface ConfiguratieRepository
{
    List<Configuratie> findAll();
    Optional<Configuratie> save(Configuratie configuratie);
}
