package domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@SQLDelete(sql = "UPDATE employee SET deleted = true, deleted_at = NOW(), deleted_by = 'admin_user' WHERE id = ? AND version = ?")
@Where(clause = "deleted = false")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Double salary;
    private String phone;

    @Version
    private Integer version;

    private boolean deleted =  false;
    private LocalDateTime deletedAt;
    private String deletedBy;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Double getSalary() { return salary; }
    public Department getDepartment() { return department; }
    public String getPhone() { return phone; }
    public Integer getVersion() { return version; }
    public boolean isDeleted() { return deleted; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public String getDeletedBy() { return deletedBy; }

    public void setId(Integer id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setSalary(Double salary) { this.salary = salary; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(Department department) { this.department = department; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setVersion(Integer version) { this.version = version; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public void setDeletedBy(String deleteBy) { this.deletedBy = deleteBy; }
}