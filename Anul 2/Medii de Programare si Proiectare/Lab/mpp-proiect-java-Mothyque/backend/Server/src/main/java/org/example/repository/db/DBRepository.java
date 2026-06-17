package org.example.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Entity;
import org.example.domain.exception.PersistenceException;
import org.example.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DBRepository<ID, E extends Entity<ID>> implements Repository<ID, E>
{
    private final String url;
    private static final Logger logger = LogManager.getLogger();
    protected Connection connection;

    public DBRepository(String url)
    {
        this.url = url;
        openConnection();
        logger.info("Initializing DBRepository with URL: " + url);
    }

    protected Connection getConnection() throws SQLException
    {
        if(connection == null || connection.isClosed())
        {
            logger.debug("Establishing new database connection");
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    @Override
    public Iterable<E> findAll()
    {
        logger.traceEntry("Finding all entities");
        List<E> entities = new ArrayList<>();
        String sql = getFindAllSQL();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery())
        {
            while (rs.next())
            {
                E entity = extractEntity(rs);
                entities.add(entity);
            }
        }
        catch (SQLException e)
        {
            logger.error("Error finding all entities", e);
            throw new PersistenceException("Error finding all entities", e);
        }
        logger.traceExit("Found " + entities.size() + " entities");
        return entities;
    }

    @Override
    public Optional<E> findOne(ID id)
    {
        logger.traceEntry("Finding entity with ID: " + id);
        String sql = getFindOneSQL();
        try (PreparedStatement statement = getConnection().prepareStatement(sql))
        {
            setFindOneDeleteParameters(statement, id);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    E entity = extractEntity(rs);
                    logger.traceExit("Entity found: " + entity);
                    return Optional.of(entity);
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("Error finding entity with ID: " + id, e);
            throw new PersistenceException("Error finding entity", e);
        }
        logger.traceExit("No entity found with ID: " + id);
        return Optional.empty();
    }

    @Override
    public Optional<E> save(E entity)
    {
        logger.traceEntry("Saving entity: " + entity);
        String sql = getSaveSQL();
        try (PreparedStatement statement = getConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS))
        {
            setSaveParameters(statement, entity);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    int newId = generatedKeys.getInt(1);
                    entity.setId((ID) Integer.valueOf(newId));
                }
            }
            logger.traceExit("Entity saved successfully: " + entity);
            return Optional.of(entity);
        }
        catch (SQLException e)
        {
            logger.error("Error saving entity: " + entity, e);
            throw new PersistenceException("Error saving entity", e);
        }
    }

    @Override
    public Optional<E> delete(ID id)
    {
        logger.traceEntry("Deleting entity with ID: " + id);
        Optional<E> entityToDelete = findOne(id);
        if (entityToDelete.isPresent())
        {
            String sql = getDeleteSQL();
            try (PreparedStatement statement = getConnection().prepareStatement(sql))
            {
                setFindOneDeleteParameters(statement, id);
                statement.executeUpdate();
                logger.traceExit("Entity deleted successfully: " + entityToDelete.get());
                return entityToDelete;
            }
            catch (SQLException e)
            {
                logger.error("Error deleting entity with ID: " + id, e);
                throw new PersistenceException("Error deleting entity", e);
            }
        }
        logger.traceExit("No entity found to delete with ID: " + id);
        return Optional.empty();
    }

    @Override
    public Optional<E> update(E entity)
    {
        logger.traceEntry("Updating entity: " + entity);
        String sql = getUpdateSQL();
        try (PreparedStatement statement = getConnection().prepareStatement(sql))
        {
            setUpdateParameters(statement, entity);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
            {
                logger.traceExit("Entity updated successfully: " + entity);
                return Optional.of(entity);
            }
        }
        catch (SQLException e)
        {
            logger.error("Error updating entity: " + entity, e);
            throw new PersistenceException("Error updating entity", e);
        }
        logger.traceExit("No entity updated for: " + entity);
        return Optional.empty();
    }

    @Override
    public int size()
    {
        logger.traceEntry("Getting size of entities");
        String sql = getSizeSQL();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery())
        {
            if (rs.next())
            {
                int count = rs.getInt(1);
                logger.traceExit("Size of entities: " + count);
                return count;
            }
        }
        catch (SQLException e)
        {
            logger.error("Error counting entities", e);
            throw new PersistenceException("Error counting entities", e);
        }
        logger.traceExit("Size of entities: 0");
        return 0;
    }

    public void openConnection()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            logger.info("Database connection established successfully");
        }
        catch (SQLException e)
        {
            logger.error("Error establishing database connection", e);
            throw new PersistenceException("Error establishing database connection", e);
        }
    }

    public void closeConnection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
                logger.info("Database connection closed successfully");
            }
        }
        catch (SQLException e)
        {
            logger.error("Error closing database connection", e);
        }
    }

    protected abstract String getFindAllSQL();
    protected abstract String getFindOneSQL();
    protected abstract String getSaveSQL();
    protected abstract String getDeleteSQL();
    protected abstract String getUpdateSQL();
    protected abstract String getSizeSQL();

    protected abstract E extractEntity(ResultSet rs) throws SQLException;
    protected abstract void setSaveParameters(PreparedStatement statement, E entity) throws SQLException;
    protected abstract void setFindOneDeleteParameters(PreparedStatement statement, ID id) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement statement, E entity) throws SQLException;
}