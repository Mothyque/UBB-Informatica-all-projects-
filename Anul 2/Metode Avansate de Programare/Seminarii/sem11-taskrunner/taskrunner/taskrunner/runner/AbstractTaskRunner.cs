namespace taskrunner.runner;

public abstract class AbstractTaskRunner : ITaskRunner
{
    protected ITaskRunner runner;

    protected AbstractTaskRunner(ITaskRunner runner)
    {
        this.runner = runner;
    }

    public virtual void ExecuteOneTask()
    {
        runner.ExecuteOneTask();
    }

    // dacă vrei o implementare diferită, suprascrii metoda în subclasă
    // public abstract void ExecuteOneTask();

    public virtual void ExecuteAll()
    {
        while (runner.HasTask())
        {
            ExecuteOneTask();
        }
    }

    public virtual void AddTask(Task t)
    {
        runner.AddTask(t);
    }

    public virtual bool HasTask()
    {
        return runner.HasTask();
    }
}