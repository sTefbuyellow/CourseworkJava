package view.panels;

import model.Room;
import model.Student;
import service.ValidatorService;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomJTextField;
import view.components.CustomLabel;
import service.TableBuilder;
import view.frames.HelpingFrame;
import view.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static service.MassageViewer.*;
import static service.ValidatorService.generateRoomErrorString;

/**
 * A <code>EditRoomPanel</code> class is an extended
 * version of <code>JPanel</code> that contains input fields
 * to update or delete room.
 *
 * @author Kirichuk K.N.
 * @version 1.6 20.03.2021
 */
public class EditRoomPanel extends JPanel {

    private final Room room;
    private final RoomService roomService;
    private final RoomPanel roomPanel;
    private final StudentPanel studentPanel;
    private ArrayList<Student> studentsArrayList;
    private final StudentService studentService;
    private final CustomJTextField roomNumber;
    private final CustomJTextField flore;
    private final CustomJTextField bedsCount;

    /**
     * A <code>EditRoomPanel</code> class object contains fields to edit a room.
     *
     * @param roomService a <code>RoomService</code> class object.
     * @param roomPanel a <code>RoomPanel</code> class object.
     * @param studentPanel a <code>StudentService</code> class object.
     * @param id id of room witch will be edited.
     */
    public EditRoomPanel(RoomService roomService, StudentService studentService,
                         RoomPanel roomPanel, StudentPanel studentPanel, int id) {
        this.roomService = roomService;
        this.studentPanel = studentPanel;
        this.studentService = studentService;
        this.roomPanel = roomPanel;
        room = roomService.getById(id);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel roomData = new JPanel();
        roomData.setLayout(new GridLayout(4,2,20,20));

        roomNumber = new CustomJTextField(Integer.toString(room.getId()));
        flore = new CustomJTextField(Integer.toString(room.getFlore()));
        bedsCount = new CustomJTextField(Integer.toString(room.getBedsCount()));

        roomData.add(new CustomLabel("Номер"));
        roomData.add(roomNumber);
        roomData.add(new CustomLabel("Этаж"));
        roomData.add(flore);
        roomData.add(new CustomLabel("Количество мест"));
        roomData.add(bedsCount);

        Button editButton = new Button("Изменить");
        Button deleteButton = new Button("Удалить");
        editButton.addActionListener(new UpdateRoomActionListener());
        deleteButton.addActionListener(new DeleteRoomActionListener());

        roomData.add(editButton);
        roomData.add(deleteButton);

        add(getStudentsInRoom(id));
        add(roomData);
    }


    /**
     * Creates and returns JTable with users, who leaves in selected room
     *
     * @param id id of room witch will be edited.
     * @return a <code>JPanel</code> object with table.
     */
    private JPanel getStudentsInRoom(int id){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        studentsArrayList = (ArrayList<Student>) studentService.getAllByRoomId(id);
        panel.add(new CustomLabel("Список проживающих в комнате студентов"));
        Object[][] studentsString = toStudentList(studentsArrayList);
        panel.add(TableBuilder.buildTable(studentsString, new String[]{"Номер студента",
                "Имя", "Фамилия", "Отчество", "Пол", "Курс", "Группа"}, new StudentsTableMouseListener()));
        return panel;
    }

    /**
     * Cast <code>ArrayList</code> of students to
     * array of strings.
     *
     * @param studentsArrayList <code>ArrayList</code> of students.
     * @return students data.
     */
    public String[][] toStudentList(ArrayList<Student> studentsArrayList){
        Student[] studentsArray = studentsArrayList.toArray(new Student[0]);
        String[][] studentsList = new String[studentsArray.length][7];
        for (int i = 0; i < studentsArray.length; i++) {
            studentsList[i][0] = Integer.toString(studentsArray[i].getId());
            studentsList[i][1] = studentsArray[i].getName();
            studentsList[i][2] = studentsArray[i].getSecondName();
            studentsList[i][3] = studentsArray[i].getFathersName();
            studentsList[i][4] = studentsArray[i].sexToString();
            studentsList[i][5] = Integer.toString(studentsArray[i].getCourse());
            studentsList[i][6] = Long.toString(studentsArray[i].getGroup());
        }
        return studentsList;
    }

    /**
     * Update room button listener.
     *
     * Validates room fields and updates.
     */
    private class UpdateRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean[] validationResult = ValidatorService.isRoomNotValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText());
            if (validationResult[0] || validationResult[1] || validationResult[2]) {
                showErrorMessage(generateRoomErrorString(validationResult), EditRoomPanel.this);
                return;
            }
            validationResult = ValidatorService.isRoomNumberFieldsNotValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText());
            if (validationResult[0] || validationResult[1] || validationResult[2]) {
                showErrorMessage(generateRoomErrorString(validationResult), EditRoomPanel.this);
                return;
            }
            if (studentsArrayList.size() > Integer.parseInt(bedsCount.getText())) {
                showErrorMessage(new String[]{"Ошибка: ","Невозможно уменьшить количество мест"}, EditRoomPanel.this);
                return;
            }
            if(Integer.parseInt(roomNumber.getText()) != room.getId()) {
                Room selectedRoom = roomService.getById(Integer.parseInt(roomNumber.getText()));
                if (selectedRoom != null) {
                    showErrorMessage(new String[]{"Ошибка: ","Комната № " + roomNumber.getText() + "уже существует."}, EditRoomPanel.this);
                    return;
                }
            }
            Room updatedRoom = new Room(Integer.parseInt(roomNumber.getText()),
                    Integer.parseInt(flore.getText()),
                    Integer.parseInt(bedsCount.getText()));
            if (roomService.update(updatedRoom, room.getId())) {
                updateUsersId(updatedRoom.getId());
                roomPanel.revalidatePanel();
                studentPanel.revalidatePanel();
                showMessage("Информация о комнате обновлена!", EditRoomPanel.this);
            }
            else
                showErrorMessage(new String[]{"Ошибка: ","Ошибка при обновлении информации о комнате."}, EditRoomPanel.this);
        }
    }

    private class DeleteRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean action = confirmAction(new String[]{"Вы действительно хотите убрать выбранную комнату из базы?"},
                    EditRoomPanel.this);
            if(action){
                if(roomService.delete(room.getId())) {
                    showMessage("Комната успешно удалена", EditRoomPanel.this);
                    roomPanel.revalidatePanel();
                    studentPanel.revalidatePanel();
                }
                else showErrorMessage(new String[]{"Ошибка: ","Ошибка при удалении комнаты"}, EditRoomPanel.this);
            }
        }
    }

    private void updateUsersId(int roomNumber){
        for(Student student: studentsArrayList){
            int oldId = student.getId();
            int number = oldId%10;
            student.setId(Integer.parseInt(""+roomNumber+""+number+""));
            student.setRoomId(roomNumber);
            studentService.update(student, oldId);
        }
    }

    /**
     * User table mouse listener.
     *
     * Creates helping frame with selected student information
     */
    public class StudentsTableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                int column = 0;
                int row = table.getSelectedRow();
                String value = table.getModel().getValueAt(row, column).toString();
                MainFrame.helpingFrame.dispatchEvent(new WindowEvent(MainFrame.helpingFrame, WindowEvent.WINDOW_CLOSING));
                MainFrame.helpingFrame = new HelpingFrame(400, 540, new CustomLabel("Студент № " + value),
                        new EditStudentPanel(studentService, roomService, roomPanel, studentPanel, Integer.parseInt(value)));
            }
        }
    }
}
