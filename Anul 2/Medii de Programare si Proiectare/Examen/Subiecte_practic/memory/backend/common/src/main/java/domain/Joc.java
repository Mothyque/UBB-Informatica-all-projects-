package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "jocuri")
public class Joc
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias", nullable = false)
    private String alias;

    @Column(name = "puncte", nullable = false)
    private Integer puncte;

    @Column(name = "durataSecunde", nullable = false)
    private Long durataSecunde;

    @Column (name = "configuratie", nullable = false)
    private String configuratie;

    @Column (name = "pozitii", nullable = false)
    private String pozitii;

    public Joc() {}

    public Joc(String alias, Integer puncte, Long durataSecunde, String configuratie, String pozitii)
    {
        this.alias = alias;
        this.puncte = puncte;
        this.durataSecunde = durataSecunde;
        this.configuratie = configuratie;
        this.pozitii = pozitii;
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

    public Long getDurataSecunde()
    {
        return durataSecunde;
    }

    public void setDurataSecunde(Long durataSecunde)
    {
        this.durataSecunde = durataSecunde;
    }

    public String getConfiguratie()
    {
        return configuratie;
    }

    public void setConfiguratie(String configuratie)
    {
        this.configuratie = configuratie;
    }

    public String getPozitii()
    {
        return pozitii;
    }

    public void setPozitii(String pozitii)
    {
        this.pozitii = pozitii;
    }
}
