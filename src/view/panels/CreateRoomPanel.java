package view.panels;

import model.Room;
import service.implementations.ValidatorService;
import service.interfaces.RoomService;
import view.components.CustomJTextField;
import view.components.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.components.MassageViewer.showErrorMessage;
import static view.components.MassageViewer.showMessage;

public class CreateRoomPanel extends JPanel {

    private RoomService roomService;
    private CustomJTextField roomNumber;
    private CustomJTextField flore;
    private CustomJTextField bedsCount;

    public CreateRoomPanel(RoomService roomService) {
        this.roomService = roomService;
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

    private class CreateRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!ValidatorService.isRoomValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText())) {
                showErrorMessage("Убедитесь, что все поля заполнены в верном формате.", CreateRoomPanel.this);
                return;
            }
            if (!ValidatorService.isRoomNumberFieldsValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText())) {
                showErrorMessage("Пожалуйста, укажите реалистичные значения.", CreateRoomPanel.this);
                return;
            }
            Room selectedRoom = roomService.getById(Integer.parseInt(roomNumber.getText()));
            if (selectedRoom != null) {
                showErrorMessage("Комната № " + roomNumber.getText() + "уже существует.", CreateRoomPanel.this);
                return;
            }
            Room room = new Room(Integer.parseInt(roomNumber.getText()),
                    Integer.parseInt(flore.getText()),
                    Integer.parseInt(bedsCount.getText()));
            if (roomService.create(room))
                showMessage("Комната успешно добавлена!", CreateRoomPanel.this);
            else
                showErrorMessage("Ошибка при добавлении комнаты.", CreateRoomPanel.this);
            roomNumber.setText("");
            flore.setText("");
            bedsCount.setText("");
        }
    }

}
