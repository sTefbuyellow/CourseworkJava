package view.panels;

import model.Room;
import model.Student;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomLabel;
import view.components.TableBuilder;
import view.frames.HelpingFrame;
import view.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RoomPanel extends JPanel {

    private RoomService roomService;
    private StudentService studentService;

    public RoomPanel(RoomService roomService, StudentService studentService) {
        this.studentService = studentService;
        this.roomService = roomService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new CustomLabel("Список комнат"));
        ArrayList<Room> roomArrayList = (ArrayList<Room>) roomService.getAllRooms();
        Object[][] roomsString = toRoomsList(roomArrayList);
        RoomsTableMouseListener roomsTableMouseListener = new RoomsTableMouseListener();
        add(TableBuilder.buildTable(roomsString, new String[]{"Номер комнаты",
                "Этаж", "Количество мест", "Занято мест"}, roomsTableMouseListener));
    }

    public Object[][] toRoomsList(ArrayList<Room> roomArrayList) {
        Room[] rooms = roomArrayList.toArray(new Room[0]);
        Object[][] roomsString = new String[rooms.length][4];
        for (int i = 0; i < rooms.length; i++) {
            roomsString[i][0] = Integer.toString(rooms[i].getId());
            roomsString[i][1] = Integer.toString(rooms[i].getFlore());
            roomsString[i][2] = Integer.toString(rooms[i].getBedsCount());
            roomsString[i][3] = Integer.toString(rooms[i].getStudents());
        }
        return roomsString;
    }

    public class RoomsTableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                int column = 0;
                int row = table.getSelectedRow();
                String value = table.getModel().getValueAt(row, column).toString();
                MainFrame.helpingFrame.dispatchEvent(new WindowEvent(MainFrame.helpingFrame, WindowEvent.WINDOW_CLOSING));
                MainFrame.helpingFrame = new HelpingFrame(500, 400, new CustomLabel("Комната № " + value),
                        new EditRoomPanel(roomService, studentService, Integer.parseInt(value)));
            }
        }
    }


}
