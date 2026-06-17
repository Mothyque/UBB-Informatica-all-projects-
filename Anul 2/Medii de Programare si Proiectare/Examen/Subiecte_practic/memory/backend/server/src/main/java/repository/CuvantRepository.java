package repository;

import domain.Cuvant;

import java.util.List;

public interface CuvantRepository
{
    void save(Cuvant cuvant);
    List<Cuvant> findAll();
}
