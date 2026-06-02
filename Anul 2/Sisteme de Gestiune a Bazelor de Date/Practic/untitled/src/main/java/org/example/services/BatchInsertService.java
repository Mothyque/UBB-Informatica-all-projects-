package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchInsertService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void executeStandardMassInsertion(int totalCount) {
        System.out.println("--- [Starting Unoptimized Standard Mass Insertion of " + totalCount + " Products] ---");

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= totalCount; i++) {
            Product product = new Product(
                    "Standard Item " + i,
                    10.0 + i,
                    "Description for standard element " + i,
                    10
            );

            em.persist(product);

            em.flush();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("   >>> Standard Insertion Performance Duration: " + (endTime - startTime) + " ms");
    }

    @Transactional
    public void executeBatchMassInsertion(int totalCount) {
        System.out.println("--- [Starting Optimized Batch Mass Insertion of " + totalCount + " Products] ---");

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= totalCount; i++) {
            Product product = new Product(
                    "Batch Item " + i,
                    10.0 + i,
                    "Description for batch element " + i,
                    10
            );

            em.persist(product);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
                System.out.println("   >>> Flushed and shipped a batched payload of 50 records.");
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("   >>> Batch Insertion Performance Duration: " + (endTime - startTime) + " ms");
    }
}