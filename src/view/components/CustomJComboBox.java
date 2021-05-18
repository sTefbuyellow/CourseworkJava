package view.components;

import model.Sex;

import javax.swing.*;
import java.awt.*;

/**
 * <code>CustomJComboBox</code> is an extension of
 * <code>JComboBox</code> that sets alignment, font and items.
 *
 * @author Kirichuk K.N.
 */
public class CustomJComboBox extends JComboBox<String> {


    /**
     * Creates <code>CustomJComboBox</code> class object
     * with set text and font.
     */
    public CustomJComboBox() {
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        addItem("Женский");
        addItem("Мужской");
        getSelectedItem();
    }


    /**
     * Constructor that creates <code>CustomJComboBox</code>
     * object with already selected item, set text and font.
     *
     * @param sex value of selected item
     */
    public CustomJComboBox(Sex sex) {
        setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Font font = new Font("Bold", Font.BOLD, 20);
        setFont(font);
        addItem("Женский");
        addItem("Мужской");
        setSelectedItem(sex);
    }
}
