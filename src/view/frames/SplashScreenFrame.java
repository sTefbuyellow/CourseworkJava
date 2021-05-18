package view.frames;

import repos.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import static java.awt.Image.SCALE_FAST;

/**
 * A <code>SplashScreen</code> is an extended version of <code>JFrame</code> that shown while
 * the <code>MainFrame</code> is creating. Contains information about the project, "Next" and "Exit" buttons.
 *
 * @author Kirichuk K.N.
 */
public class SplashScreenFrame extends JFrame {
    private final JButton nextButton;
    private final JButton exitButton;
    private final Timer timer;
    private final GridBagConstraints constraints;

    private final MainFrame mainFrame;

    /**
     * Starts a parallel thread, where <code>MainFrame</code> is being created,
     * creates visible <code>SplashScreen</code> object and runs timer.
     */
    public SplashScreenFrame(ConnectionManager connectionManager) {
        mainFrame = new MainFrame(connectionManager);

        final int delayMilliseconds = 60000;
        timer = new Timer(delayMilliseconds, e -> System.exit(0));

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        addSplashScreenText();
        nextButton = createNextButton();
        exitButton = createExitButton();
        addButtons();
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        timer.start();
    }

    private void addSplashScreenText() {
        addHeadLabel();
        addBodyLabel();
        addImageLabel();
        addInfoLabel();
    }

    private void addHeadLabel() {
        JLabel headLabel = new JLabel("<html><center><h2>"
                + "Белорусский национальный технический университет<br>"
                + "Факультет информационных технологий и робототехники<br>"
                + "Кафедра программного обеспечения информационных систем "
                + "и технологий<br></h2></center></html>");
        headLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        Insets oldInsets = constraints.insets;
        constraints.insets = new Insets(0, 10, 50, 10);
        add(headLabel, constraints);
        constraints.insets = oldInsets;
    }

    private void addBodyLabel() {
        JLabel bodyLabel = new JLabel("<html><center><h1>Курсовая работа<br>"
                + "по дисциплине «Программирование языке на Java»<br>"
                + "Приложение \"Заселение в\n <br>"
                + "студенческом общежитии\"<br></h1></center></html>");
        bodyLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(bodyLabel, constraints);
    }

    private void addImageLabel() {
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource("icon.png");
        if (url == null) {
            return;
        }
        Image image = new ImageIcon(url).getImage();
        Image scaledImage = image.getScaledInstance(300, 300, SCALE_FAST);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(imageLabel, constraints);
    }

    private void addInfoLabel() {
        JLabel infoLabel = new JLabel("<html><h2>"
                + "Выполнил: студент группы 10702418<br>"
                + "Киричук Кирилл Николаевич<br><br>"
                + "Преподаватель: к.ф.-м.н., доц.<br>"
                + "Сидорик Валерий Владимирович<br></h2></html>");
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(infoLabel, constraints);
    }

    private JButton createNextButton() {
        JButton button = new JButton("Далее");
        button.addActionListener(e -> switchToMainFrame());
        return button;
    }

    private JButton createExitButton() {
        JButton button = new JButton("Выход");
        button.addActionListener(e -> System.exit(0));
        return button;
    }

    private void addButtons() {
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0.5;
        constraints.ipady = 20;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(nextButton, constraints);
        constraints.gridx = 1;
        add(exitButton, constraints);
    }

    private void switchToMainFrame() {
        timer.stop();
        dispose();
        mainFrame.setVisible(true);
    }
}
