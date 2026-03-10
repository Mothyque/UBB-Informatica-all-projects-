package runner;

public class DelayTaskRunner extends AbstractTaskRunner
{
    public DelayTaskRunner(TaskRunner runner)
    {
        super(runner);
    }

    @Override
    public void executeOneTask()
    {
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        super.executeOneTask();
    }
}
