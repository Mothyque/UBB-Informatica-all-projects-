package service;

import domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BulkUpdateService
{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public long individualUpdate(Long deptId)
    {
        long start = System.nanoTime();
        List<Employee> employees = getEmployees(deptId);
        for (Employee emp : employees)
        {
            emp.setSalary((int) (emp.getSalary() * 1.1));
            em.merge(emp);
        }
        em.flush();
        return System.nanoTime() - start;
    }

    @Transactional
    public long bulkQueryUpdate(Long deptId)
    {
        long start = System.nanoTime();
        em.createQuery("UPDATE Employee e SET e.salary = CAST (e.salary * 1.1 AS integer) WHERE e.department.id = :deptId")
                .setParameter("deptId", deptId)
                .executeUpdate();
        return System.nanoTime() - start;
    }

    @Transactional
    public long batchUpdate(Long deptId)
    {
        long start = System.nanoTime();
        List<Employee> employees = getEmployees(deptId);
        int batchSize = 50;
        for (int i = 0; i < employees.size(); i++)
        {
            Employee emp = employees.get(i);
            emp.setSalary((int) (emp.getSalary() * 1.1));
            em.merge(emp);

            if (i % batchSize == 0 && i > 0)
            {
                em.flush();
                em.clear();
            }
        }
        em.flush();
        return System.nanoTime() - start;
    }

    private List<Employee> getEmployees(Long deptId)
    {
        return em.createQuery("SELECT e FROM Employee e WHERE e.department.id = :deptId", Employee.class)
                .setParameter("deptId", deptId)
                .getResultList();
    }
}