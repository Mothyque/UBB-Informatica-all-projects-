package repository;

import domain.Joc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public class JocRepositoryImpl implements JocRepository
{
    private static final Logger logger = LogManager.getLogger(JocRepositoryImpl.class);
    @Override
    public void save(Joc joc)
    {
        logger.info("Saving joc: {}", joc.getId());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(joc);
            transaction.commit();
            logger.info("joc saved successfully with alias: {}", joc.getId());
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error saving joc: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Joc getJoc(Long idJoc)
    {
        logger.info("Retrieving joc with id: {}", idJoc);
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            Joc joc = session.get(Joc.class, idJoc);
            if (joc != null)
            {
                logger.info("joc retrieved successfully with id: {}", idJoc);
                return joc;
            }
            else
            {
                logger.warn("joc not found with id: {}", idJoc);
            }
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving joc: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public List<Joc> getClasament()
    {
        	logger.info("Retrieving clasament.");
            try (Session session = HibernateUtils.getSessionFactory().openSession())
            {
                List<Joc> clasament = session.createQuery("FROM Joc j ORDER BY j.puncte, j.durataSecunde", Joc.class).list();
                logger.info("Clasament retrieved successfully.");
                return clasament;
            }
            catch (Exception ex)
            {
                logger.error("Error retrieving clasament: {}", ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
    }
}
