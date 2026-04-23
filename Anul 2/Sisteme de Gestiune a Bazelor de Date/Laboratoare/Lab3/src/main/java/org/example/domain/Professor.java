package org.example.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
@Table(name = "profesori")
public class Professor extends Entity<Integer> {

    @Column(name = "nume", nullable = false)
    private String name;

    @Column(name = "varsta", nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CollegeClass> collegeClasses = new ArrayList<>();

    public Professor(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Professor() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public List<CollegeClass> getClasses() { return collegeClasses; }
    public void setClasses(List<CollegeClass> collegeClasses) { this.collegeClasses = collegeClasses; }
}