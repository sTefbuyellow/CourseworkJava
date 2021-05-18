package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * <code>AboutAuthorDialog</code> is an extension of
 * <code>JDialog</code> that contains information
 * about program.
 *
 * @author Kirichuk K.N.
 */
public class AboutProgramDialog extends JDialog {
    private static final String TITLE = "About program";
    private static final String PROGRAM_NAME = "Student hostel check-in";

    /**
     * Creates dialog with information about the author.
     *
     * @param parent parent frame of the dialog.
     */
    public AboutProgramDialog(JFrame parent) {
        super(parent, TITLE);
        setLayout(new GridBagLayout());
        setSize(200, 300);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }
}
