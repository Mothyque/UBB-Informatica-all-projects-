package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils
{
    private static final Logger logger = LogManager.getLogger(HibernateUtils.class);
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null)
        {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            try
            {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                logger.info("SessionFactory created successfully.");
            }
            catch (Exception ex)
            {
                logger.error("SessionFactory creation failed.", ex);
                StandardServiceRegistryBuilder.destroy(registry);
                throw new RuntimeException(ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown()
    {
        if (sessionFactory != null)
        {
            sessionFactory.close();
            logger.info("SessionFactory closed.");
        }
    }
}
