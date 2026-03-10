package factory;

import domain.Container;
import domain.QueueContainer;
import domain.StackContainer;
import domain.Strategy;

public class TaskContainerFactory implements Factory
{

    //private static TaskContainerFactory instance = new TaskContainerFactory();

    private TaskContainerFactory() {}

    private static TaskContainerFactory instance = null;
    public static TaskContainerFactory getInstance()
    {
        if (instance != null)
        {
            return instance;
        }
        else
        {
            instance = new TaskContainerFactory();
            return instance;
        }
    }

    @Override
    public Container createContainer(Strategy strategy)
    {
        if (strategy == Strategy.FIFO)
        {
            return new QueueContainer();
        }
        else if (strategy == Strategy.LIFO)
        {
            return new StackContainer();
        }
        return null;
    }
}