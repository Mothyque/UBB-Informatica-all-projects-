package domain;


public class MeciActiv
{
    private Long id;
    private Jucator jucator;
    private String[] tabla;
    private long startTime;
    private Status status;

    public MeciActiv(Long idJoc, Jucator jucator)
    {
        this.id = idJoc;
        this.jucator = jucator;
        this.tabla = new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " "};
        this.startTime = System.currentTimeMillis();
        this.status = Status.IN_PROGRESS;
    }

    public Long getId()
    {
        return id;
    }

    public Jucator getJucator()
    {
        return jucator;
    }

    public String[] getTabla()
    {
        return tabla;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
}
