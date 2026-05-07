package tests;

import controller.EmployeeController;
import domain.Department;
import domain.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.*;

import java.util.List;
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"service", "controller", "utils"})
@EntityScan(basePackages = {"domain"})

public class Main
{
    public static ApplicationContext ctx;
    public static void main(String[] args)
    {
        ctx = SpringApplication.run(Main.class, args);
        NPlusOneService nPlusOneService = ctx.getBean(NPlusOneService.class);
        EmployeeIndexingService employeeIndexingService = ctx.getBean(EmployeeIndexingService.class);
        PaginationService paginationService = ctx.getBean(PaginationService.class);
        CacheDemoService cacheDemoService = ctx.getBean(CacheDemoService.class);
        BulkUpdateDemoService bulkUpdateDemoService = ctx.getBean(BulkUpdateDemoService.class);
        StatementReuseService statementReuseService = ctx.getBean(StatementReuseService.class);
        try
        {
            nPlusOneService.seedData();
//            runNPlusOneDemo(nPlusOneService);
//            runNPlusOneDemoFixed(nPlusOneService);
//            runIndexingDemo(employeeIndexingService);
//            runPaginationDemo(paginationService);
//            runCacheDemo(cacheDemoService);
//            runBulkUpdateDemo(bulkUpdateDemoService);
//            runStatementReuseDemo(statementReuseService);
//            Application.launch(EmployeeController.class, args);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void runNPlusOneDemo(NPlusOneService service)
    {
        System.out.println("=== Running NPlusOneDemo ===");
        service.runNPlusOneDemo();
        System.out.println("=== Done running NPlusOneDemo ===");
    }

    private static void runNPlusOneDemoFixed(NPlusOneService service)
    {
        System.out.println("=== Running NPlusOneDemo Fixed ===");
        service.runFixedDemo();
        System.out.println("=== Done running NPlusOneDemo Fixed ===");
    }

    private static void runIndexingDemo(EmployeeIndexingService service)
    {
        System.out.println("=== Running IndexingDemo ===");
        service.runBenchmarks();
        System.out.println("=== Done running IndexingDemo ===");
    }

    private static void runPaginationDemo(PaginationService service)
    {
        System.out.println("=== Running PaginationDemo ===");
        service.runPaginationDemo();
        System.out.println("=== Done running PaginationDemo ===");
    }

    private static void runCacheDemo(CacheDemoService service)
    {
        System.out.println("=== Running CacheDemo ===");
        service.runDemo();
        System.out.println("=== Done running CacheDemo ===");
    }

    private static void runBulkUpdateDemo(BulkUpdateDemoService service)
    {
        System.out.println("=== Running BulkUpdateDemo ===");
        service.runDemo();
        System.out.println("=== Done running BulkUpdateDemo ===");
    }

    private static void runStatementReuseDemo(StatementReuseService service)
    {
        System.out.println("=== Running StatementReuseDemo ===");
        service.runDemo();
        System.out.println("=== Done running StatementReuseDemo ===");
    }
}