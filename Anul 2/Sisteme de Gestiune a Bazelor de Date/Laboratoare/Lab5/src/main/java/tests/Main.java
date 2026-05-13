package tests;

import domain.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import service.*;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"service", "utils"})
@EntityScan(basePackages = {"domain"})

public class Main
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        EmployeeService service = ctx.getBean(EmployeeService.class);
        if (service.findById(1) == null)
        {
            System.out.println("Seed data not found. Creating Employee ID 1...");
            Employee seed = new Employee();
            seed.setName("Initial Name");
            seed.setEmail("test@example.com");
            seed.setSalary(50000.0);
            seed.setVersion(0);
            service.updateEmployee(seed);
        }

        try
        {
//            runOptimisticLockingDemo(service);
            runDemoSoftDelete(service);
        }
        finally
        {
            SpringApplication.exit(ctx, () -> 0);
        }
    }
    private static void runOptimisticLockingDemo(EmployeeService service) {
        System.out.println("\n=== STARTING INTERACTIVE OPTIMISTIC LOCKING DEMO ===");

        Employee userA = service.findById(1);
        Employee userB = service.findById(1);
        System.out.println("Initial Version: " + userA.getVersion());

        userA.setName("Updated by User A");
        try
        {
            service.updateEmployee(userA);
            System.out.println("User A saved successfully. DB Version is now incremented.");
        }
        catch (Exception e)
        {
            System.err.println("User A failed: " + e.getMessage());
            return;
        }

        userB.setName("Attempted by User B");
        System.out.println("User B attempting to save with version: " + userB.getVersion());

        try
        {
            service.updateEmployee(userB);
            System.out.println("User B saved unexpectedly (No conflict detected!)");
        }
        catch (Exception e)
        {
            handleInteractiveConflict(service, userB);
        }
    }

    private static void handleInteractiveConflict(EmployeeService service, Employee staleEmployee)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n[CONFLICT DETECTED] The record was modified by another user.");
        System.out.println("How would you like to proceed?");
        System.out.println("1. [RELOAD] - Discard your changes and fetch latest data.");
        System.out.println("2. [FORCE]  - Overwrite the database with your changes anyway.");
        System.out.println("3. [CANCEL] - Exit without doing anything.");
        System.out.print("Enter choice (1-3): ");

        String choice = scanner.nextLine();

        switch (choice)
        {
            case "1":
                System.out.println("Action: Reloading...");
                Employee latest = service.findById(staleEmployee.getId());
                System.out.println("Success! Latest name in DB is: " + latest.getName() + " (Version: " + latest.getVersion() + ")");
                break;

            case "2":
                System.out.println("Action: Forcing update...");
                Employee currentInDb = service.findById(staleEmployee.getId());
                staleEmployee.setVersion(currentInDb.getVersion());
                service.updateEmployee(staleEmployee);
                System.out.println("Success! Database overwritten. Current Version: " + staleEmployee.getVersion());
                break;

            case "3":
                System.out.println("Action: Canceled. No changes were saved.");
                break;

            default:
                System.out.println("Invalid choice. Operation aborted for safety.");
                break;
        }
    }

    private static void runDemoSoftDelete(EmployeeService service)
    {
        System.out.println("\n=== STARTING SOFT DELETE DEMO ===");

        System.out.println("Deleting Employee ID 1...");
        service.softDelete(1);

        Employee shouldBeNull = service.findById(1);
        System.out.println("Search for ID 1 (standard query) returned: " + (shouldBeNull == null ? "NULL (Success)" : "Found (Failure)"));

        List<Employee> deletedItems = service.findDeletedEmployees();
        System.out.println("Admin View - Deleted Records count: " + deletedItems.size());
        if (!deletedItems.isEmpty()) {
            Employee d = deletedItems.get(0);
            System.out.println("Audit Trail -> Deleted at: " + d.getDeletedAt() + " by: " + d.getDeletedBy());
        }

        System.out.println("Restoring Employee ID 1...");
        service.restoreEmployee(1);

        Employee restored = service.findById(1);
        System.out.println("Search for ID 1 after restoration: " + (restored != null ? restored.getName() : "Still missing"));
    }
}