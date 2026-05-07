package service;

import domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatementReuseService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public void runDemo() {
        int iterations = 1000;

        long startA = System.nanoTime();
        for (int i = 1; i <= iterations; i++)
        {
            try
            {
                Query query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id");
                query.setParameter("id", (long) i);
                query.getSingleResult();
            }
            catch (Exception ignored)
            {}
        }
        long endA = System.nanoTime();
        double timeA = (endA - startA) / 1_000_000.0;

        long startB = System.nanoTime();
        Query reusableQuery = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id");
        for (int i = 1; i <= iterations; i++)
        {
            try
            {
                reusableQuery.setParameter("id", (long) i);
                reusableQuery.getSingleResult();
            }
            catch (Exception ignored)
            {}
        }
        long endB = System.nanoTime();
        double timeB = (endB - startB) / 1_000_000.0;

        System.out.println("Test A (Without Reuse): " + String.format("%.4f", timeA) + " ms");
        System.out.println("Test B (With Reuse):    " + String.format("%.4f", timeB) + " ms");
        System.out.println("Performance Gain:       " + String.format("%.2f", (timeA / timeB)) + "x faster");
    }
}