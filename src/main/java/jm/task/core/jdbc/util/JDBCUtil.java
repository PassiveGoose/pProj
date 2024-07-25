package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCUtil {

    private static final Logger LOGGER = Logger.getLogger(JDBCUtil.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "mborisov";
    private static final String PASSWORD = "PassiveGoose";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Driver driver = new Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException exc) {
                LOGGER.log(Level.WARNING, "Can't open connection");
            }
        }

        return connection;
    }
}
