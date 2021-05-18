package repos.interfaces;

import model.Student;

import java.util.Collection;

/**
 * A <code>StudentRepository</code> interface describes
 * methods methods for interacting with the student table.
 *
 * @author Kirichuk K.N.
 * @version 0.01 04.03.2021
 */
public interface StudentRepository {

    /**
     * @return <code>Collection</code> of all students
     */
    Collection<Student> getAll();

    /**
     * @param id student id
     * @return <code>Student</code> class object
     */
    Student getById(int id);

    /**
     * @param id room id
     * @return <code>Collection</code> of students in selected room
     */
    Collection<Student> getAllByRoomId(int id);

    /**
     * @param student <code>Student</code> class object
     * @return <code>true</code> if student created successful, <code>false</code> otherwise
     */
    boolean create(Student student);

    /**
     * @param student <code>Student</code> class object
     * @param oldId id of student  witch will be updated
     * @return <code>true</code> if student updated successful, <code>false</code> otherwise
     */
    boolean update(Student student, int oldId);

    /**
     * @param id of student witch will be deleted
     * @return <code>true</code> if student deleted successful, <code>false</code> otherwise
     */
    boolean delete(int id);

}
