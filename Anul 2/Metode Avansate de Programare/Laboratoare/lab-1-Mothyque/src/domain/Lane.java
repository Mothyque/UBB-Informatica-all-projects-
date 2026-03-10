package domain;

public class Lane
{
    private final int id;
    private final double distance;
    private Duck assignedDuck;

    public Lane(int id, double distance)
    {
        this.id = id;
        this.distance = distance;
    }

    public int getId()
    {
        return id;
    }

    public double getDistance()
    {
        return distance;
    }

    public void assignDuck(Duck duck)
    {
        this.assignedDuck = duck;
    }

    public boolean isOccupied()
    {
        return assignedDuck != null;
    }

    public Duck getAssignedDuck()
    {
        return assignedDuck;
    }

    @Override
    public String toString()
    {
        return "Lane{" +
                "id=" + id +
                ", distance=" + distance +
                '}';
    }

}
