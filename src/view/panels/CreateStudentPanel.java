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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static service.MassageViewer.*;
import static service.ValidatorService.generateStudentErrorString;

/**
 * A <code>CreateStudentPanel</code> class is an extended
 * version of <code>JPanel</code> that contains input fields
 * to create new student.
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class CreateStudentPanel extends JPanel {

    private final StudentService studentService;
    private final RoomService roomService;
    private final StudentPanel studentPanel;
    private final RoomPanel roomPanel;
    private final CustomJTextField name;
    private final CustomJTextField secondName;
    private final CustomJTextField fathersName;
    private final CustomJComboBox sex;
    private final CustomJTextField course;
    private final CustomJTextField group;
    private final CustomJTextField room;

    /**
     * A <code>CreateStudentPanel</code> class object contains fields to create new student.
     *
     * @param studentService a <code>StudentService</code> class object.
     * @param roomService a <code>RoomService</code> class object.
     * @param roomPanel a <code>RoomPanel</code> class object.
     * @param studentPanel a <code>StudentPanel</code> class object.
     */
    public CreateStudentPanel(StudentService studentService, RoomService roomService,
                              RoomPanel roomPanel, StudentPanel studentPanel) {
        this.studentService = studentService;
        this.roomService = roomService;
        this.studentPanel = studentPanel;
        this.roomPanel = roomPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(7, 2, 20, 20));

        secondName = new CustomJTextField("");
        name = new CustomJTextField("");
        fathersName = new CustomJTextField("");
        course = new CustomJTextField("");
        group = new CustomJTextField("");
        room = new CustomJTextField("");
        sex = new CustomJComboBox();

        fieldsPanel.add(new CustomLabel("Фамилия"));
        fieldsPanel.add(secondName);
        fieldsPanel.add(new CustomLabel("Имя"));
        fieldsPanel.add(name);
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

    /**
     * Create student button listener.
     *
     * Validates student fields, checks for dublikates and creates new room.
     */
    private class CreateStudentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean[] validationResult = ValidatorService.isStudentNotValid(name.getText(), secondName.getText(), fathersName.getText(),
                    group.getText(), course.getText(), room.getText());
            if (validationResult[0] || validationResult[1] || validationResult[2] || validationResult[3] ||
                    validationResult[4] || validationResult[5]) {
                showErrorMessage(generateStudentErrorString(validationResult),
                        CreateStudentPanel.this);
                return;
            }
            if (ValidatorService.isStudentNumberFieldsNotValid(course.getText())) {
                showErrorMessage(new String[]{"Ошибка: ", "Пожалуйста, укажите реалистичные значения курса."},
                        CreateStudentPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(room.getText()));
            if (selectedRoom == null) {
                showErrorMessage(new String[]{"Ошибка: ", "Указанная комната не существует."},
                        CreateStudentPanel.this);
                return;
            }
            if (studentService.getAllByRoomId(selectedRoom.getId()).size() == selectedRoom.getBedsCount()) {
                showErrorMessage(new String[]{"Ошибка", "Все места в комнате заняты"},
                        CreateStudentPanel.this);
                return;
            }
            int studentId = getUniqueId(selectedRoom);
            Sex selectedSex = null;
            if (Objects.equals(sex.getSelectedItem(), "Мужской"))
                selectedSex = Sex.M;
            if (Objects.equals(sex.getSelectedItem(), "Женский"))
                selectedSex = Sex.F;
            Student student = new Student(studentId,
                    name.getText(), secondName.getText(), fathersName.getText(), selectedSex,
                    Integer.parseInt(group.getText()), Integer.parseInt(course.getText()), Integer.parseInt(room.getText()));
            ArrayList<Student> studentsInRoom = (ArrayList<Student>) studentService.getAllByRoomId(selectedRoom.getId());
            for (Student iterator : studentsInRoom) {
                if (student.getSex() != iterator.getSex())
                    if (confirmAction(new String[]{"Вы уверены что хотите заселить",
                            "мужчин и женщин в одну комнату?"}, CreateStudentPanel.this))
                        break;
                    else return;
                break;
            }
            if (studentService.create(student)) {
                showMessage("Студент успешно добавлен", CreateStudentPanel.this);
                studentPanel.revalidatePanel();
                roomPanel.revalidatePanel();
            } else
                showErrorMessage(new String[]{"Ошибка: ", "Ошибка при добавлении студента"}, CreateStudentPanel.this);
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
}
