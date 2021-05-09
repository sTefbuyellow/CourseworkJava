package view.panels;

import model.Room;
import model.Student;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomJTextField;
import view.components.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditRoomPanel extends JPanel {

    private RoomService roomService;
    private CustomJTextField roomNumber;
    private CustomJTextField flore;
    private CustomJTextField bedsCount;

    public EditRoomPanel(RoomService roomService, int id) {
        this.roomService = roomService;
        Room room = roomService.getById(id);
        setLayout(new GridLayout(4,2,20,20));

        roomNumber = new CustomJTextField(Integer.toString(room.getId()));
        flore = new CustomJTextField(Integer.toString(room.getFlore()));
        bedsCount = new CustomJTextField(Integer.toString(room.getBedsCount()));

        add(new CustomLabel("Номер"));
        add(roomNumber);
        add(new CustomLabel("Этаж"));
        add(flore);
        add(new CustomLabel("Количество мест"));
        add(bedsCount);
        add(new Button("Изменить"));
        add(new Button("Удалить"));
    }

    private JPanel getStudentsInRoom(int id){
        ArrayList<Student> students = (ArrayList<Student>)roomService.getStudentsByRoom(id);
        JPanel panel = new JPanel();


        return panel;
    }
}
