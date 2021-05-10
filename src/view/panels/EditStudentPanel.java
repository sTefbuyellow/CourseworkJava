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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static view.components.MassageViewer.*;

public class EditStudentPanel extends JPanel {

    private final StudentService studentService;
    private final RoomService roomService;
    private final Student student;
    private final CustomJTextField name;
    private final CustomJTextField secondName;
    private final CustomJTextField fathersName;
    private final CustomJComboBox sex;
    private final CustomJTextField course;
    private final CustomJTextField group;
    private final CustomJTextField room;

    public EditStudentPanel(StudentService studentService, RoomService roomService, int id) {
        this.studentService = studentService;
        this.roomService = roomService;
        student = studentService.getById(id);
        setLayout(new GridLayout(8, 2, 20, 20));

        name = new CustomJTextField(student.getName());
        secondName = new CustomJTextField(student.getSecondName());
        fathersName = new CustomJTextField(student.getFathersName());
        sex = new CustomJComboBox(student.getSex());
        course = new CustomJTextField(Integer.toString(student.getCourse()));
        group = new CustomJTextField(Long.toString(student.getGroup()));
        room = new CustomJTextField(Long.toString(student.getRoomId()));

        add(new CustomLabel("Имя"));
        add(name);
        add(new CustomLabel("Фамилия"));
        add(secondName);
        add(new CustomLabel("Отчество"));
        add(fathersName);
        add(new CustomLabel("Пол"));
        add(sex);
        add(new CustomLabel("Курс"));
        add(course);
        add(new CustomLabel("Группа"));
        add(group);
        add(new CustomLabel("Номер комнаты"));
        add(room);

        Button editButton = new Button("Изменить");
        Button deleteButton = new Button("Удалить");
        editButton.addActionListener(new EditStudentActionListener());
        deleteButton.addActionListener(new DeleteStudentActionListener());

        add(editButton);
        add(deleteButton);

    }

    private int getUniqueId(Room selectedRoom) {
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

    private class EditStudentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!ValidatorService.isStudentValid(name.getText(), secondName.getText(), fathersName.getText(),
                    group.getText(), course.getText(), room.getText())) {
                showErrorMessage("Убедитесь, что все поля заполнены в верном формате.",
                        EditStudentPanel.this);
                return;
            }
            if (!ValidatorService.isStudentNumberFieldsValid(course.getText())) {
                showErrorMessage("Пожалуйста, укажите реалистичные значения.",
                        EditStudentPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(room.getText()));
            if (selectedRoom == null) {
                showErrorMessage("Указанная комната не существует.",
                        EditStudentPanel.this);
                return;
            }
            if (selectedRoom.getStudents() == selectedRoom.getBedsCount()) {
                showErrorMessage("Все места в комнате заняты",
                        EditStudentPanel.this);
                return;
            }
            int studentId;
            if (student.getRoomId() == selectedRoom.getId())
                studentId = student.getId();
            else studentId = getUniqueId(selectedRoom);
            Student selectedStudent = new Student(studentId,
                    name.getText(), secondName.getText(), fathersName.getText(), (Sex) sex.getSelectedItem(),
                    Integer.parseInt(group.getText()), Integer.parseInt(course.getText()), Integer.parseInt(room.getText()));
            System.out.println(selectedStudent+ "  " + student.getId());
            if (studentService.update(selectedStudent, student.getId()))
                showMessage("Студент успешно обновлен", EditStudentPanel.this);
            else showErrorMessage("Ошибка при обновлении студента",
                    EditStudentPanel.this);
        }
    }

    private class DeleteStudentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean action = confirmAction("Вы действительно хотите убрать выбранного студента из базы?",
                    EditStudentPanel.this);
            if(action){
                if(studentService.delete(student.getId()))
                    showMessage("Студент успешно удален", EditStudentPanel.this);
                else showErrorMessage("Ошибка при удалении студента", EditStudentPanel.this);
            }
        }
    }

}
