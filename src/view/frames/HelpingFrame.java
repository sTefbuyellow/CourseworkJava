package view.frames;

import javax.swing.*;
import java.awt.*;

public class HelpingFrame extends JFrame {

    public HelpingFrame(int width, int height, JLabel label, JPanel panel){
        setSize(width, height);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().add(label);
        getContentPane().add(panel);

        setVisible(true);
    }

}
