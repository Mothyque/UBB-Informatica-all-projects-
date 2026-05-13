package service;

import domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateEmployee(Employee employee)
    {
        entityManager.merge(employee);
        entityManager.flush();
    }

    @Transactional
    public void softDelete(Integer id)
    {
        Employee emp = findById(id);
        if (emp != null) {
            entityManager.remove(emp);
        }
    }

    @Transactional(readOnly = true)
    public List<Employee> findDeletedEmployees() {
        return entityManager.createNativeQuery(
                        "SELECT * FROM employee WHERE deleted = true", Employee.class)
                .getResultList();
    }

    @Transactional
    public void restoreEmployee(Integer id)
    {
        Employee emp = (Employee) entityManager.createNativeQuery(
                        "SELECT * FROM employee WHERE id = ?", Employee.class)
                .setParameter(1, id)
                .getSingleResult();

        if (emp != null) {
            emp.setDeleted(false);
            emp.setDeletedAt(null);
            emp.setDeletedBy(null);
            entityManager.merge(emp);
            System.out.println("Employee " + id + " restored successfully.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Employee findById(Integer id)
    {
        return entityManager.find(Employee.class, id);
    }
}