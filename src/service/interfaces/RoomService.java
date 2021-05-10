package service.interfaces;

import model.Room;
import model.Student;

import java.util.Collection;

public interface RoomService {

    Collection<Room> getAllRooms();
    Collection<Student> getStudentsByRoom(int id);
    Room getById(int id);
    boolean create(Room room);
    boolean update(Room room, int oldId);
    boolean delete(int id);
}
