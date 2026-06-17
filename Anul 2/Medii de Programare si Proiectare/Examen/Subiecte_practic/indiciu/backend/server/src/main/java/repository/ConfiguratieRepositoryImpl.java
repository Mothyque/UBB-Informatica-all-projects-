package repository;

import domain.Configuratie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfiguratieRepositoryImpl implements ConfiguratieRepository
{
    private final static Logger logger = LogManager.getLogger(ConfiguratieRepositoryImpl.class);

    @Override
    public List<Configuratie> findAll()
    {
        logger.info("Fetching all configurations from the repository");
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            List<Configuratie> configuratii = session.createQuery("FROM Configuratie ").list();
            return configuratii;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Configuratie> save(Configuratie configuratie)
    {
        logger.info("Fetching configurations from the repository");
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession())
        {
            transaction = session.beginTransaction();
            session.persist(configuratie);
            transaction.commit();
            return Optional.of(configuratie);
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