package org.example;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BackgroundWorker {

    @Autowired
    private ProductRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void commitBackgroundUpdate(Integer id, Double alternativePrice) {
        Product p = repository.findOne(id).orElseThrow();
        p.setPrice(alternativePrice);
        repository.update(p);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void commitBackgroundInsertion(String name, Double price, Integer quantity) {
        Product p = new Product(name, price, "Inserted by background worker", quantity);
        repository.save(p);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateWithForcedRollback(Integer id, Double temporaryPrice) {
        Product p = repository.findOne(id).orElseThrow();
        p.setPrice(temporaryPrice);
        repository.update(p);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        throw new RuntimeException("Forced rollback simulation!");
    }
}