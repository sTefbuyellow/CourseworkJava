package view.panels;

import model.Room;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomLabel;
import service.TableBuilder;
import view.frames.HelpingFrame;
import view.frames.MainFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * A <code>RoomPanel</code> class is an extended
 * version of <code>JPanel</code> that contains rooms information.
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class RoomPanel extends JPanel {

    private final RoomService roomService;
    private final StudentPanel studentPanel;
    private final StudentService studentService;
    private JScrollPane scrollPane;

    /**
     *  A <code>RoomPanel</code> class object contains all rooms information.
     *
     * @param roomService a <code>StudentPanel</code> class object.
     * @param studentService a <code>StudentService</code> class object.
     * @param studentPanel a <code>StudentPanel</code> class object.
     */
    public RoomPanel(RoomService roomService, StudentService studentService, StudentPanel studentPanel) {
        this.studentService = studentService;
        this.roomService = roomService;
        this.studentPanel = studentPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new CustomLabel("Список комнат"));
        ArrayList<Room> roomArrayList = (ArrayList<Room>) roomService.getAllRooms();
        Object[][] roomsString = toRoomsList(roomArrayList);
        RoomsTableMouseListener roomsTableMouseListener = new RoomsTableMouseListener();
        scrollPane = new JScrollPane( TableBuilder.buildTable(roomsString, new String[]{"Номер комнаты",
                "Этаж", "Количество мест", "Занято мест", "Свободно мест"}, roomsTableMouseListener));
        add(scrollPane);

    }


    /**
     * Updates room table.
     */
    public void revalidatePanel(){
        ArrayList<Room> roomArrayList = (ArrayList<Room>) roomService.getAllRooms();
        Object[][] roomsString = toRoomsList(roomArrayList);
        RoomsTableMouseListener roomsTableMouseListener = new RoomsTableMouseListener();
        remove(scrollPane);
        scrollPane = new JScrollPane( TableBuilder.buildTable(roomsString, new String[]{"Номер комнаты",
                "Этаж", "Количество мест", "Занято мест", "Свободно мест"}, roomsTableMouseListener));
        add(scrollPane);
        repaint();
        revalidate();
    }

    /**
     * Cast <code>ArrayList</code> of students to
     * array of strings.
     *
     * @param roomArrayList <code>ArrayList</code> of rooms.
     * @return rooms data.
     */
    public Object[][] toRoomsList(ArrayList<Room> roomArrayList) {
        Room[] rooms = roomArrayList.toArray(new Room[0]);
        Object[][] roomsString = new String[rooms.length][5];
        for (int i = 0; i < rooms.length; i++) {
            roomsString[i][0] = Integer.toString(rooms[i].getId());
            roomsString[i][1] = Integer.toString(rooms[i].getFlore());
            roomsString[i][2] = Integer.toString(rooms[i].getBedsCount());
            roomsString[i][3] = Integer.toString(rooms[i].getStudents());
            roomsString[i][4] = Integer.toString(rooms[i].getBedsCount() - rooms[i].getStudents());

        }
        return roomsString;
    }

    /**
     * Room table mouse listener.
     *
     * Creates helping frame with selected room information
     */
    private class RoomsTableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                int column = 0;
                int row = table.getSelectedRow();
                String value = table.getModel().getValueAt(row, column).toString();
                MainFrame.helpingFrame.dispatchEvent(new WindowEvent(MainFrame.helpingFrame, WindowEvent.WINDOW_CLOSING));
                MainFrame.helpingFrame = new HelpingFrame(500, 400, new CustomLabel("Комната № " + value),
                        new EditRoomPanel(roomService, studentService,
                                RoomPanel.this, studentPanel, Integer.parseInt(value)));
            }
        }
    }

}
