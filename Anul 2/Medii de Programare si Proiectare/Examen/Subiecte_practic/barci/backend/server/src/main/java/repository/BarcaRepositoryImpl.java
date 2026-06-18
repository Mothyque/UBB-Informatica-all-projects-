package repository;

import domain.Barca;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;
import java.util.Optional;

public class BarcaRepositoryImpl implements BarcaRepository
{
    private final static Logger logger = LogManager.getLogger(BarcaRepositoryImpl.class);

    @Override
    public List<Barca> findAll()
    {
        logger.info("Fetching all boats from the repository");
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Barca> barca = session.createQuery("FROM Barca").list();
            return barca;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Barca> save(Barca barca)
    {
        logger.info("Fetching boats from the repository");
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(barca);
            transaction.commit();
            return Optional.of(barca);
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
