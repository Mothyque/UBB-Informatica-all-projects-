package domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jocuri")
public class Joc
{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias", nullable = false)
    private String alias;

    @Column(name = "nrIncercari", nullable = false)
    private Integer nrIncercari;

    @Column(name = "pozitiiPropuse", nullable = false)
    private String pozitiiPropuse;

    @Column(name = "textIndiciu", nullable = false)
    private String textIndiciu;

    @Column(name = "dataOra", nullable = false)
    private String dataOra;

    public Joc() {}

    public Joc(String alias, Integer nrIncercari, String pozitiiPropuse, String textIndiciu, String dataOra)
    {
        this.alias = alias;
        this.nrIncercari = nrIncercari;
        this.pozitiiPropuse = pozitiiPropuse;
        this.textIndiciu = textIndiciu;
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

    public Integer getNrIncercari()
    {
        return nrIncercari;
    }

    public void setNrIncercari(Integer nrIncercari)
    {
        this.nrIncercari = nrIncercari;
    }

    public String getPozitiiPropuse()
    {
        return pozitiiPropuse;
    }

    public String getTextIndiciu()
    {
        return textIndiciu;
    }

    public String getDataOra()
    {
        return dataOra;
    }
}
