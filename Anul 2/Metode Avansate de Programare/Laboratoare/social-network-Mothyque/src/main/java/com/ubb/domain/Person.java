package com.ubb.domain;

public class Person extends User
{
    private String lastName;
    private String firstName;
    private String birthDate;
    private String occupation;
    private Integer empathyLevel;

    public Person(Integer id, String username, String email, String password, String lastName, String firstName, String birthDate, String occupation, Integer empathyLevel)
    {
        super(id, username, email, password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.empathyLevel = empathyLevel;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getOccupation()
    {
        return occupation;
    }

    public int getEmpathyLevel()
    {
        return empathyLevel;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public void setOccupation(String occupation)
    {
        this.occupation = occupation;
    }

    public void setEmpathyLevel(Integer empathyLevel)
    {
        this.empathyLevel = empathyLevel;
    }

    @Override
    public String toString()
    {
        return "Persoana{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", nume='" + lastName + '\'' +
                ", prenume='" + firstName + '\'' +
                ", dataNasterii='" + birthDate + '\'' +
                ", ocupatie='" + occupation + '\'' +
                ", nivelEmpatie=" + empathyLevel +
                '}';
    }
}
