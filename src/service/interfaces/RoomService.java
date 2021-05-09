package service.interfaces;

import model.Room;
import model.Student;

import java.util.Collection;

public interface RoomService {

    Collection<Room> getAllRooms();
    Collection<Student> getStudentsByRoom(int id);
    Room getById(int id);
    void create(Room room);
}
