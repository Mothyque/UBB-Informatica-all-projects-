package repository;

import domain.Jucator;
import jakarta.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import utils.HibernateUtils;

public class JocRepositoryImpl implements JocRepository
{
    private final Logger logger = LogManager.getLogger(JocRepositoryImpl.class);

}
