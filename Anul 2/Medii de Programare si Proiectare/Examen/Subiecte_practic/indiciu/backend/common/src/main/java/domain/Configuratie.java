package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "configuratii")
public class Configuratie
{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "linie", nullable = false)
    private Integer linie;

    @Column(name = "coloana", nullable = false)
    private Integer coloana;

    @Column(name = "text", nullable = false)
    private String text;

    public Configuratie() {}

    public Configuratie(Integer linie, Integer coloana, String text)
    {
        this.linie = linie;
        this.coloana = coloana;
        this.text = text;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getLinie()
    {
        return linie;
    }

    public void setLinie(Integer linie)
    {
        this.linie = linie;
    }

    public Integer getColoana()
    {
        return coloana;
    }

    public void setColoana(Integer coloana)
    {
        this.coloana = coloana;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
