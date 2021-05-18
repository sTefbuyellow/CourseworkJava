package service.implementations;

import model.Student;
import repos.interfaces.StudentRepository;
import service.interfaces.StudentService;

import java.util.Collection;

/**
 * The <code>StudentServiceImpl</code> class implements <code>StudentService</code> interface
 * and describes methods for interacting with the <code>StudentRepository</code> interface.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    /**
     * @param id room id
     * @return <code>Collection</code> of students in selected room
     */
    @Override
    public Collection<Student> getAllByRoomId(int id) {
        return studentRepository.getAllByRoomId(id);
    }

    /**
     * @return <code>Collection</code> of all students
     */
    @Override
    public Collection<Student> getAll() {
        return studentRepository.getAll();
    }

    /**
     * @param id student id
     * @return <code>Student</code> class object
     */
    @Override
    public Student getById(int id) {
        return studentRepository.getById(id);
    }

    /**
     * @param student <code>Student</code> class object
     * @return <code>true</code> if student created successful, <code>false</code> otherwise
     */
    @Override
    public boolean create(Student student) {
       return studentRepository.create(student);
    }

    /**
     * @param student <code>Student</code> class object
     * @param oldId id of student  witch will be updated
     * @return <code>true</code> if student updated successful, <code>false</code> otherwise
     */
    @Override
    public boolean update(Student student, int oldId) {
        return studentRepository.update(student, oldId);
    }

    /**
     * @param id of student witch will be deleted
     * @return <code>true</code> if student deleted successful, <code>false</code> otherwise
     */
    @Override
    public boolean delete(int id) {
        return studentRepository.delete(id);
    }
}
