package org.example;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public List<Product> getProducts() { return products; }
    public void addProduct(Product product) {
        this.products.add(product);
        product.setCompany(this);
    }
}