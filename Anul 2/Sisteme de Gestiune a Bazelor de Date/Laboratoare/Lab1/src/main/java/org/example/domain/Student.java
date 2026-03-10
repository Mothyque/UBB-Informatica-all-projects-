package org.example.domain;

/**
 * Clasa care reprezintă entitatea Student în aplicație.
 * Extinde clasa generică de bază Entity, având un identificator unic (ID) de tip Integer.
 */
public class Student extends Entity<Integer>
{
    private String name;
    private Integer age;

    /**
     * Constructor cu parametri pentru crearea și inițializarea unui nou Student.
     * @param name Numele complet al studentului.
     * @param age Vârsta studentului.
     */
    public Student(String name, Integer age)
    {
        this.name = name;
        this.age = age;
    }

    /**
     * Constructor implicit (fără parametri).
     * Este o bună practică să fie inclus pentru instanțieri dinamice sau pentru utilitare.
     */
    public Student() {}

    /**
     * Preia numele studentului.
     * @return Numele studentului sub formă de String.
     */
    public String getName() { return name; }

    /**
     * Actualizează numele studentului.
     * @param name Noul nume care va fi setat.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Preia vârsta studentului.
     * @return Vârsta studentului sub formă de Integer.
     */
    public Integer getAge() { return age; }

    /**
     * Actualizează vârsta studentului.
     * @param age Noua vârstă care va fi setată.
     */
    public void setAge(Integer age) { this.age = age; }
}