package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.example.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeadlockService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void lockFirstThenSecond(Integer firstId, Integer secondId, String threadName) {
        System.out.println("   [" + threadName + "] Starting transaction...");

        Product p1 = em.find(Product.class, firstId, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("   [" + threadName + "] ACQUIRED lock on Product ID: " + firstId);

        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        System.out.println("   [" + threadName + "] TRYING to acquire lock on Product ID: " + secondId + "...");
        Product p2 = em.find(Product.class, secondId, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("   [" + threadName + "] SUCCESS! Acquired both locks.");
    }
}