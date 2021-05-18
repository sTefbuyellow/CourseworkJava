package service.implementations;

import model.Room;
import repos.interfaces.RoomRepository;
import service.interfaces.RoomService;
import java.util.Collection;

/**
 * A <code>RoomServiceImpl</code> class implements <code>RoomService</code> interface
 * and describes methods for interacting with the <code>RoomRepository</code> interface.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * @return <code>Collection</code> of all rooms
     */
    public Collection<Room> getAllRooms() {
        Collection<Room> rooms = roomRepository.getAll();
        for (Room room: rooms) {
            room.setStudents(roomRepository.getStudentsCount(room.getId()));
        }
        return rooms;
    }

    /**
     * @param id student id
     * @return student by id
     */
    @Override
    public Room getById(int id) {
        return roomRepository.getById(id);
    }

    /**
     * @param room <code>Room</code> class object
     * @return <code>true</code> if user created successful, <code>false</code> otherwise
     */
    @Override
    public boolean create(Room room) {
        return roomRepository.create(room);
    }

    /**
     * @param room <code>Room</code> class object
     * @param oldId id of room witch will be updated
     * @return <code>true</code> if user updated successful, <code>false</code> otherwise
     */
    @Override
    public boolean update(Room room, int oldId) {
        return roomRepository.update(room, oldId);
    }

    /**
     * @param id id of room witch will be deleted
     * @return <code>true</code> if user deleted successful, <code>false</code> otherwise
     */
    @Override
    public boolean delete(int id) {
        return roomRepository.delete(id);
    }
}
