package repos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionManager {

    private static final String USERNAME = "remote-user";
    private static final String PASSWORD = "cOlT-1911";
    private static final String URL = "jdbc:mysql://netfixer.tk:3306/studenthostel";
    private Connection connection;

    private final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

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

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.info("Exception during connection closing");
        }
    }
}
