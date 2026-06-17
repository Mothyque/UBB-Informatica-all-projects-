package repository;

import domain.Configuratie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.Optional;

public class ConfiguratieRepositoryImpl implements ConfiguratieRepository
{
    private static final Logger logger = LogManager.getLogger(ConfiguratieRepositoryImpl.class);
    @Override
    public void save(Configuratie configuratie)
    {
        logger.info("Saving configuratie: {}", configuratie.getText());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(configuratie);
            transaction.commit();
            logger.info("Configuratie saved successfully with alias: {}", configuratie.getText());
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error saving configuratie: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean exists(String text)
    {
        logger.info("Checking if configuratie exists with text: {}", text);
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            Long count = session.createQuery("SELECT COUNT(c) FROM Configuratie c WHERE c.text = :text", Long.class)
                    .setParameter("text", text)
                    .uniqueResult();
            boolean exists = count != null && count > 0;
            logger.info("Configuratie with text '{}' exists: {}", text, exists);
            return exists;
        }
        catch (Exception ex)
        {
            logger.error("Error checking if configuratie exists: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Configuratie configuratie)
    {
        logger.info("Updating configuratie with id: {}", configuratie.getId());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.merge(configuratie);
            transaction.commit();
            logger.info("Configuratie updated successfully with id: {}", configuratie.getId());
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error updating configuratie: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Configuratie> find(Long id)
    {
        logger.info("Finding configuratie with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            Configuratie configuratie = session.get(Configuratie.class, id);
            if (configuratie != null) {
                logger.info("Configuratie found with id: {}", id);
                return Optional.of(configuratie);
            } else {
                logger.info("No Configuratie found with id: {}", id);
                return Optional.empty();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error finding configuratie: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }


}
