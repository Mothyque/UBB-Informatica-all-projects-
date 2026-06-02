package org.example.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.Company;
import org.example.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NPlusOneService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public void simulateNPlusOneProblem() {
        List<Company> companies = em.createQuery("SELECT c FROM Company c", Company.class).getResultList();
        System.out.println("   Fetched " + companies.size() + " companies from DB. Now reading lazily loaded products...");

        for (Company company : companies) {
            int productCount = company.getProducts().size();
            System.out.println("   Company '" + company.getName() + "' owns " + productCount + " products.");
        }
    }

    @Transactional(readOnly = true)
    public void preventNPlusOneProblem() {
        List<Company> companies = em.createQuery(
                "SELECT DISTINCT c FROM Company c JOIN FETCH c.products", Company.class
        ).getResultList();

        System.out.println("   Fetched " + companies.size() + " companies from DB upfront. Iterating over collection...");
        for (Company company : companies) {
            System.out.println("   Company '" + company.getName() + "' owns " + company.getProducts().size() + " products.");
        }
    }

    @Transactional
    public void setupTestData() {
        Company c1 = new Company("TechCorp");
        c1.addProduct(new Product("Laptop", 1200.0, "Notebook", 10));
        c1.addProduct(new Product("Mouse", 25.0, "Wireless mouse", 50));

        Company c2 = new Company("FoodInc");
        c2.addProduct(new Product("Apple", 1.5, "Organic apple", 200));

        Company c3 = new Company("AutoGroup");
        c3.addProduct(new Product("Tire", 90.0, "All-season tire", 40));

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.flush();
        em.clear();
    }
}