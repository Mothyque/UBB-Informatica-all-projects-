package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConnectionPoolService connectionPoolService;

    @Autowired
    private DatabasePopulator databasePopulator;


    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        executeConnectionPoolDemo();
        productRepository.save(new Product("Nume", "Data", 100, "Producator"));
        System.out.println(productRepository.size());
    }



    private void executeConnectionPoolDemo() {
        System.out.println("\n=== CONNECTION POOL EXHAUSTION DEMO ===");

        int testProductId = databasePopulator.createTestProduct("Pool Test Product", "Data", 100, "Producator");

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



}
