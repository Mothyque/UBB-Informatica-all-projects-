package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabasePopulator
{
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public int createTestProduct(String name, Double initialPrice, Integer quantity)
    {
        Product product = new Product(name, initialPrice, "Sample product for testing", quantity);
        productRepository.save(product);
        return product.getId();
    }
}
