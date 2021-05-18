package view.components;

import view.frames.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.swing.filechooser.FileFilter;

import static service.DataPrinter.*;

/**
 * MenuBar is an extension of JMenuBar which contains menu items about section.
 * About section consists of "About author" and "About program" items.
 * "About" items open "About" dialogs with certain information.
 *
 * @author Kirichuk K.N.
 */
public class MenuBar extends JMenuBar {

    private final MainFrame parentFrame;
    private final JFileChooser fileChooser;

    private final String fileExtension = "txt";

    /**
     * Creates <code>MenuBar</code> object with "Load", "Save" and "About" items.
     * Initializes <code>parentFrame</code> which used for centering dialogs relatively to parent frame.
     *
     * @param parent parent frame of the menu bar.
     */
    public MenuBar(MainFrame parent) {
        super();
        parentFrame = parent;
        fileChooser = createFileChooser();
        add(createAboutMenu());
        add(createFileMenu());
    }

    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSize(300, 200);
        String fileDescription = "Hostel data .txt";
        FileFilter filter = new FileNameExtensionFilter(fileDescription, fileExtension);
        fileChooser.setFileFilter(filter);
        fileChooser.addActionListener(e -> {
            if (fileChooser.getDialogType() != JFileChooser.SAVE_DIALOG) {
                return;
            }
            File file = fileChooser.getSelectedFile();

            // If file name does not end with correct extension, add it
            if (!file.getName().endsWith('.' + fileExtension)) {
                String name = file.getAbsolutePath() + '.' + fileExtension;
                fileChooser.setSelectedFile(new File(name));
            }
        });
        return fileChooser;
    }

    private JMenu createAboutMenu() {
        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutDeveloperItem = new JMenuItem("Об авторе");
        JMenuItem aboutProgramItem = new JMenuItem("О программе");
        aboutDeveloperItem.addActionListener(e -> new AboutAuthorDialog(parentFrame));
        aboutProgramItem.addActionListener(e -> new AboutProgramDialog(parentFrame));
        aboutMenu.add(aboutDeveloperItem);
        aboutMenu.add(aboutProgramItem);
        return aboutMenu;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("Печать");
        JMenuItem dataItem = new JMenuItem("Вся информация");
        JMenuItem roomsItem = new JMenuItem("Комнаты");
        JMenuItem studentsItem = new JMenuItem("Студенты");
        dataItem.addActionListener(e -> saveHostelData());
        roomsItem.addActionListener(e -> saveRoomData());
        studentsItem.addActionListener(e -> saveStudentData());
        fileMenu.add(dataItem);
        fileMenu.add(roomsItem);
        fileMenu.add(studentsItem);
        return fileMenu;
    }

    private void saveHostelData() {
        File initialFile = new File("Hostel data." + fileExtension);
        fileChooser.setSelectedFile(initialFile);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

        int result = fileChooser.showSaveDialog(parentFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(selectedFile));
            for (String string: getPrintedData())
                outputWriter.write(string);
            outputWriter.flush();
            outputWriter.close();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Error. Couldn't save file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveRoomData() {
        File initialFile = new File("Hostel rooms data." + fileExtension);
        fileChooser.setSelectedFile(initialFile);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

        int result = fileChooser.showSaveDialog(parentFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(selectedFile));
            for (String string: getPrintedRooms())
                outputWriter.write(string);
            outputWriter.flush();
            outputWriter.close();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Error. Couldn't save file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveStudentData() {
        File initialFile = new File("Hostel students data." + fileExtension);
        fileChooser.setSelectedFile(initialFile);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

        int result = fileChooser.showSaveDialog(parentFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(selectedFile));
            for (String string: getPrintedStudents())
                outputWriter.write(string);
            outputWriter.flush();
            outputWriter.close();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Error. Couldn't save file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
