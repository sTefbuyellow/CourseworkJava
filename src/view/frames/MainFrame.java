package view.frames;

import repos.ConnectionManager;
import repos.implementations.RoomRepositoryImpl;
import repos.implementations.StudentRepositoryImpl;
import service.implementations.RoomServiceImpl;
import service.implementations.StudentServiceImpl;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.panels.CreateRoomPanel;
import view.panels.CreateStudentPanel;
import view.panels.RoomPanel;
import view.panels.StudentPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public static HelpingFrame helpingFrame;

    public MainFrame(ConnectionManager connectionManager) {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1, 10, 10));

        RoomRepositoryImpl roomRepository = new RoomRepositoryImpl(connectionManager.getConnection());
        StudentRepositoryImpl studentRepository = new StudentRepositoryImpl(connectionManager.getConnection());
        RoomService roomService = new RoomServiceImpl(roomRepository, studentRepository);
        StudentService studentService = new StudentServiceImpl(studentRepository);

        helpingFrame = new HelpingFrame(1,1, new JLabel(), new JPanel());
        helpingFrame.setVisible(false);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Комнаты", new RoomPanel(roomService, studentService));
        tabbedPane.addTab("Студенты", new StudentPanel(studentService, roomService));
        tabbedPane.addTab("Добавить комнату", new CreateRoomPanel(roomService));
        tabbedPane.addTab("Добавить студента", new CreateStudentPanel(studentService, roomService));
        getContentPane().add(tabbedPane);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closed");
                e.getWindow().dispose();
                connectionManager.closeConnection();
            }
        });
        setVisible(true);
    }

}
