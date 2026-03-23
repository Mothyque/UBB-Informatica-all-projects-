package org.example;
public class Main {
    public static void main(String[] args)
    {
        ConcurrencyDemo demo = new ConcurrencyDemo();
//        runDirtyReads(demo);
//        runNonRepeatableReads(demo);
//        runPhantomReads(demo);
//        runLostUpdate(demo);
//        runDeadLock(demo);
//        demo.runInsertAutoCommit();
//        demo.runInsertBatch();
//        demo.runInsertSingleTransaction();
    }

    private static void runDirtyReads(ConcurrencyDemo demo)
    {
        System.out.println("Running with possibility of dirty reads:");
        demo.runDirtyReadDemo(false);
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("\nNow running with prevention of dirty reads:");
        demo.runDirtyReadDemo(true);
        System.out.println("------------------------------------------------------------");
    }

    private static void runNonRepeatableReads(ConcurrencyDemo demo)
    {
        System.out.println("Running with possibility of non-repeatable reads:");
        demo.runNonRepeatableReadDemo(false);
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("\nNow running with prevention of non-repeatable reads:");
        demo.runNonRepeatableReadDemo(true);
        System.out.println("------------------------------------------------------------");
    }

    private static void runPhantomReads(ConcurrencyDemo demo)
    {
        System.out.println("Running with possibility of phantom reads:");
        demo.runPhantomReadDemo(false);
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("\nNow running with prevention of phantom reads:");
        demo.runPhantomReadDemo(true);
        System.out.println("------------------------------------------------------------");
    }

    private static void runLostUpdate(ConcurrencyDemo demo)
    {
        System.out.println("Running with possibility of lost updates:");
        demo.runLostUpdateDemo(false);
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("\nNow running with prevention of lost updates:");
        demo.runLostUpdateDemo(true);
        System.out.println("------------------------------------------------------------");
    }

    private static void runDeadLock(ConcurrencyDemo demo)
    {
        System.out.println("Running with possibility of deadlock:");
        demo.runDeadLockDemo(false);
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("\nNow running with prevention of deadlock:");
        demo.runDeadLockDemo(true);
        System.out.println("------------------------------------------------------------");
    }

}