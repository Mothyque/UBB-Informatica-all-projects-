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
        logger.info("Saving Joc with id: {}", joc.getId());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(joc);
            transaction.commit();
            logger.info("Joc saved successfully with id: {}", joc.getId());
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error saving Joc: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Joc> getAllGamesSorted()
    {
        logger.info("Retrieving all games sorted by date.");
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Joc> jocuri = session.createQuery("FROM Joc j JOIN FETCH j.jucator ORDER by j.puncte DESC, j.durataSecunde", Joc.class).list();
            logger.info("Retrieved {} games.", jocuri.size());
            return jocuri;
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving games: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Joc> getWonGamesByPlayerAlias(String alias)
    {
        logger.info("Retrieving won games for player with alias: {}", alias);
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Joc> jocuri = session.createQuery("FROM Joc j JOIN FETCH j.jucator WHERE j.jucator.alias = :alias AND j.puncte = 10 ORDER BY j.durataSecunde", Joc.class)
                    .setParameter("alias", alias)
                    .list();
            logger.info("Retrieved {} won games for player with alias: {}", jocuri.size(), alias);
            return jocuri;
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving won games: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
