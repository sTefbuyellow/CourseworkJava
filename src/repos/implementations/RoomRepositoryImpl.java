package repos.implementations;

import model.Room;
import repos.interfaces.RoomRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RoomRepositoryImpl implements RoomRepository {

    Connection connection;

    public RoomRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    private static final Logger logger = Logger.getLogger(RoomRepositoryImpl.class.getName());


    public ArrayList<Room> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.room");
            ArrayList<Room> rooms = getRooms(resultSet);
            statement.close();
            resultSet.close();
            return rooms;
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Error during room select", e);
            return null;
        }
    }

    public Room getById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.room where id = " + id);
            ArrayList<Room> rooms = getRooms(resultSet);
            statement.close();
            resultSet.close();
            if(rooms.size() == 0)
                return null;
            return rooms.get(0);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Error during room select", e);
            return null;
        }
    }

    public int getStudentsCount(int id) {
        try {
            int count = 0;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT COUNT(*) FROM studenthostel.student where room_id = " + id);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            statement.close();
            resultSet.close();
            return count;
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Error during students count select", e);
            return 0;
        }
    }

    @Override
    public void create(Room room) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into studenthostel.room " +
                    "(id, flore, beds_count) values ( ?, ?, ?)");
            statement.setInt(1, room.getId());
            statement.setInt(2, room.getFlore());
            statement.setInt(3, room.getBedsCount());
            boolean insertResult = statement.execute();
            statement.close();
        } catch (SQLException e) {
            logger.info("Exception during building create");
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
            logger.log(Level.SEVERE,"Error during room object create", e);
            return new ArrayList<>();
        }
    }

}
