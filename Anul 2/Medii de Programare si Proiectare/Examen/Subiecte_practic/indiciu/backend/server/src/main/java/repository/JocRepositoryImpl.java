package repository;

import domain.Joc;
import domain.Jucator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;
import java.util.Optional;

public class JocRepositoryImpl implements JocRepository
{
    private final Logger logger = LogManager.getLogger(JocRepositoryImpl.class);

    @Override
    public Optional<Joc> save(Joc joc)
    {
        logger.info("Saving joc for player: {}", joc.getAlias());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(joc);
            transaction.commit();
            logger.info("Joc saved successfully for player: {}", joc.getAlias());
            return Optional.of(joc);
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error saving joc for player {}: {}", joc.getAlias(), ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    @Override
    public List<Joc> getClasament()
    {
        logger.info("Retrieving clasament.");
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Joc> jocuri = session.createQuery("FROM Joc ORDER BY nrIncercari ASC, dataOra DESC", Joc.class).list();
            logger.info("Retrieved {} jocuri for clasament.", jocuri.size());
            return jocuri;
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving clasament: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Joc> getJocuri(String alias)
    {
        logger.info("Retrieving joc for player: {}", alias);
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Joc> jocuri = session.createQuery("FROM Joc WHERE alias = :alias", Joc.class)
                    .setParameter("alias", alias)
                    .list();
            if (!jocuri.isEmpty())
            {
                logger.info("Joc retrieved successfully for player: {}", alias);
                return jocuri;
            }
            else
            {
                logger.info("Joc could not be found for player: {}", alias);
            }
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving joc for player: {}", alias, ex);
        }
        return null;
    }
}
