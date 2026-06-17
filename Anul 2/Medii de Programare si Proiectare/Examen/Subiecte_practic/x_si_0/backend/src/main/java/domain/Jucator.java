package domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jucatori")
public class Jucator
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias", unique = true, nullable = false)
    private String alias;

    @OneToMany(mappedBy = "jucator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Joc> jocuri = new ArrayList<>();

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

    public List<Joc> getJocuri()
    {
        return jocuri;
    }

    public void addJoc(Joc joc)
    {
        jocuri.add(joc);
        joc.setJucator(this);
    }
}
