package repos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * A <code>ConnectionManager</code> object creates connection between
 * program and MySql database using mysql-connector driver
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class ConnectionManager {

    /**
     * database connection credentials
     */
    private static final String USERNAME = "remote-user";
    private static final String PASSWORD = "cOlT-1911";
    private static final String URL = "jdbc:mysql://netfixer.tk:3306/studenthostel";
    private Connection connection;

    private final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

    /**
     * Creates connection with database
     */
    public ConnectionManager(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected");
        }catch (Exception e){
            connection = null;
            logger.info("Exception during connection creating");
        }
    }

    /**
     * @return a <code>Connection</code> class object
     */
    public Connection getConnection(){
        return connection;
    }

    /**
     * closes connection
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.info("Exception during connection closing");
        }
    }
}
