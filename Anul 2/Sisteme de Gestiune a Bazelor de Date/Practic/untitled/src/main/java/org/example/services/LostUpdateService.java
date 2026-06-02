package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.example.Product;
import org.example.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LostUpdateService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void simulateLostUpdate(Integer productId, Runnable concurrentModifierAction) {

        Product p1 = productRepository.findOne(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("   User 1 reads price: " + p1.getPrice());

        concurrentModifierAction.run();

        System.out.println("   User 1 modifies price by adding +10.0 to their local stale state...");
        p1.setPrice(p1.getPrice() + 10.0);
        productRepository.update(p1);

        em.flush();
        em.clear();

        Product finalProduct = productRepository.findOne(productId).orElseThrow();
        System.out.println("   Final Price in DB: " + finalProduct.getPrice());
        System.out.println("   >> ANOMALY DETECTED: Concurrent updates were lost! Final value should have been higher.");
    }

    @Transactional
    public void preventLostUpdate(Integer productId, Runnable concurrentModifierAction) {

        Product p1 = em.find(Product.class, productId, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("   User 1 reads locked price: " + p1.getPrice());

        try {
            concurrentModifierAction.run();
        } catch (Exception e) {
            System.out.println("   >> [System Alert]: Concurrent modifier was blocked/aborted due to active locks!");
        }

        p1.setPrice(p1.getPrice() + 10.0);
        productRepository.update(p1);

        em.flush();
        em.clear();

        Product finalProduct = productRepository.findOne(productId).orElseThrow();
        System.out.println("   Final Price in DB: " + finalProduct.getPrice());
        System.out.println("   >> SUCCESS: Lost update prevented via transaction exclusion.");
    }
}