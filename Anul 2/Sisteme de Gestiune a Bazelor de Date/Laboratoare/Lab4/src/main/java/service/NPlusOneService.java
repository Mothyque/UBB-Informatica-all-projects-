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
public class NPlusOneService
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void seedData()
    {
        Random rand = new Random();
        for (int i = 1; i <= 10; i++)
        {
            Department department = new Department();
            department.setName("Department " + i);
            em.persist(department);
            for (int j = 1; j <= 3000; j++)
            {
                Employee e = new Employee();
                e.setName("Employee " + i);
                e.setEmail("employee" + i + "@example.com");
                e.setSalary(50000 + (rand.nextInt(30000)));
                e.setDepartment(department);
                em.persist(e);
            }
        }
        em.flush();
        em.clear();
    }

    @Transactional(readOnly = true)
    public void  runNPlusOneDemo()
    {
        long startTime =  System.currentTimeMillis();

        List<Department> departments = em
                .createQuery("SELECT d FROM Department d", Department.class)
                .getResultList();

        for (Department department : departments)
        {
            System.out.println(department.getName() + " has " + department.getEmployees().size() + " employees");
        }
        long endTime =  System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }

    @Transactional(readOnly = true)
    public void runFixedDemo()
    {
        long startTime =  System.currentTimeMillis();
        List<Department> departments = em
                .createQuery("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees", Department.class)
                .getResultList();

        for (Department department : departments)
        {
            System.out.println(department.getName() + " has " + department.getEmployees().size() + " employees");
        }
        long endTime =  System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
}