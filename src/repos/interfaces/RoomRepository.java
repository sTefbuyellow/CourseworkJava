package repos.interfaces;

import model.Room;

import java.util.Collection;

public interface RoomRepository {
    Collection<Room> getAll();
    Room getById(int id);
    int getStudentsCount(int id);
    boolean create(Room room);
    boolean update(Room room, int oldId);
    boolean delete(int roomId);
}
