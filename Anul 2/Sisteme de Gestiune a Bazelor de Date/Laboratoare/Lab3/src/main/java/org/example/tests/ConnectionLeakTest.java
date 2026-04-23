package org.example.tests;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConnectionLeakTest
{
    private static final String URL = "jdbc:postgresql://localhost:5432/College";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mathy";

    public static void main(String[] args)
    {
        System.out.println("Test: Epuizarea Pool-ului de conexiuni");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(3000);

        HikariDataSource dataSource = new HikariDataSource(config);
        List<Connection> connections = new ArrayList<>();

        try
        {
            System.out.println("Lasam conexiuni deschise intentionat");

            for (int i = 1; i <= 11; i++)
            {
                System.out.println("Cerem conexiunea nr" + i);
                Connection conn = dataSource.getConnection();
                connections.add(conn);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        finally
        {
            System.out.println("Inchidem conexiunile deschise pentru a rezolva problema");
            for (Connection connection : connections)
            {
                try
                {
                    if (connection != null && !connection.isClosed())
                    {
                        connection.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            dataSource.close();
        }
    }
}