package view.components;

import javax.swing.*;
import java.awt.*;

public class CustomJTextField extends JTextField {

    public CustomJTextField(String text){
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        setText(text);
    }

}
