package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "jocuri")
public class Joc
{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias", nullable = false)
    private String alias;

    @Column(name = "puncte", nullable = false)
    private Integer puncte;

    @Column(name = "pozitii", nullable = false)
    private String pozitii;

    @Column(name = "ghicite", nullable = false)
    private Integer ghicite;

    @Column(name = "pozitiiBarca", nullable = false)
    private String pozitiiBarca;

    @Column(name = "dataOra", nullable = false)
    private String dataOra;

    public Joc() {}

    public Joc(String alias, Integer puncte, String pozitii, Integer ghicite, String pozitiiBarca, String dataOra)
    {
        this.alias = alias;
        this.puncte = puncte;
        this.pozitii = pozitii;
        this.ghicite = ghicite;
        this.pozitiiBarca = pozitiiBarca;
        this.dataOra = dataOra;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public Integer getPuncte()
    {
        return puncte;
    }

    public void setPuncte(Integer puncte)
    {
        this.puncte = puncte;
    }

    public String getPozitii()
    {
        return pozitii;
    }

    public void setPozitii(String pozitii)
    {
        this.pozitii = pozitii;
    }

    public Integer getGhicite()
    {
        return ghicite;
    }

    public void setGhicite(Integer ghicite)
    {
        this.ghicite = ghicite;
    }

    public String getPozitiiBarca()
    {
        return pozitiiBarca;
    }

    public void setPozitiiBarca(String pozitiiBarca)
    {
        this.pozitiiBarca = pozitiiBarca;
    }

    public String getDataOra()
    {
        return dataOra;
    }

    public void setDataOra(String dataOra)
    {
        this.dataOra = dataOra;
    }

}
