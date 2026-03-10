namespace taskrunner.container;

public class QueueContainer : IContainer
{
    private Task[] tasks;
    private int size;

    public QueueContainer()
    {
        tasks = new Task[10];
        size = 0;
    }

    public Task Remove()
    {
        if (IsEmpty())
            return null;

        Task first = tasks[0];

        // shift la stânga
        for (int i = 1; i < size; i++)
        {
            tasks[i - 1] = tasks[i];
        }

        size--;
        return first;
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