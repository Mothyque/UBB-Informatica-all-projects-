package org.example.repository;

import org.example.domain.Entity;
import org.example.domain.exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clasă abstractă generică ce implementează operațiile CRUD de bază pe baza de date folosind JDBC.
 * Folosește design pattern-ul Template Method: logica comună de conectare și execuție a query-urilor
 * este aici, iar detaliile specifice (query-uri SQL, mapare parametri) sunt delegate claselor copil.
 *
 * @param <ID> Tipul de date al identificatorului entității (ex: Integer)
 * @param <E> Tipul entității (trebuie să extindă clasa de bază Entity)
 */
public abstract class DBRepository<ID, E extends Entity<ID>> implements Repository<ID, E>
{
    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructor pentru inițializarea parametrilor de conexiune la baza de date.
     */
    public DBRepository(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Creează și returnează o nouă conexiune către baza de date.
     * @return Connection obiect de conexiune JDBC
     * @throws SQLException în caz de eroare la conectare
     */
    protected Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Returnează toate entitățile din tabelul asociat.
     * Utilizează un bloc try-with-resources pentru a asigura închiderea corectă a conexiunii.
     * @return Iterable cu toate entitățile găsite
     */
    @Override
    public Iterable<E> findAll()
    {
        List<E> entities = new ArrayList<>();
        String sql = getFindAllSQL();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                E entity = extractEntity(rs);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding all entities", e);
        }
        return entities;
    }

    /**
     * Returnează o listă paginată de entități.
     * Folosește clauzele LIMIT și OFFSET din SQL pentru a extrage doar o porțiune din date.
     * @param pageNumber numărul paginii curente (începând cu 1)
     * @param pageSize numărul de elemente pe pagină
     * @return Iterable cu entitățile de pe pagina cerută
     */
    public Iterable<E> findAll(int pageNumber, int pageSize)
    {
        List<E> entities = new ArrayList<>();
        String sql = getFindAllSQL() + " LIMIT ? OFFSET ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, pageSize);
            statement.setInt(2, (pageNumber - 1) * pageSize); // Calculam OFFSET-ul (de unde începe citirea)

            try (ResultSet rs = statement.executeQuery())
            {
                while (rs.next())
                {
                    E entity = extractEntity(rs);
                    entities.add(entity);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return entities;
    }

    /**
     * Caută și returnează o entitate după ID-ul său.
     * Se folosește PreparedStatement cu parametri setați dinamic pentru a preveni SQL Injection.
     * @param id ID-ul entității căutate
     * @return Optional conținând entitatea (dacă este găsită) sau Optional.empty() altfel
     */
    @Override
    public Optional<E> findOne(ID id)
    {
        String sql = getFindOneSQL();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            setFindOneDeleteParameters(statement, id);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.next())
                {
                    return Optional.of(extractEntity(rs));
                }
            }
        }
        catch (SQLException e)
        {
            throw new PersistenceException("Error finding entity", e);
        }
        return Optional.empty();
    }

    /**
     * Salvează o nouă entitate în baza de date.
     * @param entity Entitatea care urmează să fie salvată
     * @return Optional cu entitatea proaspăt salvată
     */
    @Override
    public Optional<E> save(E entity)
    {
        String sql = getSaveSQL();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            setSaveParameters(statement, entity);
            statement.executeUpdate();
            return Optional.of(entity);
        }
        catch (SQLException e)
        {
            throw new PersistenceException("Error saving entity", e);
        }
    }

    /**
     * Șterge o entitate din baza de date pe baza ID-ului.
     * Se caută mai întâi entitatea pentru a o putea returna în caz de succes.
     * @param id ID-ul entității de șters
     * @return Optional conținând entitatea ștearsă sau empty dacă aceasta nu exista
     */
    @Override
    public Optional<E> delete(ID id)
    {
        Optional<E> entityToDelete = findOne(id);
        if (entityToDelete.isPresent())
        {
            String sql = getDeleteSQL();
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql))
            {
                setFindOneDeleteParameters(statement, id);
                statement.executeUpdate();
                return entityToDelete;
            }
            catch (SQLException e)
            {
                throw new PersistenceException("Error deleting entity", e);
            }
        }
        return Optional.empty();
    }

    /**
     * Actualizează datele unei entități existente în baza de date.
     * @param entity Entitatea cu datele noi (trebuie să conțină un ID valid)
     * @return Optional cu entitatea actualizată dacă operația a avut succes, altfel empty
     */
    @Override
    public Optional<E> update(E entity)
    {
        String sql = getUpdateSQL();
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
        {
            setUpdateParameters(statement, entity);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0)
            {
                return Optional.of(entity);
            }
        }
        catch (SQLException e)
        {
            throw new PersistenceException("Error updating entity", e);
        }
        return Optional.empty();
    }

    /**
     * Numără toate înregistrările din tabel.
     * @return numărul total de rânduri
     */
    @Override
    public int size()
    {
        String sql = getSizeSQL();
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery())
        {
            if (rs.next())
            {
                return rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            throw new PersistenceException("Error counting entities", e);
        }
        return 0;
    }

    // -----------------------------------------------------------------------------------------
    // Metode abstracte (Template Methods) ce trebuie implementate de clasele copil
    // pentru a oferi query-urile specifice și maparea parametrilor pentru fiecare entitate în parte
    // -----------------------------------------------------------------------------------------

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