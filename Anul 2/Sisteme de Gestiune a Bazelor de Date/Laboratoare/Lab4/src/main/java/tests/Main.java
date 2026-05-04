package tests;

import domain.Department;
import domain.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import service.EmployeeIndexingService;
import service.NPlusOneService;
import service.PaginationService;

import java.util.List;
@SpringBootApplication
@ComponentScan(basePackages = {"service"})
@EntityScan(basePackages = {"domain"})

public class Main
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        NPlusOneService nPlusOneService = ctx.getBean(NPlusOneService.class);
        PaginationService paginationService = ctx.getBean(PaginationService.class);
        EmployeeIndexingService employeeIndexingService = ctx.getBean(EmployeeIndexingService.class);
        try
        {
//            runNPlusOneDemo(nPlusOneService);
//            runNPlusOneDemoFixed(nPlusOneService);
//            runIndexingDemo(employeeIndexingService);
            runPaginationDemo(paginationService);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void runNPlusOneDemo(NPlusOneService service)
    {
        System.out.println("=== Running NPlusOneDemo ===");
        service.seedData();
        service.runNPlusOneDemo();
        System.out.println("=== Done running NPlusOneDemo ===");
    }

    private static void runNPlusOneDemoFixed(NPlusOneService service)
    {
        System.out.println("=== Running NPlusOneDemo Fixed ===");
        service.seedData();
        service.runFixedDemo();
        System.out.println("=== Done running NPlusOneDemo Fixed ===");
    }

    private static void runIndexingDemo(EmployeeIndexingService service)
    {
        System.out.println("=== Running IndexingDemo ===");
//        service.seedLargeDataset();
        service.runBenchmarks();
        System.out.println("=== Done running IndexingDemo ===");
    }

    private static void runPaginationDemo(PaginationService service)
    {
        System.out.println("=== Running PaginationDemo ===");
        service.runPaginationDemo();
        System.out.println("=== Done running PaginationDemo ===");
    }
}