package service.implementations;

import model.Student;
import repos.interfaces.StudentRepository;
import service.interfaces.StudentService;

import java.util.Collection;


public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public Collection<Student> getAllByRoomId(int id) {
        return studentRepository.getAllByRoomId(id);
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.getAll();
    }

    @Override
    public Student getById(int id) {
        return studentRepository.getById(id);
    }

    @Override
    public boolean create(Student student) {
       return studentRepository.create(student);
    }

    @Override
    public boolean update(Student student, int oldId) {
        return studentRepository.update(student, oldId);
    }

    @Override
    public boolean delete(int studentId) {
        return studentRepository.delete(studentId);
    }
}
