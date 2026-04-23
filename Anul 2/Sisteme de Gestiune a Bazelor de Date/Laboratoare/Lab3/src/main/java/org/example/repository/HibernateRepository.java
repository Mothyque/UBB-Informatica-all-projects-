package org.example.repository;

import org.example.domain.Entity;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class HibernateRepository<ID, E extends Entity<ID>> implements Repository<ID, E>
{

    private final Class<E> entityClass;

    public HibernateRepository(Class<E> entityClass)
    {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<E> findOne(ID id)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            E entity = session.get(entityClass, id);
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Iterable<E> findAll()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.createQuery("from " + entityClass.getName()).list();
        }
    }

    @Override
    public Optional<E> save(E entity)
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return Optional.of(entity);
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<E> delete(ID id)
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            E entity = session.get(entityClass, id);
            if (entity != null)
            {
                session.remove(entity);
                transaction.commit();
                return Optional.of(entity);
            }
            return Optional.empty();
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<E> update(E entity)
    {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            E mergedEntity = session.merge(entity);
            transaction.commit();
            return Optional.of(mergedEntity);
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Iterable<E> findAll(int pageNumber, int pageSize)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.createQuery("from " + entityClass.getName(), entityClass)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
        }
    }

    @Override
    public int size()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Long count = session.createQuery("select count(*) from " + entityClass.getName() + " e",  Long.class).uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}