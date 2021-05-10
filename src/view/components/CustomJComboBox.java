package view.components;

import model.Sex;

import javax.swing.*;
import java.awt.*;

public class CustomJComboBox extends JComboBox<Sex> {

    public CustomJComboBox() {
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        addItem(Sex.F);
        addItem(Sex.M);
    }

    public CustomJComboBox(Sex sex) {
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        addItem(Sex.F);
        addItem(Sex.M);
        setSelectedItem(sex);
    }
}
