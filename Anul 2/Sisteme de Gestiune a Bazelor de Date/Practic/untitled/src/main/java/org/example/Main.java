package org.example;

import org.example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner
{
    @Autowired
    private NonRepeatableReadService nonRepeatableReadService;

    @Autowired
    private PhantomReadService phantomReadService;

    @Autowired
    private DirtyReadService dirtyReadService;

    @Autowired
    private LostUpdateService lostUpdateService;

    @Autowired
    private ConnectionPoolService connectionPoolService;

    @Autowired
    private DeadlockService deadlockService;

    @Autowired
    private NPlusOneService nPlusOneService;

    @Autowired
    private BatchInsertService batchInsertService;

    @Autowired
    private DatabasePopulator databasePopulator;

    @Autowired
    private BackgroundWorker backgroundWorker;


    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
//        executeNonRepeatableDemo();
//        executePhantomReadDemo();
//        executeDirtyReadDemo();
//        executeLostUpdateDemo();
//        executeConnectionPoolDemo();
//        executeDeadlockDemo();
//        executeNPlusOneDemo();
        executeBatchInsertDemo();
    }


    private void executeNonRepeatableDemo() {
        System.out.println("\n=== NON-REPEATABLE READ DEMO ===");

        int productId1 = databasePopulator.createTestProduct("Test Product", 100.0, 10);
        nonRepeatableReadService.simulateNonRepeatableRead(productId1,
                () -> backgroundWorker.commitBackgroundUpdate(productId1, 150.0)
        );

        System.out.println();

        int productId2 = databasePopulator.createTestProduct("Test Product 2", 200.0, 20);
        nonRepeatableReadService.preventNonRepeatableRead(productId2,
                () -> backgroundWorker.commitBackgroundUpdate(productId2, 250.0)
        );
    }

    private void executePhantomReadDemo() {
        System.out.println("\n=== PHANTOM READ DEMO ===");

        int productId1 = databasePopulator.createTestProduct("Test Product", 100.0, 20);

        phantomReadService.simulatePhantomRead(
                () -> backgroundWorker.commitBackgroundInsertion("Phantom Product", 300.0, 30)
        );

        System.out.println();

        phantomReadService.preventPhantomRead(
                () -> backgroundWorker.commitBackgroundInsertion("Phantom Product 2", 400.0, 40)
        );
    }

    private void executeDirtyReadDemo() {
        System.out.println("\n=== DIRTY READ DEMO ===");

        int productId1 = databasePopulator.createTestProduct("Dirty Read Product 1", 500.0, 5);
        dirtyReadService.simulateDirtyRead(productId1,
                () -> backgroundWorker.updateWithForcedRollback(productId1, 999.0)
        );

        System.out.println();

        int productId2 = databasePopulator.createTestProduct("Dirty Read Product 2", 600.0, 8);
        dirtyReadService.preventDirtyRead(productId2,
                () -> backgroundWorker.updateWithForcedRollback(productId2, 888.0)
        );
    }

    private void executeLostUpdateDemo() {
        System.out.println("\n=== LOST UPDATE DEMO ===");

        int productId1 = databasePopulator.createTestProduct("Lost Update Item 1", 100.0, 10);
        lostUpdateService.simulateLostUpdate(productId1, () ->
                backgroundWorker.commitBackgroundUpdate(productId1, 350.0)
        );

        System.out.println();

        int productId2 = databasePopulator.createTestProduct("Lost Update Item 2", 200.0, 10);
        lostUpdateService.preventLostUpdate(productId2, () ->
                backgroundWorker.commitBackgroundUpdate(productId2, 450.0)
        );
    }

    private void executeConnectionPoolDemo() {
        System.out.println("\n=== CONNECTION POOL EXHAUSTION DEMO ===");

        int testProductId = databasePopulator.createTestProduct("Pool Test Product", 10.0, 100);

        Thread t1 = new Thread(() -> {
            connectionPoolService.holdConnectionMidFlight("Thread-1", testProductId);
        });

        Thread t2 = new Thread(() -> {
            connectionPoolService.holdConnectionMidFlight("Thread-2", testProductId);
        });

        Thread t3 = new Thread(() -> {
            try {
                System.out.println("   [Thread-3] trying to start a transaction...");
                connectionPoolService.holdConnectionMidFlight("Thread-3", testProductId);
            } catch (Exception e) {
                System.out.println("\n   >> ANOMALY DETECTED: [Thread-3] failed to acquire a connection!");
                System.out.println("   >> ERROR REASON: " + e.getCause().getMessage());
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void executeDeadlockDemo() {
        System.out.println("\n=== DATABASE DEADLOCK DEMO ===");

        int productAId = databasePopulator.createTestProduct("Product A", 10.0, 5);
        int productBId = databasePopulator.createTestProduct("Product B", 20.0, 5);

        Thread t1 = new Thread(() -> {
            try {
                deadlockService.lockFirstThenSecond(productAId, productBId, "User-1 (Locks A->B)");
            } catch (Exception e) {
                System.out.println("\n   >> [System Action]: User-1 transaction aborted by the database deadlock detector!");
                System.out.println("   >> ERROR TYPE: " + e.getClass().getSimpleName());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                deadlockService.lockFirstThenSecond(productBId, productAId, "User-2 (Locks B->A)");
            } catch (Exception e) {
                System.out.println("\n   >> [System Action]: User-2 transaction aborted by the database deadlock detector!");
                System.out.println("   >> ERROR TYPE: " + e.getClass().getSimpleName());
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void executeNPlusOneDemo() {
        System.out.println("\n=== N+1 QUERY PERFORMANCE DEMO ===");
        nPlusOneService.setupTestData();

        nPlusOneService.simulateNPlusOneProblem();
        System.out.println();
        nPlusOneService.preventNPlusOneProblem();
    }

    private void executeBatchInsertDemo() {
        System.out.println("\n=== BATCH INSERT PERFORMANCE DEMO ===");
        batchInsertService.executeStandardMassInsertion(100);
        System.out.println();
        batchInsertService.executeBatchMassInsertion(100);
    }
}
