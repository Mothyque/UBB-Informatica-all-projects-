package org.example.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
@Table(name = "materii")
public class CollegeClass extends Entity<Integer> {

    @Column(name = "nume", nullable = false)
    private String name;

    @Column(name = "credite", nullable = false)
    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor")
    private Professor professor;

    @ManyToMany(mappedBy = "collegeClasses", fetch = FetchType.LAZY, targetEntity = Student.class)
    private List<Student> students = new ArrayList<>();

    public CollegeClass(String name, Integer credits, Professor professor) {
        this.name = name;
        this.credits = credits;
        this.professor = professor;
    }

    public CollegeClass() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}