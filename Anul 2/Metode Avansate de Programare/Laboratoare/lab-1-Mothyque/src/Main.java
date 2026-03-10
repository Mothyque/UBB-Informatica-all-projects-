import domain.*;
import runner.*;
import strategies.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        int numberOfTasks;
        int n, m;
        Duck[] ducks;
        Lane[] lanes;
        TaskRunner runner = new StrategyTaskRunner(Strategy.FIFO);
        PrinterTaskRunner printer = new PrinterTaskRunner(runner);
        AbstractTaskRunner delay = new DelayTaskRunner(printer);
        OptimisationStrategy[]  strategies =
        {
            new BacktrackingStrategy(),
            new BinarySearchStrategy(),
            new BruteForceStrategy(),
            new DynamicProgrammingStrategy()
        };

        try
        {
            File inputFile = new File("src/input.txt");
            Scanner fileScanner = new Scanner(inputFile);
            numberOfTasks = fileScanner.nextInt();
            int j = 1;
            while(numberOfTasks > 0)
            {
                n = fileScanner.nextInt();
                m = fileScanner.nextInt();
                ducks = new Duck[n];
                lanes = new Lane[m];
                for (int i = 0; i < n; i++)
                {
                    Duck duck = new Duck(i);
                    duck.setSpeed(fileScanner.nextDouble());
                    ducks[i] = duck;
                }
                for(int i = 0; i < n; i++)
                {
                    ducks[i].setResistance(fileScanner.nextInt());
                }
                for(int i = 0; i < m; i++)
                {
                    Lane lane = new Lane(i, fileScanner.nextDouble());
                    lanes[i] = lane;
                }
                Race race = new Race(ducks, lanes, n, m);
                System.out.println("Which strategy would you like to use for task number " + j +  " ?");
                System.out.println("1: Backtracking");
                System.out.println("2: Binary Search");
                System.out.println("3: Brute Force");
                System.out.println("4: Dynamic Programming");
                Scanner scanner = new  Scanner(System.in);
                int which =  scanner.nextInt();
                RaceSolver solver = new RaceSolver(race, strategies[which - 1]);
                Task task = new NatatieTask("Problema Ratustelor", Integer.toString(which), solver);
                delay.addTask(task);
                j++;
                numberOfTasks--;
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        delay.executeAll();
    }
}