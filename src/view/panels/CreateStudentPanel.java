package view.panels;

import model.Room;
import model.Student;
import service.implementations.ValidatorService;
import service.interfaces.StudentService;
import view.components.CustomJTextField;
import view.components.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateStudentPanel extends JPanel {

    private StudentService studentService;
    private CustomJTextField number;
    private CustomJTextField name;
    private CustomJTextField secondName;
    private CustomJTextField fathersName;
    private CustomJTextField course;
    private CustomJTextField group;
    private CustomJTextField room;

    public CreateStudentPanel(StudentService studentService){
        this.studentService = studentService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(7, 2, 20, 20));

        number = new CustomJTextField("");
        name = new CustomJTextField("");
        secondName = new CustomJTextField("");
        fathersName = new CustomJTextField("");
        course = new CustomJTextField("");
        group = new CustomJTextField("");
        room = new CustomJTextField("");

        fieldsPanel.add(new CustomLabel("Номер студента"));
        fieldsPanel.add(number);
        fieldsPanel.add(new CustomLabel("Имя"));
        fieldsPanel.add(name);
        fieldsPanel.add(new CustomLabel("Фамилия"));
        fieldsPanel.add(secondName);
        fieldsPanel.add(new CustomLabel("Отчество"));
        fieldsPanel.add(fathersName);
        fieldsPanel.add(new CustomLabel("Курс"));
        fieldsPanel.add(course);
        fieldsPanel.add(new CustomLabel("Группа"));
        fieldsPanel.add(group);
        fieldsPanel.add(new CustomLabel("Номер комнаты"));
        fieldsPanel.add(room);

        add(new CustomLabel("Заселение студента"));
        add(fieldsPanel);
        add(new Button("Добавить"));

    }

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(CreateStudentPanel.this,
                new String[] {"Ошибка :",
                        message},
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(CreateStudentPanel.this,
                "Комната успешно добавлена");
    }


    private class CreateRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//            if (!ValidatorService.isRoomValid(roomNumber.getText(),
//                    flore.getText(), bedsCount.getText())) {
//                showErrorMessage("Убедитесь, что все поля заполнены в верном формате.");
//                return;
//            }
//            if (!ValidatorService.isRoomNumberFieldsValid(roomNumber.getText(),
//                    flore.getText(), bedsCount.getText())){
//                showErrorMessage("Пожалуйста, укажите реалистичные значения.");
//                return;
//            }
//            roomService.create(new Room(Integer.parseInt(roomNumber.getText()),
//                    Integer.parseInt(flore.getText()),
//                    Integer.parseInt(bedsCount.getText())));
//            showMessage("Комната успешно добавлена!");
//            roomNumber.setText("");
//            flore.setText("");
//            bedsCount.setText("");

        }

    }
}
