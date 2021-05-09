package repos.interfaces;

import model.Student;

import java.sql.SQLException;
import java.util.Collection;

public interface StudentRepository {
    Collection<Student> getAll();
    Student getById(int id);
    Collection<Student> getAllByRoomId(int id);
    boolean delete(int id);
    Student update(Student student);
}
