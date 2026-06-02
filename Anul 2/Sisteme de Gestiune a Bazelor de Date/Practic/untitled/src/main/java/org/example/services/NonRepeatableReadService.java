package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.Product;
import org.example.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NonRepeatableReadService
{
    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void simulateNonRepeatableRead(Integer productId, Runnable concurrentUpdateAction) {
        Product p1 = productRepository.findOne(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("   Read 1 Price: " + p1.getPrice());

        concurrentUpdateAction.run();

        em.clear();

        Product p2 = productRepository.findOne(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("   Read 2 Price: " + p2.getPrice());

        if (!p1.getPrice().equals(p2.getPrice())) {
            System.out.println("   >> ANOMALY DETECTED: Price changed");
        } else {
            System.out.println("   >> No anomaly.");
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void preventNonRepeatableRead(Integer productId, Runnable concurrentUpdateAction)
    {
        Product p1 = productRepository.findOne(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("   Read 1 Price: " + p1.getPrice());

        concurrentUpdateAction.run();

        em.clear();

        Product p2 = productRepository.findOne(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("   Read 2 Price: " + p2.getPrice());

        if (!p1.getPrice().equals(p2.getPrice())) {
            System.out.println("   >> ANOMALY DETECTED: Price changed.");
        } else {
            System.out.println("   >> SUCCESS: Non-repeatable read prevented. Price is kept at " + p1.getPrice());
        }
    }
}