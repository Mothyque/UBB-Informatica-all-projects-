package repository;

import domain.Cuvant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public class CuvantRepositoryImpl implements CuvantRepository
{
    private static final Logger logger = LogManager.getLogger(CuvantRepositoryImpl.class);
    @Override
    public void save(Cuvant cuvant)
    {
        logger.info("Saving cuvant: {}", cuvant.getText());
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(cuvant);
            transaction.commit();
            logger.info("Cuvant saved successfully with alias: {}", cuvant.getText());
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error.");
            }
            logger.error("Error saving cuvant: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Cuvant> findAll()
    {
        logger.info("Retrieving all cuvinte.");
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Cuvant> cuvinte = session.createQuery("FROM Cuvant", Cuvant.class).list();
            logger.info("Retrieved {} cuvinte.", cuvinte.size());
            return cuvinte;
        }
        catch (Exception ex)
        {
            logger.error("Error retrieving cuvinte: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
