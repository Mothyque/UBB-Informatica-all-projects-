package service;

import domain.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentCacheService
{

    @PersistenceContext
    private EntityManager entityManager;

    @Cacheable(value = "departments", key = "#id")
    public Department getDepartmentById(Long id)
    {
        System.out.println("--- Cache MISS: Fetching department " + id + " from Database ---");
        return entityManager.find(Department.class, id);
    }

    @Transactional
    @CacheEvict(value = "departments", key = "#department.id")
    public void updateDepartment(Department department)
    {
        System.out.println("--- Cache EVICT: Updating department " + department.getId() + " and clearing cache ---");
        entityManager.merge(department);
    }
}