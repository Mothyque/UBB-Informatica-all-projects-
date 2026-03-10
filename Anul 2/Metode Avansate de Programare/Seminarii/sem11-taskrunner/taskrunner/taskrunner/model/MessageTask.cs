using taskrunner.utils;

namespace taskrunner;

using System;

public class MessageTask : Task
{
    public string Message { get; set; }
    public string From { get; set; }
    public string To { get; set; }
    public DateTime Date { get; set; }

    public MessageTask(
        string taskId,
        string description,
        string message,
        string from,
        string to,
        DateTime date)
        : base(taskId, description)
    {
        Message = message;
        From = from;
        To = to;
        Date = date;
    }

    public override void Execute()
    {
        Console.WriteLine(ToString());
    }

    public override string ToString()
    {
        return $"{base.ToString()} {Message} {From} {Date.ToString(Constants.DateTimeFormatter)}";
    }
}

