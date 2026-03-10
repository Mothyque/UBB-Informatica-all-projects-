package org.example.domain;

public class Professor extends Entity<Integer>
{
    private String name;
    private Integer age;

    public Professor(String name, Integer age)
    {
        this.name = name;
        this.age = age;
    }

    public Professor() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
