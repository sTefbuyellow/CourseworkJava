package view.components;

import view.panels.CreateStudentPanel;

import javax.swing.*;

public class MassageViewer {

    public static void showErrorMessage(String message, JPanel panel) {
        JOptionPane.showMessageDialog(panel,
                new String[]{"Ошибка :",
                        message},
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(String message, JPanel panel) {
        JOptionPane.showMessageDialog(panel,
                message);
    }

    public static boolean confirmAction(String message, JPanel panel){
        int result = JOptionPane.showConfirmDialog(
                panel,
                message,
                "Подтверждение",
                JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

}
