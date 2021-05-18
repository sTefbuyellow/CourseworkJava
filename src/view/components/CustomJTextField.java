package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * <code>CustomJTextField</code> is an extension of
 * <code>JTextField</code> that sets alignment, font and text.
 *
 * @author Kirichuk K.N.
 */
public class CustomJTextField extends JTextField {


    /**
     * Creates the <code>CustomJTextField</code> object with set text
     * and font.
     *
     * @param text text witch will be set
     */
    public CustomJTextField(String text){
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        setText(text);
    }

}
