package repos.interfaces;

import model.Room;

import java.util.Collection;

public interface RoomRepository {
    Collection<Room> getAll();
    Room getById(int id);
    int getStudentsCount(int id);
    void create(Room room);
}
