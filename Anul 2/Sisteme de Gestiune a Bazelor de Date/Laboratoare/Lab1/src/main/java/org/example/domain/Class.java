package org.example.domain;

public class Class extends Entity<Integer>
{
    private String name;
    private Integer credits;
    private Integer professorId;

    public Class(String name, Integer credits, Integer professorId)
    {
        this.name = name;
        this.credits = credits;
        this.professorId = professorId;
    }
    public Class() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }
}
