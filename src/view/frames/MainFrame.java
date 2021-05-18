package view.frames;

import repos.ConnectionManager;
import repos.implementations.RoomRepositoryImpl;
import repos.implementations.StudentRepositoryImpl;
import service.DataPrinter;
import service.implementations.RoomServiceImpl;
import service.implementations.StudentServiceImpl;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.MenuBar;
import view.panels.CreateRoomPanel;
import view.panels.CreateStudentPanel;
import view.panels.RoomPanel;
import view.panels.StudentPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A <code>MainFrame</code> class is an extended
 * version of <code>JFrame</code> that contains main data.
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class MainFrame extends JFrame {

    public static HelpingFrame helpingFrame;

    /**
     * Creates main frame.
     *
     * @param connectionManager <code>ConnectionManager</code> class object that creates connection
     *                          between program and database.
     */
    public MainFrame(ConnectionManager connectionManager) {
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1, 10, 10));
        setLocationRelativeTo(null);

        setJMenuBar(new MenuBar(this));

        RoomRepositoryImpl roomRepository = new RoomRepositoryImpl(connectionManager.getConnection());
        StudentRepositoryImpl studentRepository = new StudentRepositoryImpl(connectionManager.getConnection());
        RoomService roomService = new RoomServiceImpl(roomRepository);
        StudentService studentService = new StudentServiceImpl(studentRepository);

        DataPrinter.setServices(studentService, roomService);

        helpingFrame = new HelpingFrame(1, 1, new JLabel(), new JPanel());
        helpingFrame.setVisible(false);

        StudentPanel studentPanel = new StudentPanel(studentService, roomService);
        RoomPanel roomPanel = new RoomPanel(roomService, studentService, studentPanel);
        studentPanel.setRoomPanel(roomPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Комнаты", roomPanel);
        tabbedPane.addTab("Студенты", studentPanel);
        tabbedPane.addTab("Добавить комнату", new CreateRoomPanel(roomService, roomPanel, studentPanel));
        tabbedPane.addTab("Добавить студента", new CreateStudentPanel(studentService,
                roomService, roomPanel, studentPanel));
        getContentPane().add(tabbedPane);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closed");
                e.getWindow().dispose();
                connectionManager.closeConnection();
            }
        });
    }

}
