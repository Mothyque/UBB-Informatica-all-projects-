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
public class DirtyReadService {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void simulateDirtyRead(Integer productId, Runnable concurrentUpdateAction) {
        System.out.println("--- [READ_UNCOMMITTED Dirty Read Simulation] ---");

        Product p1 = productRepository.findOne(productId).orElseThrow();
        System.out.println("   Initial Price in DB: " + p1.getPrice());

        Thread backgroundThread = new Thread(concurrentUpdateAction);
        backgroundThread.start();

        try { Thread.sleep(500); } catch (InterruptedException e) { }

        em.clear();

        Product p2 = productRepository.findOne(productId).orElseThrow();
        System.out.println("   Read Price while background is running: " + p2.getPrice());

        if (!p1.getPrice().equals(p2.getPrice())) {
            System.out.println("   >> ANOMALY DETECTED: Dirty read occurred! Uncommitted change was visible.");
        } else {
            System.out.println("   >> No anomaly detected (H2 engine protects uncommitted memory blocks).");
        }

        try { backgroundThread.join(); } catch (InterruptedException e) { }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void preventDirtyRead(Integer productId, Runnable concurrentUpdateAction) {
        System.out.println("--- [READ_COMMITTED Dirty Read Prevention] ---");

        Product p1 = productRepository.findOne(productId).orElseThrow();
        System.out.println("   Initial Price in DB: " + p1.getPrice());

        Thread backgroundThread = new Thread(concurrentUpdateAction);
        backgroundThread.start();

        try { Thread.sleep(500); } catch (InterruptedException e) { }

        em.clear();

        Product p2 = productRepository.findOne(productId).orElseThrow();
        System.out.println("   Read Price while background is running: " + p2.getPrice());

        if (!p1.getPrice().equals(p2.getPrice())) {
            System.out.println("   >> ANOMALY DETECTED: Uncommitted data leaked!");
        } else {
            System.out.println("   >> SUCCESS: Dirty read prevented. Uncommitted change was hidden.");
        }

        try { backgroundThread.join(); } catch (InterruptedException e) { }
    }
}