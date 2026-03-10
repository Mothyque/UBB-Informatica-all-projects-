using taskrunner.container;
using taskrunner.factory;

namespace taskrunner.runner;

public class StrategyTaskRunner : ITaskRunner
{
    private IContainer container;

    public StrategyTaskRunner(Strategy strategy)
    {
        container = TaskContainerFactory.GetInstance().CreateContainer(strategy);
    }

    public void ExecuteOneTask()
    {
        if (!container.IsEmpty())
        {
            Task task = container.Remove();
            if (task != null)
            {
                task.Execute();
            }
        }
    }

    public void ExecuteAll()
    {
        while (!container.IsEmpty())
        {
            ExecuteOneTask();
        }
    }

    public void AddTask(Task t)
    {
        container.Add(t);
    }

    public bool HasTask()
    {
        return !container.IsEmpty();
    }
}