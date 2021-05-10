package service.implementations;

import model.Room;
import model.Student;
import repos.interfaces.RoomRepository;
import repos.interfaces.StudentRepository;
import service.interfaces.RoomService;
import java.util.Collection;

public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;
    private StudentRepository studentRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
    }

    public Collection<Room> getAllRooms() {
        Collection<Room> rooms = roomRepository.getAll();
        for (Room room: rooms) {
            room.setStudents(roomRepository.getStudentsCount(room.getId()));
        }
        return rooms;
    }

    @Override
    public Collection<Student> getStudentsByRoom(int id) {
        return studentRepository.getAllByRoomId(id);
    }

    @Override
    public Room getById(int id) {
        return roomRepository.getById(id);
    }

    @Override
    public boolean create(Room room) {
        return roomRepository.create(room);
    }

    @Override
    public boolean update(Room room, int oldId) {
        return roomRepository.update(room, oldId);
    }

    @Override
    public boolean delete(int id) {
        return roomRepository.delete(id);
    }
}
