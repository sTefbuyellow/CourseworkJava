package view.panels;

import model.Room;
import model.Sex;
import model.Student;
import service.ValidatorService;
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
import java.util.Objects;

import static service.MassageViewer.*;
import static service.ValidatorService.generateStudentErrorString;

/**
 * A <code>EditRoomPanel</code> class is an extended
 * version of <code>JPanel</code> that contains input fields
 * to update student data.
 *
 * @author Kirichuk K.N.
 * @version 1.5 20.03.2021
 */
public class EditStudentPanel extends JPanel {

    private final StudentService studentService;
    private final StudentPanel studentPanel;
    private final RoomPanel roomPanel;
    private final RoomService roomService;
    private final Student student;
    private final CustomJTextField name;
    private final CustomJTextField secondName;
    private final CustomJTextField fathersName;
    private final CustomJComboBox sex;
    private final CustomJTextField course;
    private final CustomJTextField group;
    private final CustomJTextField room;


    /**
     * A <code>EditRoomPanel</code> class object contains fields to edit a student.
     *
     * @param studentService a <code>StudentService</code> class object.
     * @param roomService a <code>RoomService</code> class object.
     * @param roomPanel a <code>RoomPanel</code> class object.
     * @param studentPanel a <code>StudentPanel</code> class object.
     * @param id id of ыегвуте witch will be edited.
     */
    public EditStudentPanel(StudentService studentService, RoomService roomService,
                            RoomPanel roomPanel, StudentPanel studentPanel, int id) {
        this.studentService = studentService;
        this.roomService = roomService;
        this.roomPanel = roomPanel;
        this.studentPanel = studentPanel;
        student = studentService.getById(id);
        setLayout(new GridLayout(8, 2, 20, 20));

        name = new CustomJTextField(student.getName());
        secondName = new CustomJTextField(student.getSecondName());
        fathersName = new CustomJTextField(student.getFathersName());
        sex = new CustomJComboBox(student.getSex());
        course = new CustomJTextField(Integer.toString(student.getCourse()));
        group = new CustomJTextField(Long.toString(student.getGroup()));
        room = new CustomJTextField(Long.toString(student.getRoomId()));

        add(new CustomLabel("Фамилия"));
        add(secondName);
        add(new CustomLabel("Имя"));
        add(name);
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

    /**
     * Create room button listener.
     *
     * Validates room fields and creates new room.
     */
    private class EditStudentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean[] validationResults = ValidatorService.isStudentNotValid(name.getText(), secondName.getText(), fathersName.getText(),
                    group.getText(), course.getText(), room.getText());
            if (validationResults[0] || validationResults[1] || validationResults[2]
                    || validationResults[3] || validationResults[4] || validationResults[5]) {
                showErrorMessage(generateStudentErrorString(validationResults),
                        EditStudentPanel.this);
                return;
            }
            if (ValidatorService.isStudentNumberFieldsNotValid(course.getText())) {
                showErrorMessage(new String[]{"Ошибка: ", "Пожалуйста, укажите реалистичные значения курса."},
                        EditStudentPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(room.getText()));
            if (selectedRoom == null) {
                showErrorMessage(new String[]{"Ошибка: ", "Указанная комната не существует."},
                        EditStudentPanel.this);
                return;
            }
            if (selectedRoom.getStudents() == selectedRoom.getBedsCount()) {
                showErrorMessage(new String[]{"Ошибка: ", "Все места в комнате заняты"},
                        EditStudentPanel.this);
                return;
            }
            int studentId;
            if (student.getRoomId() == selectedRoom.getId())
                studentId = student.getId();
            else studentId = getUniqueId(selectedRoom);
            Sex selectedSex = null;
            if(Objects.equals(sex.getSelectedItem(), "Мужской"))
                selectedSex = Sex.M;
            if(Objects.equals(sex.getSelectedItem(), "Женский"))
                selectedSex = Sex.F;
            Student selectedStudent = new Student(studentId,
                    name.getText(), secondName.getText(), fathersName.getText(), selectedSex,
                    Integer.parseInt(group.getText()), Integer.parseInt(course.getText()), Integer.parseInt(room.getText()));
            ArrayList<Student> studentsInRoom = (ArrayList<Student>) studentService.getAllByRoomId(selectedRoom.getId());
            for (Student iterator : studentsInRoom) {
                if (selectedStudent.getSex() != iterator.getSex()) {
                    if (confirmAction(new String[]{"Вы уверены что хотите заселить",
                            "мужчин и женщин в одну комнату?"}, EditStudentPanel.this))
                        break;
                    else return;
                }
            }
            if (studentService.update(selectedStudent, student.getId())) {
                showMessage("Студент успешно обновлен", EditStudentPanel.this);
                studentPanel.revalidatePanel();
                roomPanel.revalidatePanel();
            } else showErrorMessage(new String[]{"Ошибка: ", "Ошибка при обновлении студента"},
                    EditStudentPanel.this);
        }
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

    /**
     * Delete student button listener
     *
     * Deletes user from database
     */
    private class DeleteStudentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean action = confirmAction(new String[]{"Вы действительно хотите убрать выбранного студента из базы?"},
                    EditStudentPanel.this);
            if (action) {
                if (studentService.delete(student.getId())) {
                    showMessage("Студент успешно удален", EditStudentPanel.this);
                    studentPanel.revalidatePanel();
                    roomPanel.revalidatePanel();
                } else
                    showErrorMessage(new String[]{"Ошибка: ", "Ошибка при удалении студента"}, EditStudentPanel.this);
            }
        }
    }

}
