package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    private static final String CREATE = "CREATE TABLE `mydb`.`" + JDBCUtil.getTableName() + "` +  (\n" +
                                         "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                                         "`name` VARCHAR(45) NOT NULL,\n" +
                                         "`lastName` VARCHAR(45) NOT NULL,\n" +
                                         "`age` INT NOT NULL,\n" +
                                         "PRIMARY KEY (`id`),\n" +
                                         "UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
                                         "ENGINE = InnoDB\n" +
                                         "DEFAULT CHARACTER SET = utf8;";

    private static final String DROP = "DROP TABLE " + JDBCUtil.getTableName() + ";";

    private static final String CLEAN = "DELETE from " + JDBCUtil.getTableName() + ";";

    private static final String SELECT_ALL = "SELECT * from " + JDBCUtil.getTableName() + ";";

    public void createUsersTable() {
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement()){
            statement.execute(CREATE);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't create users table", exception);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement()){
            statement.execute(DROP);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't drop users table", exception);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement()){
            String sqlStatement = "INSERT " + JDBCUtil.getTableName()
                    + "(name, lastName, age) VALUES('" + name + "'," +
                    " '" + lastName + "', '" + String.valueOf(age) + "');";
            statement.executeUpdate(sqlStatement);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't save user", exception);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement()){
            String sqlStatement = "DELETE from " + JDBCUtil.getTableName() + " WHERE id=" + id + ";";
            statement.executeUpdate(sqlStatement);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't remove user", exception);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement()){
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
            LOGGER.log(Level.WARNING, "Can't get users", exception);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(CLEAN);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Can't clean users table", exception);
        }
    }
}
