package domain;

import jakarta.persistence.*;

@Entity
@Table(name = "jucatori")
public class Jucator
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias", unique = true, nullable = false)
    private String alias;

    public Jucator() {}

    public Jucator(String alias)
    {
        this.alias = alias;
    }

    public Long getId()
    {
        return id;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }
}
