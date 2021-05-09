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

public class CreateRoomPanel extends JPanel {

    private RoomService roomService;
    private CustomJTextField roomNumber;
    private CustomJTextField flore;
    private CustomJTextField bedsCount;

    public CreateRoomPanel(RoomService roomService) {
        this.roomService = roomService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3,2,20,20));

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

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(CreateRoomPanel.this,
                new String[] {"Ошибка :",
                              message},
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message){
            JOptionPane.showMessageDialog(CreateRoomPanel.this,
                    "Комната успешно добавлена");
    }


    private class CreateRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!ValidatorService.isRoomValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText())) {
                showErrorMessage("Убедитесь, что все поля заполнены в верном формате.");
                return;
            }
            if (!ValidatorService.isRoomNumberFieldsValid(roomNumber.getText(),
                    flore.getText(), bedsCount.getText())){
                showErrorMessage("Пожалуйста, укажите реалистичные значения.");
                return;
            }
            roomService.create(new Room(Integer.parseInt(roomNumber.getText()),
                    Integer.parseInt(flore.getText()),
                    Integer.parseInt(bedsCount.getText())));
            showMessage("Комната успешно добавлена!");
            roomNumber.setText("");
            flore.setText("");
            bedsCount.setText("");

        }

    }

}
