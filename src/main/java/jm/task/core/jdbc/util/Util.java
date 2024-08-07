package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "mborisov";
    private static final String PASSWORD = "PassiveGoose";

    public static Connection getConnection() throws SQLException {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException exc) {
            LOGGER.log(Level.WARNING, "Can't register driver");
        }

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
