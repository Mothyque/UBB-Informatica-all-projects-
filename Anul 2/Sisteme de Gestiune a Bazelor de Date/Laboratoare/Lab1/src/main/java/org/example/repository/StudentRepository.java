package org.example.repository;

import org.example.domain.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository extends DBRepository<Integer, Student>
{
    public StudentRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM studenti";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM studenti WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO studenti (nume, varsta) VALUES (?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM studenti WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE studenti SET nume = ?, varsta = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM studenti";
    }

    @Override
    protected Student extractEntity(ResultSet rs) throws SQLException
    {
        String nume = rs.getString("nume");
        Integer varsta = rs.getInt("varsta");
        Student student = new Student(nume, varsta);
        student.setId(rs.getInt("id"));
        return student;
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Student entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getAge());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Student entity) throws SQLException
    {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getAge());
        statement.setInt(3, entity.getId());
    }
}
