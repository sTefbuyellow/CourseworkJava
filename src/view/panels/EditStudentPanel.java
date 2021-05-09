package view.panels;

import model.Student;
import service.interfaces.StudentService;
import view.components.CustomJTextField;
import view.components.CustomLabel;

import javax.swing.*;
import java.awt.*;

public class EditStudentPanel extends JPanel {

    private StudentService studentService;
    private CustomJTextField number;
    private CustomJTextField name;
    private CustomJTextField secondName;
    private CustomJTextField fathersName;
    private CustomJTextField course;
    private CustomJTextField group;
    private CustomJTextField room;

    public EditStudentPanel(StudentService studentService, int id){
        this.studentService = studentService;
        Student student = studentService.getById(id);
        setLayout(new GridLayout(8, 2, 20, 20));

        number = new CustomJTextField(Integer.toString(student.getId()));
        name = new CustomJTextField(student.getName());
        secondName = new CustomJTextField(student.getSecondName());
        fathersName = new CustomJTextField(student.getFathersName());
        course = new CustomJTextField(Integer.toString(student.getCourse()));
        group = new CustomJTextField(Long.toString(student.getGroup()));
        room = new CustomJTextField(Long.toString(student.getRoomId()));

        add(new CustomLabel("Номер студента"));
        add(number);
        add(new CustomLabel("Имя"));
        add(name);
        add(new CustomLabel("Фамилия"));
        add(secondName);
        add(new CustomLabel("Отчество"));
        add(fathersName);
        add(new CustomLabel("Курс"));
        add(course);
        add(new CustomLabel("Группа"));
        add(group);
        add(new CustomLabel("Номер комнаты"));
        add(room);
        add(new Button("Изменить"));
        add(new Button("Удалить"));

    }

}
