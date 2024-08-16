package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS User " +
                                         "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                                         "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                                         "age TINYINT NOT NULL)";


    private static final String DROP = "DROP TABLE IF EXISTS User";

    @Override
    public void createUsersTable() {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(CREATE).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            LOGGER.log(Level.INFO, "Table created");
        } catch (HibernateException exception) {
            LOGGER.log(Level.WARNING, "Can't create users table", exception);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(DROP).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            LOGGER.log(Level.INFO, "Table dropped");
        } catch (HibernateException exception) {
            LOGGER.log(Level.WARNING, "Can't drop users table", exception);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            LOGGER.log(Level.INFO, user + " saved");
        } catch (HibernateException exception) {
            LOGGER.log(Level.WARNING, "Can't save user", exception);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            LOGGER.log(Level.INFO, user + " removed");
        } catch (HibernateException exception) {
            LOGGER.log(Level.WARNING, "Can't remove user", exception);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            users =  session.createQuery(criteriaQuery).getResultList();
            LOGGER.log(Level.INFO, "Users list was provided");
        } catch (HibernateException exception) {
            LOGGER.log(Level.WARNING, "Can't save user", exception);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            LOGGER.log(Level.INFO, "Table was cleaned");
        } catch (HibernateException exception) {
            LOGGER.log(Level.WARNING, "Can't clean table", exception);
        }
    }
}
