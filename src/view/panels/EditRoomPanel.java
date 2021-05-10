package view.panels;

import model.Room;
import model.Student;
import service.implementations.ValidatorService;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomJTextField;
import view.components.CustomLabel;
import view.components.TableBuilder;
import view.frames.HelpingFrame;
import view.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static view.components.MassageViewer.*;

public class EditRoomPanel extends JPanel {

    private Room room;
    private RoomService roomService;
    private ArrayList<Student> studentsArrayList;
    private final StudentService studentService;
    private CustomJTextField roomNumber;
    private CustomJTextField flore;
    private CustomJTextField bedsCount;

    public EditRoomPanel(RoomService roomService, StudentService studentService, int id) {
        this.roomService = roomService;
        this.studentService = studentService;
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

    private void revalidatePanel(int id){
        new EditRoomPanel(roomService, studentService, id);
    }

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

    public String[][] toStudentList(ArrayList<Student> studentsArrayList){
        Student[] studentsArray = studentsArrayList.toArray(new Student[0]);
        String[][] studentsList = new String[studentsArray.length][8];
        for (int i = 0; i < studentsArray.length; i++) {
            studentsList[i][0] = Integer.toString(studentsArray[i].getId());
            studentsList[i][1] = studentsArray[i].getName();
            studentsList[i][2] = studentsArray[i].getSecondName();
            studentsList[i][3] = studentsArray[i].getFathersName();
            studentsList[i][4] = studentsArray[i].getSex().toString();
            studentsList[i][5] = Integer.toString(studentsArray[i].getCourse());
            studentsList[i][6] = Long.toString(studentsArray[i].getGroup());
            studentsList[i][7] = "Выселить";
        }
        return studentsList;
    }

    private class UpdateRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!ValidatorService.isRoomValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText())) {
                showErrorMessage("Убедитесь, что все поля заполнены в верном формате.", EditRoomPanel.this);
                return;
            }
            if (!ValidatorService.isRoomNumberFieldsValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText())) {
                showErrorMessage("Пожалуйста, укажите реалистичные значения.", EditRoomPanel.this);
                return;
            }
            if (studentsArrayList.size()< Integer.parseInt(bedsCount.getText())) {
                showErrorMessage("Невозможно уменьшить количество мест", EditRoomPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(roomNumber.getText()));
            if (selectedRoom != null) {
                showErrorMessage("Комната № " + roomNumber.getText() + "уже существует.", EditRoomPanel.this);
                return;
            }
            Room updatedRoom = new Room(Integer.parseInt(roomNumber.getText()),
                    Integer.parseInt(flore.getText()),
                    Integer.parseInt(bedsCount.getText()));
            if (roomService.update(updatedRoom, room.getId())) {
                updateUsersId(updatedRoom.getId());
                revalidatePanel(updatedRoom.getId());
                showMessage("Информация о комнате обновлена!", EditRoomPanel.this);
            }
            else
                showErrorMessage("Ошибка при обновлении информации о комнате.", EditRoomPanel.this);
        }
    }

    private class DeleteRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean action = confirmAction("Вы действительно хотите убрать выбранную комнату из базы?",
                    EditRoomPanel.this);
            if(action){
                if(roomService.delete(room.getId()))
                    showMessage("Комната успешно удалена", EditRoomPanel.this);
                else showErrorMessage("Ошибка при удалении комнаты", EditRoomPanel.this);
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

    public class StudentsTableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                int column = 0;
                int row = table.getSelectedRow();
                String value = table.getModel().getValueAt(row, column).toString();
                MainFrame.helpingFrame.dispatchEvent(new WindowEvent(MainFrame.helpingFrame, WindowEvent.WINDOW_CLOSING));
                MainFrame.helpingFrame = new HelpingFrame(400, 540, new CustomLabel("Студент № " + value),
                        new EditStudentPanel(studentService, roomService, Integer.parseInt(value)));
            }
        }
    }
}
