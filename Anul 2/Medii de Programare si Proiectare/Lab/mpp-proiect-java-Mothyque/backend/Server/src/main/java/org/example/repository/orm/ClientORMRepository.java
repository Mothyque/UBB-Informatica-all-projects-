package org.example.repository.orm;

import jakarta.persistence.*;
import org.example.domain.Client;
import org.example.repository.ClientRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ClientORMRepository implements ClientRepository
{
    private final EntityManagerFactory emf;

    public ClientORMRepository()
    {
        this.emf = Persistence.createEntityManagerFactory("org.example.sqlite");
    }

    @Override
    public List<Client> findClientsByNameAndAddress(String name, String address)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT c FROM Client c WHERE c.name = :name AND c.address = :address", Client.class)
                .setParameter("name", name)
                .setParameter("address", address)
                .getResultList();
        }
    }

    @Override
    public Client findClientByNameAndAddress(String name, String address)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT c FROM Client c WHERE c.name = :name AND c.address = :address", Client.class)
                    .setParameter("name", name)
                    .setParameter("address", address)
                    .getSingleResult();
        }
        catch (NoResultException ex)
        {
            return null;
        }
    }

    @Override
    public Optional<Client> findOne(Integer integer) {
        try (EntityManager em = emf.createEntityManager())
        {
            return Optional.ofNullable(em.find(Client.class, integer));
        }
    }

    @Override
    public Iterable<Client> findAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("from Client", Client.class).getResultList();
        }
    }

    @Override
    public Optional<Client> save(Client entity)
    {
        return executeInTransaction(em -> {
            em.persist(entity);
            return Optional.of(entity);
        });
    }

    @Override
    public Optional<Client> delete(Integer integer)
    {
        return executeInTransaction(em -> {
            Client client = em.find(Client.class, integer);
            if (client != null)
            {
                em.remove(client);
                return Optional.of(client);
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<Client> update(Client entity)
    {
        return executeInTransaction(em -> {
            em.merge(entity);
            return Optional.of(entity);
        });
    }

    @Override
    public int size()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(c) FROM Client c", Long.class).getSingleResult().intValue();
        }
    }

    private <T> T executeInTransaction(Function<EntityManager, T> function)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();
            T result = function.apply(em);
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