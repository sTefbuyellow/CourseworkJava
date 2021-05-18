package repos.implementations;

import model.Room;
import repos.interfaces.RoomRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A <code>RoomRepositoryImpl</code> class implements
 * <code>RoomRepository</code> interface and describes
 * methods for interacting with the room table.
 *
 * @author Kirichuk K.N.
 * @version 0.01 04.03.2021
 */
public class RoomRepositoryImpl implements RoomRepository {

    Connection connection;

    public RoomRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * <code>Logger</code> class object to log runtime info
     */
    private static final Logger logger = Logger.getLogger(RoomRepositoryImpl.class.getName());

    /**
     * @return <code>Collection</code> of all rooms
     */
    @Override
    public ArrayList<Room> getAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.room");
            ArrayList<Room> rooms = getRooms(resultSet);
            statement.close();
            resultSet.close();
            return rooms;
        } catch (SQLException e) {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE, "Error during room select", e);
            return null;
        }
    }

    /**
     * @param id student id
     * @return student by id
     */
    @Override
    public Room getById(int id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.room where id = " + id);
            ArrayList<Room> rooms = getRooms(resultSet);
            statement.close();
            resultSet.close();
            if (rooms.size() == 0)
                return null;
            return rooms.get(0);
        } catch (SQLException e) {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE, "Error during room select", e);
            return null;
        }
    }

    /**
     * @param id room id
     * @return count of students in selected room
     */
    @Override
    public int getStudentsCount(int id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            int count = 0;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT COUNT(*) FROM studenthostel.student where room_id = " + id);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            statement.close();
            resultSet.close();
            return count;
        } catch (SQLException e) {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE, "Error during students count select", e);
            return 0;
        }
    }

    /**
     * @param room <code>Room</code> class object
     * @return <code>true</code> if user created successful, <code>false</code> otherwise
     */
    @Override
    public boolean create(Room room) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO studenthostel.room " +
                    "(id, flore, beds_count) values ( ?, ?, ?)");
            statement.setInt(1, room.getId());
            statement.setInt(2, room.getFlore());
            statement.setInt(3, room.getBedsCount());
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement close");
            }
            logger.info("Exception during room create");
            return false;
        }
    }

    /**
     * @param room <code>Room</code> class object
     * @param oldId id of room
     * @return <code>true</code> if user updated successful, <code>false</code> otherwise
     */
    @Override
    public boolean update(Room room, int oldId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE studenthostel.room " +
                    "SET id = ?, flore = ?, beds_count = ? WHERE id = ?");
            statement.setInt(1, room.getId());
            statement.setInt(2, room.getFlore());
            statement.setInt(3, room.getBedsCount());
            statement.setInt(4, oldId);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement close");
            }
            logger.info("Exception during room update");
            return false;
        }
    }

    /**
     * @param roomId id of room
     * @return <code>true</code> if user deleted successful, <code>false</code> otherwise
     */
    @Override
    public boolean delete(int roomId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM studenthostel.room WHERE id = ?");
            statement.setInt(1, roomId);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement close");
            }
            logger.info("Exception during room delete");
            return false;
        }
    }

    private ArrayList<Room> getRooms(ResultSet resultSet) {
        try {
            ArrayList<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int flore = resultSet.getInt("flore");
                int bedsCount = resultSet.getInt("beds_count");
                rooms.add(new Room(id, flore, bedsCount));
            }
            return rooms;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during room object create", e);
            return new ArrayList<>();
        }
    }

}
