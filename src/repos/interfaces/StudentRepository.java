package repos.interfaces;

import model.Student;

import java.util.Collection;

public interface StudentRepository {
    Collection<Student> getAll();

    Student getById(int id);

    Collection<Student> getAllByRoomId(int id);

    boolean create(Student student);

    boolean update(Student student, int oldId);

    boolean delete(int id);

}
