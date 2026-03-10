package domain;

public class Race
{
    private Duck[] ducks;
    private Lane[] lanes;
    private int duckNumber;
    private int laneNumber;

    public Race(Duck[] ducks, Lane[] lanes,int duckNumber, int laneNumber)
    {
        this.ducks = ducks;
        this.lanes = lanes;
        this.duckNumber = duckNumber;
        this.laneNumber = laneNumber;
    }

    public void assignDuckToLane(Duck duck, int laneIndex)
    {
        if (laneIndex < 0 || laneIndex >= laneNumber)
        {
            throw new IllegalArgumentException("Invalid lane index");
        }
        if (lanes[laneIndex].isOccupied())
        {
            throw new IllegalStateException("Lane is already occupied");
        }
        lanes[laneIndex].assignDuck(duck);
    }

    public void clearAssignments()
    {
        for (Lane lane : lanes)
        {
            lane.assignDuck(null);
        }
    }

    public Duck getAssignedDuckInLane(int laneIndex)
    {
        if (laneIndex < 0 || laneIndex >= laneNumber)
        {
            throw new IllegalArgumentException("Invalid lane index");
        }
        return lanes[laneIndex].getAssignedDuck();
    }

    public Duck[] getDucks()
    {
        return ducks;
    }

    public Lane[] getLanes()
    {
        return lanes;
    }

    public int getDuckNumber()
    {
        return duckNumber;
    }

    public int getLaneNumber()
    {
        return laneNumber;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Race:\n");
        for (int i = 0; i < laneNumber; i++)
        {
            sb.append("Lane ").append(i + 1).append(": ");
            if (lanes[i].isOccupied())
            {
                sb.append(lanes[i].getAssignedDuck().getId()).append("\n");
            }
            else
            {
                sb.append("Empty\n");
            }
        }
        return sb.toString();
    }
}
