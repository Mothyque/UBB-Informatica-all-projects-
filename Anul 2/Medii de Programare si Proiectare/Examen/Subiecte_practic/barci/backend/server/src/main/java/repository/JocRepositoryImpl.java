package repository;

import domain.Joc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
            List<Joc> jocuri = session.createQuery("FROM Joc ORDER BY ghicite DESC, dataOra DESC", Joc.class).list();
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
    public List<Joc> getJocuriCuHit(String alias)
    {
        logger.info("Retrieving jocuri cu hit.");
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Joc> jocuri = session.createQuery("FROM Joc WHERE alias = :alias AND ghicite > 0 ORDER BY ghicite DESC, dataOra DESC", Joc.class)
                    .setParameter("alias", alias)
                    .list();
            logger.info("Retrieved {} jocuri cu hit.", jocuri.size());
            return jocuri;
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving jocuri cu hit: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }


}
