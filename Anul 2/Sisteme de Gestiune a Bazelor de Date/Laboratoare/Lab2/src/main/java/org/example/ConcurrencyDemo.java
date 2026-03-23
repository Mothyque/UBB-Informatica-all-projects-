package org.example;

import java.sql.*;

public class ConcurrencyDemo
{
    private static final String url = "jdbc:sqlserver://localhost:1433;database=transactions;encrypt=true;trustServerCertificate=true;user=sa;password=admin;";

    public void runDirtyReadDemo(boolean preventProblem)
    {
        Thread threadA = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                conn.setAutoCommit(false);
                String query = "UPDATE employees SET salary = salary - 1000 WHERE id = 1";
                try (PreparedStatement statement = conn.prepareStatement(query))
                {
                    statement.executeUpdate();
                    System.out.println("Thread A: Updated balance, but not committed yet.");
                }
                Thread.sleep(3000);

                conn.rollback();
                System.out.println("Thread A: Rolled back transaction. Balance resets to original value.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        Thread threadB = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                Thread.sleep(1000);
                conn.setAutoCommit(false);

                if (preventProblem)
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                else
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                }
                String query = "SELECT salary FROM employees WHERE id = 1";
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int salary = rs.getInt("salary");
                        System.out.println("Thread B: Read salary = " + salary);
                    }
                }
                conn.commit();
                System.out.println("Thread B: Committed transaction.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();

        try
        {
            threadA.join();
            threadB.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void runNonRepeatableReadDemo (boolean preventProblem)
    {
        try(Connection conn = DriverManager.getConnection(url); PreparedStatement statement = conn.prepareStatement("UPDATE  employees SET salary = 5000 WHERE id = 1"))
        {
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Thread threadA = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                conn.setAutoCommit(false);
                if (preventProblem)
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                else
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                String query = "SELECT salary FROM employees WHERE id = 1";
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int salary = rs.getInt("salary");
                        System.out.println("Thread A: First read salary = " + salary);
                    }
                }
                Thread.sleep(3000);
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int salary = rs.getInt("salary");
                        System.out.println("Thread A: Second read salary = " + salary);
                    }
                }
                conn.commit();
                System.out.println("Thread A: Committed transaction.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                Thread.sleep(1000);
                String query = "UPDATE employees SET salary = 4000 WHERE id = 1";
                try (PreparedStatement statement = conn.prepareStatement(query))
                {
                    statement.executeUpdate();
                    System.out.println("Thread B: Updated salary to 4000.");
                }
                conn.commit();
                System.out.println("Thread B: Committed transaction.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();

        try
        {
            threadA.join();
            threadB.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void runPhantomReadDemo (boolean preventProblem)
    {
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement statement = conn.prepareStatement("DELETE FROM employees WHERE department_id = 5"))
        {
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Thread threadA = new Thread(() ->{
            try (Connection conn = DriverManager.getConnection(url))
            {
                conn.setAutoCommit(false);
                if (preventProblem)
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                }
                else
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                String query = "SELECT COUNT(*) AS count FROM employees WHERE department_id = 5";
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int count = rs.getInt("count");
                        System.out.println("Thread A: First read employee count in department 5 = " + count);
                    }
                }
                Thread.sleep(3000);
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int count = rs.getInt("count");
                        System.out.println("Thread A: Second read employee count in department 5 = " + count);
                    }
                }
                conn.commit();
                System.out.println("Thread A: Committed transaction.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                Thread.sleep(1000);
                String query = "INSERT INTO employees (id, name, salary, department_id) VALUES (100, 'New Employee', 3000, 5)";
                try (PreparedStatement statement = conn.prepareStatement(query))
                {
                    statement.executeUpdate();
                    System.out.println("Thread B: Inserted new employee into department 5.");
                }
                conn.commit();
                System.out.println("Thread B: Committed transaction.");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        try
        {
            threadA.join();
            threadB.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void runLostUpdateDemo (boolean preventProblem)
    {
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement statement = conn.prepareStatement("UPDATE employees SET salary = 5000 WHERE id = 1"))
        {
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Thread threadA = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                conn.setAutoCommit(false);
                if (preventProblem)
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                else
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                String query = "SELECT salary FROM employees WHERE id = 1";
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int salary = rs.getInt("salary");
                        System.out.println("Thread A: Read salary = " + salary);
                        int newSalary = salary + 1000;
                        Thread.sleep(3000);
                        try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE employees SET salary = ? WHERE id = 1"))
                        {
                            updateStatement.setInt(1, newSalary);
                            updateStatement.executeUpdate();
                            System.out.println("Thread A: Updated salary to " + newSalary);
                        }
                    }
                }
                conn.commit();
                System.out.println("Thread A: Committed transaction.");
            }
            catch (Exception e)
            {
                System.out.println("Thread A was interrupted: " + e.getMessage());
            }
        });

        Thread threadB = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(url))
            {
                Thread.sleep(1000);
                conn.setAutoCommit(false);
                if (preventProblem)
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                else
                {
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                String query = "SELECT salary FROM employees WHERE id = 1";
                try (PreparedStatement statement = conn.prepareStatement(query); ResultSet rs = statement.executeQuery())
                {
                    if (rs.next())
                    {
                        int salary = rs.getInt("salary");
                        System.out.println("Thread B: Read salary = " + salary);
                        int newSalary = salary - 500;
                        try (PreparedStatement updateStatement = conn.prepareStatement("UPDATE employees SET salary = ? WHERE id = 1"))
                        {
                            updateStatement.setInt(1, newSalary);
                            updateStatement.executeUpdate();
                        }
                        conn.commit();
                        System.out.println("Thread B: Updated salary to " + newSalary + " and committed transaction.");
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Thread B was interrupted: " + e.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        try
        {
            threadA.join();
            threadB.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement statement = conn.prepareStatement("SELECT salary FROM employees WHERE id = 1"); ResultSet rs = statement.executeQuery())
        {
            if (rs.next())
            {
                int finalSalary = rs.getInt("salary");
                System.out.println("Final salary after both transactions: " + finalSalary);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runDeadLockDemo (boolean resolveProblem)
    {
        try(Connection conn = DriverManager.getConnection(url))
        {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE employees SET salary = 5000 WHERE id IN (1, 2)"))
            {
                statement.executeUpdate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Thread threadA = new Thread(() ->
        {
            try (Connection conn = DriverManager.getConnection(url))
            {
                conn.setAutoCommit(false);
                System.out.println("Thread A: Starting transaction and locking employee 1");
                try (PreparedStatement statement = conn.prepareStatement("UPDATE employees SET salary = 6000 WHERE id = 1"))
                {
                    statement.executeUpdate();
                }
                Thread.sleep(2000);
                System.out.println("Thread A: Attempting to lock employee 2");
                try (PreparedStatement statement = conn.prepareStatement("UPDATE employees SET salary = 7000 WHERE id = 2"))
                {
                    statement.executeUpdate();
                }
                conn.commit();
                System.out.println("Thread A: Committed transaction.");
            }
            catch (Exception e)
            {
                System.out.println("Thread A was interrupted: " + e.getMessage());
            }
        });

        Thread threadB = new Thread(() ->
        {
            try(Connection conn = DriverManager.getConnection(url))
            {
                conn.setAutoCommit(false);
                int primulId = 0;
                int alDoileaId = 0;
                if (resolveProblem)
                {
                    primulId = 1;
                    alDoileaId = 2;
                }
                else
                {
                    primulId = 2;
                    alDoileaId = 1;
                }

                System.out.println("Thread B: Starting transaction and locking employee " + primulId);
                try (PreparedStatement statement = conn.prepareStatement("UPDATE employees SET salary = 6000 WHERE id = ?"))
                {
                    statement.setInt(1, primulId);
                    statement.executeUpdate();
                }

                Thread.sleep(2000);

                System.out.println("Thread B: Attempting to lock employee " + alDoileaId);
                try (PreparedStatement statement = conn.prepareStatement("UPDATE employees SET salary = 7000 WHERE id = ?"))
                {
                    statement.setInt(1, alDoileaId);
                    statement.executeUpdate();
                }

                conn.commit();
                System.out.println("Thread B: Committed transaction.");
            }
            catch (Exception e)
            {
                System.out.println("Thread B was interrupted: " + e.getMessage());
            }
        });

        threadA.start();
        threadB.start();

        try
        {
            threadA.join();
            threadB.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void runInsertAutoCommit()
    {
        try (Connection conn = DriverManager.getConnection(url))
        {
            try(PreparedStatement statement = conn.prepareStatement("DELETE FROM employees WHERE id > 2"))
            {
                statement.executeUpdate();
            }

            conn.setAutoCommit(true);
            long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO employees (id, name, salary, department_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql))
            {
                for (int i = 3; i <= 5002; i++)
                {
                    statement.setInt(1, i);
                    statement.setString(2, "Employee " + i);
                    statement.setInt(3, 4000);
                    statement.setInt(4, 1);
                    statement.executeUpdate();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken with auto-commit: " + (endTime - startTime) + " ms");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runInsertBatch()
    {
        try (Connection conn = DriverManager.getConnection(url))
        {
            try(PreparedStatement statement = conn.prepareStatement("DELETE FROM employees WHERE id > 2"))
            {
                statement.executeUpdate();
            }

            conn.setAutoCommit(false);
            long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO employees (id, name, salary, department_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql))
            {
                for (int i = 3; i <= 5002; i++)
                {
                    statement.setInt(1, i);
                    statement.setString(2, "Employee " + i);
                    statement.setInt(3, 4000);
                    statement.setInt(4, 1);
                    statement.addBatch();

                    if ((i - 2) % 100 == 0)
                    {
                        statement.executeBatch();
                        conn.commit();
                    }
                }
                statement.executeBatch();
                conn.commit();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken with batch inserts: " + (endTime - startTime) + " ms");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runInsertSingleTransaction()
    {
        try (Connection conn = DriverManager.getConnection(url))
        {
            try(PreparedStatement statement = conn.prepareStatement("DELETE FROM employees WHERE id > 2"))
            {
                statement.executeUpdate();
            }

            conn.setAutoCommit(false);
            long startTime = System.currentTimeMillis();
            String sql = "INSERT INTO employees (id, name, salary, department_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql))
            {
                for (int i = 3; i <= 5002; i++)
                {
                    statement.setInt(1, i);
                    statement.setString(2, "Employee " + i);
                    statement.setInt(3, 4000);
                    statement.setInt(4, 1);
                    statement.addBatch();
                }
                statement.executeBatch();
                conn.commit();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Time taken with single transaction: " + (endTime - startTime) + " ms");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
