package service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheDemoService {

    @Autowired
    private DepartmentCacheService departmentService;

    @Autowired
    private Cache<Object, Object> departmentsCaffeineCache;

    public void runDemo()
    {
        Long deptId = 1L;

        long start = System.nanoTime();
        departmentService.getDepartmentById(deptId);
        long end = System.nanoTime();
        System.out.println("First call (Miss) time: " + (end - start) / 1_000_000.0 + " ms");

        start = System.nanoTime();
        departmentService.getDepartmentById(deptId);
        end = System.nanoTime();
        System.out.println("Second call (Hit) time: " + (end - start) / 1_000_000.0 + " ms");

        Department dept = departmentService.getDepartmentById(deptId);
        dept.setName("Updated Name");
        departmentService.updateDepartment(dept);

        start = System.nanoTime();
        departmentService.getDepartmentById(deptId);
        end = System.nanoTime();
        System.out.println("Call after Evict (Miss) time: " + (end - start) / 1_000_000.0 + " ms");

        System.out.println("[TTL Demo] Waiting 11 seconds for cache to expire...");
        try
        {
            Thread.sleep(11000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }

        System.out.println("Fetching after TTL expiration:");
        start = System.nanoTime();
        departmentService.getDepartmentById(deptId);
        end = System.nanoTime();
        System.out.println("Call after TTL Expiration (Miss) time: " + (end - start) / 1_000_000.0 + " ms");

        printStats();
    }

    private void printStats()
    {
        if (departmentsCaffeineCache != null)
        {
            CacheStats stats = departmentsCaffeineCache.stats();

            System.out.println("Cache Stats:");
            System.out.println("Hits: " + stats.hitCount());
            System.out.println("Misses: " + stats.missCount());
            System.out.println("Total requests: " + (stats.hitCount() + stats.missCount()));
            System.out.println("Hit Rate: " + String.format("%.2f%%", stats.hitRate() * 100));
            System.out.println("Evictions: " + stats.evictionCount());
            System.out.println("Load time (avg): " + String.format("%.4f ms", stats.averageLoadPenalty() / 1_000_000.0));
            System.out.println("--------------------------------------------");
        }
        else
        {
            System.out.println("Cache not found!");
        }
    }
}
