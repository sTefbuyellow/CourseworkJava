package view.panels;

import model.Student;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomLabel;
import view.components.TableBuilder;
import view.frames.HelpingFrame;
import view.frames.MainFrame;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentPanel extends JPanel {

    private static int selectedRow = 0;
    private StudentService studentService;
    private RoomService roomService;

    public StudentPanel(StudentService studentService, RoomService roomService) {
        this.roomService = roomService;
        this.studentService = studentService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ArrayList<Student> studentsArrayList = (ArrayList<Student>) studentService.getAll();
        add(new CustomLabel("Список заселенных студентов"));
        String[][] studentsString = toStudentList(studentsArrayList);
        StudentsTableMouseListener studentsTableMouseListener = new StudentsTableMouseListener();
        add(TableBuilder.buildTable(studentsString, new String[]{"Номер студента",
                "Имя", "Фамилия", "Отчество", "Пол", "Курс", "Группа"}, studentsTableMouseListener));
    }

    public String[][] toStudentList(ArrayList<Student> studentsArrayList){
        Student[] studentsArray = studentsArrayList.toArray(new Student[0]);
        String[][] studentsList = new String[studentsArray.length][7];
        for (int i = 0; i < studentsArray.length; i++) {
            studentsList[i][0] = Integer.toString(studentsArray[i].getId());
            studentsList[i][1] = studentsArray[i].getName();
            studentsList[i][2] = studentsArray[i].getSecondName();
            studentsList[i][3] = studentsArray[i].getFathersName();
            studentsList[i][4] = studentsArray[i].getSex().toString();
            studentsList[i][5] = Integer.toString(studentsArray[i].getCourse());
            studentsList[i][6] = Long.toString(studentsArray[i].getGroup());
        }
        return studentsList;
    }

    public class StudentsTableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                int column = 0;
                int row = table.getSelectedRow();
                String value = table.getModel().getValueAt(row, column).toString();
                MainFrame.helpingFrame.dispatchEvent(new WindowEvent(MainFrame.helpingFrame, WindowEvent.WINDOW_CLOSING));
                MainFrame.helpingFrame = new HelpingFrame(400, 540, new CustomLabel("Студент № " + value),
                        new EditStudentPanel(studentService, roomService, Integer.parseInt(value)));
            }
        }
    }



}
