package org.example.domain;

public class Student extends Entity<Integer>
{
    private String nume;
    private Double medie;

    public Student(String nume, Double medie)
    {
        this.nume = nume;
        this.medie = medie;
    }

    public Student() {}

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public Double getMedie() { return medie; }
    public void setMedie(Double medie) { this.medie = medie; }

    @Override
    public String toString()
    {
        return nume;
    }
}