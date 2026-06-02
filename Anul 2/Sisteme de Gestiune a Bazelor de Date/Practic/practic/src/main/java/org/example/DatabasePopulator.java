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
    public int createTestProduct(String name, String data_expirare, Integer pret, String producator)
    {
        Product product = new Product(name, data_expirare, pret, producator);
        productRepository.save(product);
        return product.getId();
    }
}
