package domain;

import strategies.OptimisationStrategy;

public class RaceSolver
{
    private OptimisationStrategy strategy;
    private Race race;

    public RaceSolver(Race race, OptimisationStrategy strategy)
    {
        this.race = race;
        this.strategy = strategy;
    }

    public void solve()
    {
        strategy.solveRace(race);
    }
}
