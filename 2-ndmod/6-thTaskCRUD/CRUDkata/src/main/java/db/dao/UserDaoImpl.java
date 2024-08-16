package db.dao;

import models.User;
import org.springframework.stereotype.Repository;
import web.config.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public void saveUser(String name, String lastName, int age) {
        User user = new User(name, lastName, age);
        EntityManager entityManager = DatabaseConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            LOGGER.log(Level.INFO, user + " saved");
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't save user", exception);
        }
    }

    @Override
    public void removeUserById(int id) {
        EntityManager entityManager = DatabaseConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
            entityManager.getTransaction().commit();
            LOGGER.log(Level.INFO, user + " removed");
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't remove user", exception);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        EntityManager entityManager = DatabaseConfig.getEntityManager();
        try {
            CriteriaQuery<User> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            users =  entityManager.createQuery(criteriaQuery).getResultList();
            LOGGER.log(Level.INFO, "Users list was provided");
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't save user", exception);
        }
        return users;
    }

    @Override
    public void updateUserById(int id, String name, String surname, int age) {
        EntityManager entityManager = DatabaseConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            entityManager.detach(user);

            user.setName(name);
            user.setSurname(surname);
            user.setAge(age);

            entityManager.merge(user);
            entityManager.getTransaction().commit();
            LOGGER.log(Level.INFO, user + " updated");
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't update user", exception);
        }
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        EntityManager entityManager = DatabaseConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't get user", exception);
        }
        return user;
    }
}

