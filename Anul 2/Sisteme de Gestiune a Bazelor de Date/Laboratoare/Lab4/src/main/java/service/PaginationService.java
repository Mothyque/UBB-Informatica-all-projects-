package service;

import domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaginationService
{
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public void runPaginationDemo()
    {
        benchmarkOffset("First Page", 0, 10);
        benchmarkOffset("Middle Page", 50000, 10);
        benchmarkOffset("Last Page", 99999, 10);
        benchmarkKeyset("First Page", 0, 10);
        benchmarkKeyset("Middle Page", 500000L, 10);
        benchmarkKeyset("Last Page",999990L, 10);
    }

    private void  benchmarkOffset(String label, int pageNumber, int iterations)
    {
        int pageSize = 10;
        int offset = pageNumber * pageSize;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++)
        {
            long start = System.nanoTime();
            em.createQuery("SELECT e FROM Employee e ORDER BY e.id",  Employee.class)
                    .setFirstResult(offset + (i * pageSize))
                    .setMaxResults(pageSize)
                    .getResultList();
            totalTime += (System.nanoTime() - start);
        }
        System.out.println("Offset Paging " + label + " Avg: " + (totalTime / (double) iterations / 1_000_000.0)  + " ms");
    }

    private void benchmarkKeyset(String label, long lastSeenId, int iterations)
    {
        int pageSize = 10;

        long totalTime = 0;
        for (int i = 0; i < iterations; i++)
        {
            long start = System.nanoTime();
            em.createQuery("SELECT e FROM Employee e WHERE e.id > :lastId ORDER BY e.id", Employee.class)
                    .setParameter("lastId", lastSeenId + (i * (long)pageSize))
                    .setMaxResults(pageSize)
                    .getResultList();
            totalTime += (System.nanoTime() - start);
        }
        long end = System.currentTimeMillis();

        System.out.println("Keyset Paging" + label + " Avg: " + (totalTime / (double) iterations / 1_000_000.0) + " ms");
    }
}