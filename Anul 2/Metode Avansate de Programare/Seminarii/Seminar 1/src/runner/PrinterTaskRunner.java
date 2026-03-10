package runner;

public class PrinterTaskRunner extends AbstractTaskRunner
{
    public PrinterTaskRunner(TaskRunner runner)
    {
        super(runner);
    }

    @Override
    public void executeOneTask()
    {
        System.out.println("Executing one task");
        super.executeOneTask();
        System.out.println("Task executed at " + java.time.LocalDateTime.now());
    }
}
