package repository;

import domain.Jucator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.Optional;

public class    JucatorRepositoryImpl implements JucatorRepository
{
    private static final Logger logger = LogManager.getLogger(JucatorRepositoryImpl.class);

    @Override
    public void save(Jucator jucator)
    {
        logger.info("Saving Jucator with alias: {}", jucator.getAlias());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(jucator);
            transaction.commit();
            logger.info("Jucator saved successfully with id: {}", jucator.getId());
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error saving Jucator: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Jucator> findByAlias(String alias)
    {
        logger.info("Finding Jucator by alias: {}", alias);
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            Jucator jucator = session.createQuery("FROM Jucator WHERE alias = :alias", Jucator.class)
                    .setParameter("alias", alias)
                    .uniqueResult();
            if (jucator != null)
            {
                logger.info("Jucator found with id: {}", jucator.getId());
                return Optional.of(jucator);
            }
            else
            {
                logger.info("No Jucator found with alias: {}", alias);
                return Optional.empty();
            }
        }
        catch (Exception ex)
        {
            logger.error("Error finding Jucator by alias: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
