package view.panels;

import model.Room;
import service.ValidatorService;
import service.interfaces.RoomService;
import view.components.CustomJTextField;
import view.components.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static service.MassageViewer.showErrorMessage;
import static service.MassageViewer.showMessage;
import static service.ValidatorService.generateRoomErrorString;

/**
 * A <code>CreateRoomPanel</code> class is an extended
 * version of <code>JPanel</code> that contains input fields
 * to create new room.
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class CreateRoomPanel extends JPanel {

    private final RoomService roomService;
    private final RoomPanel roomPanel;
    private final StudentPanel studentPanel;
    private final CustomJTextField roomNumber;
    private final CustomJTextField flore;
    private final CustomJTextField bedsCount;

    /**
     * A <code>CreateRoomPanel</code> class object contains fields to create new room.
     *
     * @param roomService a <code>RoomService</code> class object.
     * @param roomPanel a <code>RoomPanel</code> class object.
     * @param studentPanel a <code>StudentService</code> class object.
     */
    public CreateRoomPanel(RoomService roomService, RoomPanel roomPanel, StudentPanel studentPanel) {
        this.roomService = roomService;
        this.roomPanel = roomPanel;
        this.studentPanel = studentPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3, 2, 20, 20));

        roomNumber = new CustomJTextField("");
        flore = new CustomJTextField("");
        bedsCount = new CustomJTextField("");

        fieldsPanel.add(new CustomLabel("Номер"));
        fieldsPanel.add(roomNumber);
        fieldsPanel.add(new CustomLabel("Этаж"));
        fieldsPanel.add(flore);
        fieldsPanel.add(new CustomLabel("Количество мест"));
        fieldsPanel.add(bedsCount);

        add(new JPanel());
        add(new CustomLabel("Новая комната"));
        add(new JPanel());
        add(fieldsPanel);
        add(new JPanel());
        add(new JPanel());
        CreateRoomActionListener listener = new CreateRoomActionListener();
        Button createRoomButton = new Button("Добавить");
        createRoomButton.addActionListener(listener);
        add(createRoomButton);
    }


    /**
     * Create room button listener.
     *
     * Validates room fields and creates new room.
     */
    private class CreateRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean[] validationResult = ValidatorService.isRoomNotValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText());
            if (validationResult[0] || validationResult[1] || validationResult[2]) {
                showErrorMessage(generateRoomErrorString(validationResult), CreateRoomPanel.this);
                return;
            }
            validationResult = ValidatorService.isRoomNumberFieldsNotValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText());
            if (validationResult[0] || validationResult[1] || validationResult[2]) {
                showErrorMessage(generateRoomErrorString(validationResult), CreateRoomPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(roomNumber.getText()));
            if (selectedRoom != null) {
                showErrorMessage(new String[]{"Ошибка: ","Комната № " + roomNumber.getText() + "уже существует."}, CreateRoomPanel.this);
                return;
            }
            Room room = new Room(Integer.parseInt(roomNumber.getText()),
                    Integer.parseInt(flore.getText()),
                    Integer.parseInt(bedsCount.getText()));
            if (roomService.create(room)) {
                showMessage("Комната успешно добавлена!", CreateRoomPanel.this);
                roomPanel.revalidatePanel();
                studentPanel.revalidatePanel();
            }
            else
                showErrorMessage(new String[]{"Ошибка: ","Ошибка при добавлении комнаты."}, CreateRoomPanel.this);
            roomNumber.setText("");
            flore.setText("");
            bedsCount.setText("");
        }
    }

}
