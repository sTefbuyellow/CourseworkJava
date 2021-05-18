package view.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import static java.awt.Image.SCALE_FAST;

/**
 * <code>AboutAuthorDialog</code> is an extension of
 * <code>JDialog</code> that contains information
 * about author of the project.
 *
 * @author Kirichuk K.N.
 */
public class AboutAuthorDialog extends JDialog {

    /**
     * Creates dialog with information about the author.
     *
     * @param parent parent frame of the dialog.
     */
    public AboutAuthorDialog(JFrame parent) {
        super(parent);
        setTitle("About author");
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        setModal(true);
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource("author.png");
        if (url == null) {
            return;
        }
        Image image = new ImageIcon(url).getImage();
        Image scaledImage = image.getScaledInstance(300, 300, SCALE_FAST);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        JLabel[] textLabels = new JLabel[]{
                imageLabel,
                new JLabel("Author"),
                new JLabel("student from group 10702418"),
                new JLabel("Kirichuk Kirill Nikolaevich"),
                new JLabel("kirilleastwood06@gmail.com")
        };
        Font font = new Font(Font.DIALOG, Font.BOLD, 16);
        for (JLabel label : textLabels) {
            label.setFont(font);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(label);
        }
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        add(textPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
