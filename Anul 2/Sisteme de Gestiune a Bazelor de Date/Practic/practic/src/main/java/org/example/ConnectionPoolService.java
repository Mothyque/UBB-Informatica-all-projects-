package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConnectionPoolService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void holdConnectionMidFlight(String threadName, Integer productId) {
        System.out.println("   >>> [" + threadName + "] acquired a database connection. Holding it...");

        productRepository.findOne(productId);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("   >>> [" + threadName + "] transaction complete. Releasing connection back to pool.");
    }
}