package org.example.repository.orm;

import jakarta.persistence.*;
import org.example.domain.Match;
import org.example.repository.MatchRepository;

import java.util.Optional;
import java.util.function.Function;

public class MatchORMRepository implements MatchRepository
{
    private final EntityManagerFactory emf;

    public MatchORMRepository()
    {
        this.emf = Persistence.createEntityManagerFactory("org.example.sqlite");
    }

    @Override
    public Optional<Match> findOne(Integer integer)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return Optional.ofNullable(em.find(Match.class, integer));
        }
    }

    @Override
    public Iterable<Match> findAll() {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("from Match", Match.class).getResultList();
        }
    }

    @Override
    public Optional<Match> save(Match entity)
    {
        return executeInTransaction(em -> {
            em.persist(entity);
            return Optional.of(entity);
        });
    }

    @Override
    public Optional<Match> delete(Integer integer)
    {
        return executeInTransaction(em -> {
            Match match = em.find(Match.class, integer);
            if (match != null) {
                em.remove(match);
                return Optional.of(match);
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<Match> update(Match entity)
    {
        return executeInTransaction(em -> {
            Match updatedMatch = em.merge(entity);
            return Optional.of(updatedMatch);
        });
    }

    @Override
    public int size()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("select count(*) from Match", Integer.class).getSingleResult();
        }
    }

    private <T> T executeInTransaction(Function<EntityManager, T> action)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();
            T result = action.apply(em);
            tx.commit();
            return result;
        }
        catch (Exception e)
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            throw e;
        }
        finally
        {
        em.close();
        }
    }
}