package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "barci")
public class Barca
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pozitie", nullable = false)
    private String pozitie;

    public Barca() {}

    public Barca (String pozitie)
    {
        this.pozitie = pozitie;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPozitie()
    {
        return pozitie;
    }

    public void setPozitie(String pozitie)
    {
        this.pozitie = pozitie;
    }
}
