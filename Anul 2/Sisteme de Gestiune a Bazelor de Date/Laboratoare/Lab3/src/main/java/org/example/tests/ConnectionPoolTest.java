package org.example.tests;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPoolTest
{
    private static final String URL = "jdbc:postgresql://localhost:5432/College";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mathy";
    private static final int ITERATIONS = 100;

    public static void main(String[] args)
    {
        System.out.println("Test performanta: Connection Pooling");
        try
        {
            testWithoutPooling();
            testWithPooling();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void testWithoutPooling() throws SQLException
    {
        System.out.println("Test fara pooling...");
        long start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++)
        {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.close();
        }

        long end = System.currentTimeMillis();
        long total = end - start;
        double average = (double) total  / ITERATIONS;
        System.out.println("Timp total: " + total + " ms");
        System.out.println("Timp mediu: " + average + " ms");
    }

    private static void testWithPooling() throws SQLException
    {
        System.out.println("Test fara pooling...");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);

        HikariDataSource ds = new HikariDataSource(config);
        long start = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++)
        {
            Connection conn = ds.getConnection();
            conn.close();
        }

        long end = System.currentTimeMillis();
        long total = end - start;
        double average = (double) total  / ITERATIONS;

        System.out.println("Timp total: " + total + " ms");
        System.out.println("Timp mediu: " + average + " ms");
        ds.close();
    }
}