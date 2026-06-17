package domain;

public class MeciActiv
{
    private Long idJoc;
    private String alias;
    private String[] configuratie;
    private String[] descoperite;
    private int puncte;
    private int incercari;
    private long startTime;
    private String pozitii;

    public MeciActiv(Long idJoc, String alias, String[] configuratie)
    {
        this.idJoc = idJoc;
        this.alias = alias;
        this.configuratie = configuratie;
        this.descoperite = new String[configuratie.length];
        for (int i = 0; i < descoperite.length; i++)
        {
            descoperite[i] = "no";
        }
        this.puncte = 0;
        this.incercari = 0;
        this.startTime = System.currentTimeMillis();
        this.pozitii = "";
    }

    public Long getIdJoc()
    {
        return idJoc;
    }

    public String getAlias()
    {
        return alias;
    }

    public String[] getConfiguratie()
    {
        return configuratie;
    }

    public String[] getDescoperite()
    {
        return descoperite;
    }

    public int getPuncte()
    {
        return puncte;
    }

    public int getIncercari()
    {
        return incercari;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public String getPozitii()
    {
        return pozitii;
    }

    public void actualizeazaDescoperite(int pozitie)
    {
        if (pozitie >= 0 && pozitie < configuratie.length)
        {
            descoperite[pozitie] = configuratie[pozitie];
        }
    }

    public void adaugaPuncte(int puncte)
    {
        this.puncte += puncte;
    }

    public void adaugaIncercare(String pozitie1, String pozitie2)
    {
        this.pozitii += pozitie1 + "," + pozitie2 + ";";
        this.incercari++;
    }

}
