namespace taskrunner.container;

public class StackContainer : IContainer
{
    private Task[] tasks;
    private int size;

    public StackContainer()
    {
        tasks = new Task[10];
        size = 0;
    }

    public Task Remove()
    {
        if (!IsEmpty())
        {
            size--;
            return tasks[size];
        }
        return null;
    }

    public void Add(Task task)
    {
        if (tasks.Length == size)
        {
            Task[] temp = new Task[tasks.Length * 2];
            Array.Copy(tasks, temp, tasks.Length);
            tasks = temp;
        }

        tasks[size] = task;
        size++;
    }

    public int Size()
    {
        return size;
    }

    public bool IsEmpty()
    {
        return size == 0;
    }
}