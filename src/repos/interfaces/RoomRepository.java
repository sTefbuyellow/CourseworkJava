package repos.interfaces;

import model.Room;

import java.util.Collection;

/**
 * A <code>RoomRepository</code> interface describes
 * methods for interacting with the room table.
 *
 * @author Kirichuk K.N.
 * @version 0.01 04.03.2021
 */
public interface RoomRepository {

    /**
     * @return <code>Collection</code> of all rooms
     */
    Collection<Room> getAll();

    /**
     * @param id student id
     * @return student by id
     */
    Room getById(int id);

    /**
     * @param id room id
     * @return count of students in selected room
     */
    int getStudentsCount(int id);

    /**
     * @param room <code>Room</code> class object
     * @return <code>true</code> if user created successful, <code>false</code> otherwise
     */
    boolean create(Room room);

    /**
     * @param room <code>Room</code> class object
     * @param oldId id of room witch will be updated
     * @return <code>true</code> if user updated successful, <code>false</code> otherwise
     */
    boolean update(Room room, int oldId);

    /**
     * @param roomId id of room witch will be deleted
     * @return <code>true</code> if user deleted successful, <code>false</code> otherwise
     */
    boolean delete(int roomId);
}
