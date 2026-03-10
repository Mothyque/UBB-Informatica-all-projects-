using taskrunner.container;

namespace taskrunner.factory;

public class TaskContainerFactory : IFactory
{
    private static TaskContainerFactory instance = null;

    private TaskContainerFactory()
    {
    }

    public static TaskContainerFactory GetInstance()
    {
        if (instance == null)
            instance = new TaskContainerFactory();

        return instance;
    }

    public IContainer CreateContainer(Strategy strategy)
    {
        if (strategy == Strategy.LIFO)
            return new StackContainer();
        else
            return new QueueContainer();
    }
}
