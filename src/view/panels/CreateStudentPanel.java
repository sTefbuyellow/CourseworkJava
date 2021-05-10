package view.panels;

import model.Room;
import model.Sex;
import model.Student;
import service.implementations.ValidatorService;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomJComboBox;
import view.components.CustomJTextField;
import view.components.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.components.MassageViewer.showErrorMessage;
import static view.components.MassageViewer.showMessage;

public class CreateStudentPanel extends JPanel {

    private final StudentService studentService;
    private final RoomService roomService;
    private final CustomJTextField name;
    private final CustomJTextField secondName;
    private final CustomJTextField fathersName;
    private final CustomJComboBox sex;
    private final CustomJTextField course;
    private final CustomJTextField group;
    private final CustomJTextField room;

    public CreateStudentPanel(StudentService studentService, RoomService roomService) {
        this.studentService = studentService;
        this.roomService = roomService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(7, 2, 20, 20));

        name = new CustomJTextField("");
        secondName = new CustomJTextField("");
        fathersName = new CustomJTextField("");
        course = new CustomJTextField("");
        group = new CustomJTextField("");
        room = new CustomJTextField("");
        sex = new CustomJComboBox();

        fieldsPanel.add(new CustomLabel("Имя"));
        fieldsPanel.add(name);
        fieldsPanel.add(new CustomLabel("Фамилия"));
        fieldsPanel.add(secondName);
        fieldsPanel.add(new CustomLabel("Отчество"));
        fieldsPanel.add(fathersName);
        fieldsPanel.add(new CustomLabel("Пол"));
        fieldsPanel.add(sex);
        fieldsPanel.add(new CustomLabel("Курс"));
        fieldsPanel.add(course);
        fieldsPanel.add(new CustomLabel("Группа"));
        fieldsPanel.add(group);
        fieldsPanel.add(new CustomLabel("Номер комнаты"));
        fieldsPanel.add(room);

        Button addButton = new Button("Добавить");
        addButton.addActionListener(new CreateStudentActionListener());

        add(new CustomLabel("Заселение студента"));
        add(fieldsPanel);
        add(addButton);

    }

    private int getUniqueId(Room selectedRoom){
        int studentId = 0;
        ArrayList<Student> studentList = (ArrayList<Student>) studentService.getAllByRoomId(selectedRoom.getId());
        for (int i = 1; i <= selectedRoom.getBedsCount(); i++) {
            studentId = Integer.parseInt("" + selectedRoom.getId() + "" + i);
            boolean isThereSameId = false;
            for (Student student : studentList) {
                if (student.getId() == studentId) {
                    isThereSameId = true;
                    break;
                }
            }
            if (!isThereSameId) {
                break;
            }
        }
        return studentId;
    }

    private class CreateStudentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!ValidatorService.isStudentValid(name.getText(), secondName.getText(), fathersName.getText(),
                    group.getText(), course.getText(), room.getText())) {
                showErrorMessage("Убедитесь, что все поля заполнены в верном формате.",
                        CreateStudentPanel.this);
                return;
            }
            if (!ValidatorService.isStudentNumberFieldsValid(course.getText())) {
                showErrorMessage("Пожалуйста, укажите реалистичные значения.",
                        CreateStudentPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(room.getText()));
            if (selectedRoom == null) {
                showErrorMessage("Указанная комната не существует.",
                        CreateStudentPanel.this);
                return;
            }
            if (selectedRoom.getStudents() == selectedRoom.getBedsCount()) {
                showErrorMessage("Все места в комнате заняты",
                        CreateStudentPanel.this);
                return;
            }
            int studentId = getUniqueId(selectedRoom);
            Student student = new Student(studentId,
                    name.getText(), secondName.getText(), fathersName.getText(), (Sex) sex.getSelectedItem(),
                    Integer.parseInt(group.getText()), Integer.parseInt(course.getText()), Integer.parseInt(room.getText()));
            System.out.println(student);
            if (studentService.create(student))
                showMessage("Студент успешно добавлен", CreateStudentPanel.this);
            else showErrorMessage("Ошибка при добавлении студента", CreateStudentPanel.this);
        }

    }
}
