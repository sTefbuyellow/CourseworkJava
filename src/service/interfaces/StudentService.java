package service.interfaces;

import model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> getAllByRoomId(int id);
    Collection<Student> getAll();
    Student getById(int id);
}
