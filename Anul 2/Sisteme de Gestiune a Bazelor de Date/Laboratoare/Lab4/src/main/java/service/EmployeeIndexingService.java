package service;

import domain.Department;
import domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class EmployeeIndexingService
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void seedLargeDataset()
    {
        List<Department> depts = em.createQuery("select d from Department d", Department.class).getResultList();
        if (depts.isEmpty())
        {
            return;
        }
        Random rand = new Random();
        for (int i = 0; i < 1000000; i++)
        {
            Employee e = new Employee();
            e.setName("Employee " + i);
            e.setEmail("employee" + i + "@example.com");
            e.setSalary(50000 + (rand.nextInt(30000)));
            e.setDepartment(depts.get(rand.nextInt(depts.size())));
            em.persist(e);

            if (i % 10000 == 0)
            {
                em.flush();
                em.clear();
            }
        }
    }

    public void runBenchmarks()
    {
        benchmark("Search by Email", "SELECT e FROM Employee e WHERE e.email = 'employee9999@example.com'");
        benchmark("Search by Department", "SELECT e FROM Employee e WHERE e.department.id = 5");
        benchmark("Search by Salary", "SELECT e FROM Employee e WHERE e.salary BETWEEN 50000 and 80000");
        benchmark("Multi-column", "SELECT e FROM Employee e WHERE e.department.id = 5 AND e.salary > 60000");
    }

    private void benchmark(String label, String query)
    {
        long totalTime = 0;
        int iterations = 100;

        for(int i = 0; i < iterations; i++)
        {
            long start = System.currentTimeMillis();
            em.createQuery(query).getResultList();
            totalTime += System.currentTimeMillis() - start;
        }
        System.out.println("Benchmarking " + label + " Average time : " + (totalTime / (double) iterations)  + " ms");
    }
}
