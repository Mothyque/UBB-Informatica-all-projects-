package com.ubb.repository.db;

import com.ubb.domain.Person;
import com.ubb.repository.paging.DBPagingRepository;
import com.ubb.repository.paging.PagingRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDBRepository extends DBPagingRepository<Integer, Person> implements PagingRepository<Integer, Person>
{
    public PersonDBRepository(String url, String username, String password)
    {
        super(url, username, password);
    }

    @Override
    protected String getFindAllPagedSQL()
    {
        return "SELECT * FROM persons ORDER BY id LIMIT ? OFFSET ?";
    }

    @Override
    protected String getFindAllSQL()
    {
        return "SELECT * FROM persons ORDER BY id";
    }

    @Override
    protected String getFindOneSQL()
    {
        return "SELECT * FROM persons WHERE id = ?";
    }

    @Override
    protected String getSaveSQL()
    {
        return "INSERT INTO persons (id, username, email, password, first_name, last_name, birth_date, occupation, empathy_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM persons WHERE id = ?";
    }

    @Override
    protected String getUpdateSQL()
    {
        return "UPDATE persons SET username = ?, email = ?, password = ?, first_name = ?, last_name = ?, birth_date = ?, occupation = ?, empathy_level = ? WHERE id = ?";
    }

    @Override
    protected String getSizeSQL()
    {
        return "SELECT COUNT(*) AS total FROM persons";
    }

    @Override
    protected Person extractEntity(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String birthDate = rs.getString("birth_date");
        String occupation = rs.getString("occupation");
        int empathyLevel = rs.getInt("empathy_level");
        return new Person(id, username, email, password, firstName, lastName, birthDate, occupation, empathyLevel);
    }

    @Override
    protected void setSaveParameters(PreparedStatement statement, Person entity) throws SQLException
    {
        statement.setInt(1, entity.getId());
        statement.setString(2,  entity.getUsername());
        statement.setString(3, entity.getEmail());
        statement.setString(4, entity.getPassword());
        statement.setString(5, entity.getFirstName());
        statement.setString(6, entity.getLastName());
        statement.setString(7, entity.getBirthDate());
        statement.setString(8, entity.getOccupation());
        statement.setInt(9, entity.getEmpathyLevel());
    }

    @Override
    protected void setFindOneDeleteParameters(PreparedStatement statement, Integer integer) throws SQLException
    {
        statement.setInt(1, integer);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Person entity) throws SQLException
    {
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setString(4, entity.getFirstName());
        statement.setString(5, entity.getLastName());
        statement.setString(6, entity.getBirthDate());
        statement.setString(7, entity.getOccupation());
        statement.setInt(8, entity.getEmpathyLevel());
        statement.setInt(9, entity.getId());
    }
}
