package domain;

import jakarta.persistence.*;

@Entity
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setSalary(Integer salary)
    {
        this.salary = salary;
    }
    public void setName(String name) { this.name = name; }
    public void setDepartment(Department department) { this.department = department; }
}