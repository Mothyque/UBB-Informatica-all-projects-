package domain;
import jakarta.persistence.*;

@Entity
@Table(name = "jocuri")
public class Joc
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jucator_id", nullable = false)
    private Jucator jucator;

    @Column(nullable = false)
    private Integer puncte;

    @Column(name = "durataSecunde", nullable = false)
    private Integer durataSecunde;

    public Joc() {}
    public Joc(Jucator jucator, Integer puncte, Integer durataSecunde)
    {
        this.jucator = jucator;
        this.puncte = puncte;
        this.durataSecunde = durataSecunde;
    }

    public Long getId()
    {
        return id;
    }

    public Jucator getJucator()
    {
        return jucator;
    }

    public void setJucator(Jucator jucator)
    {
        this.jucator = jucator;
    }

    public Integer getPuncte()
    {
        return puncte;
    }

    public void setPuncte(Integer puncte)
    {
        this.puncte = puncte;
    }

    public Integer getDurataSecunde()
    {
        return durataSecunde;
    }

    public void setDurataSecunde(Integer durataSecunde)
    {
        this.durataSecunde = durataSecunde;
    }

}
