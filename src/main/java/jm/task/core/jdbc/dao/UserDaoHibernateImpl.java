package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS users " +
                                         "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                                         "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                                         "age TINYINT NOT NULL)";


    private static final String DROP = "DROP TABLE IF EXISTS users";

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
            User user = session.load(User.class, id);
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
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);
            TypedQuery<User> allQuery = session.createQuery(all);
            users =  allQuery.getResultList();
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
