package view.frames;

import javax.swing.*;

/**
 * <code>MenuBar</code> class is an extension of <code>JMenuBar</code>
 * which contains object of <code>StudentEditPanel</code> or <code>ProductEditPanel</code> class.
 *
 * @author Kirichuk K.N.
 */
public class HelpingFrame extends JFrame {

    /**
     * @param width width of a frame
     * @param height height of a frame
     * @param label title of a panel
     * @param panel panel that contains data
     */
    public HelpingFrame(int width, int height, JLabel label, JPanel panel){
        setSize(width, height);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);

        getContentPane().add(label);
        getContentPane().add(panel);

        setVisible(true);
    }

}
