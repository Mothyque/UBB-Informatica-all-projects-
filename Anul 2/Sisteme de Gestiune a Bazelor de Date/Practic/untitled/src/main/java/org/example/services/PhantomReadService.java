package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.Product;
import org.example.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PhantomReadService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void simulatePhantomRead(Runnable concurrentInsertAction) {
        List<Product> list1 = productRepository.findByQuantityGreaterThan(15);
        System.out.println("   Read 1: Found " + list1.size() + " products with quantity > 15");

        concurrentInsertAction.run();

        em.clear();

        List<Product> list2 = productRepository.findByQuantityGreaterThan(15);
        System.out.println("   Read 2: Found " + list2.size() + " products with quantity > 15");

        if (list2.size() > list1.size()) {
            System.out.println("   >> ANOMALY DETECTED: A phantom read occurred! A new record appeared mid-flight.");
        } else {
            System.out.println("   >> No anomaly detected.");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void preventPhantomRead(Runnable concurrentInsertAction) {

        List<Product> list1 = productRepository.findByQuantityGreaterThan(15);
        System.out.println("   Read 1: Found " + list1.size() + " products with quantity > 15");

        try {
            concurrentInsertAction.run();
        } catch (Exception e) {
            System.out.println("   >> [System Alert]: Background transaction was blocked or aborted by SERIALIZABLE range locks!");
        }

        em.clear();

        List<Product> list2 = productRepository.findByQuantityGreaterThan(15);
        System.out.println("   Read 2: Found " + list2.size() + " products with quantity > 15");

        if (list1.size() == list2.size()) {
            System.out.println("   >> SUCCESS: Phantom read prevented. Dataset count is kept at " + list2.size());
        } else {
            System.out.println("   >> ANOMALY DETECTED: Phantom read occurred! A new record appeared mid-flight.");
        }
    }
}