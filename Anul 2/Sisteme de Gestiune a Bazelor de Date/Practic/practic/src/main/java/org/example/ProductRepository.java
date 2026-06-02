package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements IRepository<Integer, Product> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Product> findOne(Integer id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public Iterable<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Optional<Product> save(Product entity) {
        em.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Product> update(Product entity) {
        Product merged = em.merge(entity);
        return Optional.of(merged);
    }

    @Override
    public Optional<Product> delete(Integer id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public int size() {
        return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult()
                .intValue();
    }
}