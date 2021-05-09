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
}
