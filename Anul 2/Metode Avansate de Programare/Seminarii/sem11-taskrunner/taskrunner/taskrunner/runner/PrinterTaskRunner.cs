using taskrunner.utils;

namespace taskrunner.runner;

public class PrinterTaskRunner : AbstractTaskRunner
{
    public PrinterTaskRunner(ITaskRunner runner)
        : base(runner)
    {
    }

    public override void ExecuteOneTask()
    {
        base.ExecuteOneTask();
        DecorateExecuteOneTask();
    }

    public void DecorateExecuteOneTask()
    {
        Console.WriteLine(
            "task executat la: " +
            DateTime.Now.ToString(Constants.DateTimeFormatter)
        );
    }
}