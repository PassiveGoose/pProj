package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    private static final String CREATE = """
            CREATE TABLE `mydb`.`users` (
              `id` INT NOT NULL AUTO_INCREMENT,
              `name` VARCHAR(45) NOT NULL,
              `lastName` VARCHAR(45) NOT NULL,
              `age` INT NOT NULL,
              PRIMARY KEY (`id`),
              UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
            ENGINE = InnoDB
            DEFAULT CHARACTER SET = utf8;""";

    private static final String DROP = "DROP TABLE users;";

    private static final String CLEAN = "DELETE from users;";

    private static final String SELECT_ALL = "SELECT * from users;";

    private Connection connection = null;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, "Can't connect to DB");
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute(CREATE);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't create users table");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute(DROP);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't drop users table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()){
            String sqlStatement = "INSERT users(name, lastName, age) VALUES('" + name + "'," +
                    " '" + lastName + "', '" + String.valueOf(age) + "');";
            statement.executeUpdate(sqlStatement);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't save user");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()){
            String sqlStatement = CLEAN + " WHERE id=" + id;
            statement.executeUpdate(sqlStatement);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't remove user");
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                result.add(user);
            }
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't get users");
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(CLEAN);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't clean users table");
        }
    }
}
