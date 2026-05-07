package domain;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getSalary() { return salary; }
    public Department getDepartment() { return department; }

    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setSalary(Integer salary) { this.salary = salary; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(Department department) { this.department = department; }
}