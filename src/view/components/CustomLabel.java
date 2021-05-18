package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * <code>CustomJLabel</code> is an extension of
 * <code>JLabel</code> that sets alignment, font and text.
 *
 * @author Kirichuk K.N.
 */
public class CustomLabel extends JLabel {

    /**
     * Creates the <code>CustomJTextField</code> object with set text
     * and font and created padding.
     *
     * @param text text witch will be set
     */
    public CustomLabel(String text){
        setHorizontalAlignment(JLabel.LEFT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        setText("        " + text);
    }
}
