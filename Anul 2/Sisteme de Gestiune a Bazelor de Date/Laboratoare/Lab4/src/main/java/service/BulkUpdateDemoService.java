package service;

import domain.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BulkUpdateDemoService {
    @Autowired
    private BulkUpdateService service;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void runDemo()
    {
        Long testId = 1L;

        Department dept = em.find(Department.class, testId);
        if (dept == null)
        {
            System.out.println("Demo aborted: Department with ID " + testId + " not found.");
            return;
        }

        long time1 = service.individualUpdate(testId);
        System.out.println("Approach 1 (Individual): " + (time1 / 1_000_000.0) + " ms");

        long time2 = service.bulkQueryUpdate(testId);
        System.out.println("Approach 2 (Bulk Query): " + (time2 / 1_000_000.0) + " ms");

        long time3 = service.batchUpdate(testId);
        System.out.println("Approach 3 (Manual Batch): " + (time3 / 1_000_000.0) + " ms");

    }
}