package domain;

import jakarta.persistence.*;

@Entity
@Table (name = "configuratii")
public class Configuratie
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "text", nullable = false, unique = true)
    private String text;

    public Configuratie () {}

    public Configuratie (String text)
    {
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

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
