package domain;

public class NatatieTask extends Task
{
    private RaceSolver solver;

    public NatatieTask(String descriere, String taskId, RaceSolver solver)
    {
        super(descriere, taskId);
        this.solver = solver;
    }

    @Override
    public void execute()
    {
        solver.solve();
    }
}
