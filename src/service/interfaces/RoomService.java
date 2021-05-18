package service.interfaces;

import model.Room;

import java.util.Collection;

/**
 * A <code>RoomService</code> interface describes
 * methods for interacting with the <code>RoomService</code> interface.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public interface RoomService {

    /**
     * @return <code>Collection</code> of all rooms
     */
    Collection<Room> getAllRooms();

    /**
     * @param id student id
     * @return student by id
     */
    Room getById(int id);

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
     * @param id id of room witch will be deleted
     * @return <code>true</code> if user deleted successful, <code>false</code> otherwise
     */
    boolean delete(int id);
}
