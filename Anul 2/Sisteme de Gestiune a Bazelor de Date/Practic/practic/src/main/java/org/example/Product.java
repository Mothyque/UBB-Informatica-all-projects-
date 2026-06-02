package org.example;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "products")
public class Product extends Entity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "data_expirare")
    private String data_expirare;

    @Column(name = "pret")
    private Integer pret;

    @Column(name = "producator")
    private String producator;

    public Product() {}

    public Product(String nume, String data_expirare, Integer pret, String producator)
    {
        this.nume = nume;
        this.data_expirare = data_expirare;
        this.pret = pret;
        this.producator = producator;
    }

    public Integer getId()
    {
        return id;
    }

    public Integer getPret() {
        return pret;
    }

    public String getNume()
    {
        return nume;
    }

    public String getData_expirare()
    {
        return data_expirare;
    }

    public String getProducator()
    {
        return producator;
    }

    public void setData_expirare(String data_expirare) {
        this.data_expirare = data_expirare;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

    public void setProducator(String producator)
    {
        this.producator = producator;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}
