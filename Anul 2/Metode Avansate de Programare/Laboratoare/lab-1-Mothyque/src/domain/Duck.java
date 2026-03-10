package domain;

public class Duck
{
    private int id;
    private double speed;
    private int resistance;

    public Duck(int id)
    {
        this.id = id;
        this.speed = 0;
        this.resistance = 0;
    }

    public Duck(int id, double speed, int resistance)
    {
        this.id = id;
        this.speed = speed;
        this.resistance = resistance;
    }

    public int getId()
    {
        return id;
    }

    public double getSpeed()
    {
        return speed;
    }

    public int getResistance()
    {
        return resistance;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public void setResistance(int resistance)
    {
        this.resistance = resistance;
    }


    public double getTime(double distance)
    {
        return distance / speed;
    }

    @Override
    public String toString()
    {
        return "Duck{" +
                "id=" + id +
                ", speed=" + speed +
                ", resistance=" + resistance +
                '}';
    }

    @Override
    public int hashCode()
    {
        return Integer.hashCode(id);
    }
}
