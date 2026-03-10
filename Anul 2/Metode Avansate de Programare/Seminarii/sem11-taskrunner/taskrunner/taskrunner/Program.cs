// See https://aka.ms/new-console-template for more information

using taskrunner;
using taskrunner.container;
using taskrunner.runner;

static MessageTask[] CreateMessageTaskArray()
{
    MessageTask t1 = new MessageTask(
        "1",
        "Feedback lab1",
        "Ai obtinut 9.60",
        "Gigi",
        "Ana",
        DateTime.Now
    );

    MessageTask t2 = new MessageTask(
        "2",
        "Feedback lab1",
        "Ai obtinut 5.60",
        "Gigi",
        "Ana",
        DateTime.Now
    );

    MessageTask t3 = new MessageTask(
        "3",
        "Feedback lab3",
        "Ai obtinut 10",
        "Gigi",
        "Ana",
        DateTime.Now
    );

    return new[] { t1, t2, t3 };
}


MessageTask[] l = CreateMessageTaskArray();

StrategyTaskRunner runner = new StrategyTaskRunner(Strategy.LIFO);
runner.AddTask(l[0]);
runner.AddTask(l[1]);
runner.AddTask(l[2]);
// runner.ExecuteAll();

PrinterTaskRunner decorator = new PrinterTaskRunner(runner);
decorator.ExecuteAll();

