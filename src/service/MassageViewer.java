package service;

import javax.swing.*;

/**
 * The <code>MassageViewer</code> class creates
 * <code>JOptionPane</code> with a massage.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public class MassageViewer {

    /**
     * Displays frame with a error message.
     *
     * @param message displayed massage
     * @param panel panel witch contains massage
     */
    public static void showErrorMessage(String[] message, JPanel panel) {
        JOptionPane.showMessageDialog(panel,
                message,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays frame with a message.
     *
     * @param message displayed massage
     * @param panel panel witch contains massage
     */
    public static void showMessage(String message, JPanel panel) {
        JOptionPane.showMessageDialog(panel,
                message);
    }

    /**
     * Displays frame with a message and
     * suggests to choose between two options: "yes" and "no".
     *
     * @param message displayed massage
     * @param panel panel witch contains massage
     * @return <code>true</code> if user selected "yes" option;
     * <code>false</code> if user selected "no" option
     */
    public static boolean confirmAction(String[] message, JPanel panel){
        int result = JOptionPane.showConfirmDialog(
                panel,
                message,
                "Подтверждение",
                JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

}
