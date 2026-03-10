import domain.MessageTask;
import domain.Strategy;
import runner.DelayTaskRunner;
import runner.PrinterTaskRunner;
import runner.StrategyTaskRunner;
import runner.TaskRunner;

import java.time.LocalDateTime;

public class Main
{

    public static MessageTask[] createMessageTaskArray()
    {
        MessageTask t1=new MessageTask("1","Feedback lab1",
                "m1","Gigi", "Ana", LocalDateTime.now());
        MessageTask t2=new MessageTask("2","Feedback lab1",
                "m2","Gigi", "Ana", LocalDateTime.now());
        MessageTask t3=new MessageTask("3","Feedback lab3",
                "m3","Gigi", "Ana", LocalDateTime.now());
        return new MessageTask[]{t1,t2,t3};
    }


    public static void main(String[] args)
    {

        MessageTask[] l = Main.createMessageTaskArray();
        TaskRunner runner = new StrategyTaskRunner(Strategy.FIFO);
        runner.addTask(l[0]);
        runner.addTask(l[1]);
        runner.addTask(l[2]);
//        runner.executeAll();

        TaskRunner decorator = new PrinterTaskRunner(runner);
//        decorator.executeAll();
        TaskRunner delayer = new DelayTaskRunner(decorator);
        delayer.executeAll();
    }

}