package view.panels;

import model.Student;
import service.interfaces.RoomService;
import service.interfaces.StudentService;
import view.components.CustomLabel;
import service.TableBuilder;
import view.frames.HelpingFrame;
import view.frames.MainFrame;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * A <code>StudentPanel</code> class is an extended
 * version of <code>JPanel</code> that contains students information.
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class StudentPanel extends JPanel {

    private final StudentService studentService;
    private final RoomService roomService;
    private RoomPanel roomPanel;
    private JScrollPane scrollPane;

    /**
     *  A <code>RoomPanel</code> class object contains all rooms information.
     *
     * @param roomService a <code>StudentPanel</code> class object.
     * @param studentService a <code>StudentService</code> class object.
     */
    public StudentPanel(StudentService studentService, RoomService roomService) {
        this.roomService = roomService;
        this.studentService = studentService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new CustomLabel("Список заселенных студентов"));
        ArrayList<Student> studentsArrayList = (ArrayList<Student>) studentService.getAll();
        String[][] studentsString = toStudentList(studentsArrayList);
        StudentsTableMouseListener studentsTableMouseListener = new StudentsTableMouseListener();
        scrollPane = TableBuilder.buildTable(studentsString, new String[]{"Номер студента",
                "Имя", "Фамилия", "Отчество", "Пол", "Курс", "Группа"}, studentsTableMouseListener);
        add(scrollPane);
    }


    /**
     * Set roomPanel.
     *
     * @param roomPanel a <code>RoomPanel</code> class object
     */
    public void setRoomPanel(RoomPanel roomPanel){
        this.roomPanel = roomPanel;
    }

    /**
     * Cast <code>ArrayList</code> of students to
     * array of strings.
     *
     * @param studentsArrayList <code>ArrayList</code> of students
     * @return students data.
     */
    public String[][] toStudentList(ArrayList<Student> studentsArrayList){
        Student[] studentsArray = studentsArrayList.toArray(new Student[0]);
        String[][] studentsList = new String[studentsArray.length][7];
        for (int i = 0; i < studentsArray.length; i++) {
            studentsList[i][0] = Integer.toString(studentsArray[i].getId());
            studentsList[i][1] = studentsArray[i].getName();
            studentsList[i][2] = studentsArray[i].getSecondName();
            studentsList[i][3] = studentsArray[i].getFathersName();
            studentsList[i][4] = studentsArray[i].sexToString();
            studentsList[i][5] = Integer.toString(studentsArray[i].getCourse());
            studentsList[i][6] = Long.toString(studentsArray[i].getGroup());
        }
        return studentsList;
    }

    /**
     * User table mouse listener.
     *
     * Creates helping frame with selected student information
     */
    private class StudentsTableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                int column = 0;
                int row = table.getSelectedRow();
                String value = table.getModel().getValueAt(row, column).toString();
                MainFrame.helpingFrame.dispatchEvent(new WindowEvent(MainFrame.helpingFrame, WindowEvent.WINDOW_CLOSING));
                MainFrame.helpingFrame = new HelpingFrame(400, 540, new CustomLabel("Студент № " + value),
                        new EditStudentPanel(studentService, roomService, roomPanel,StudentPanel.this, Integer.parseInt(value)));
            }
        }
    }

    /**
     * Updates student table.
     */
    public void revalidatePanel(){
        ArrayList<Student> studentsArrayList = (ArrayList<Student>) studentService.getAll();
        String[][] studentsString = toStudentList(studentsArrayList);
        StudentsTableMouseListener studentsTableMouseListener = new StudentsTableMouseListener();
        remove(scrollPane);
        scrollPane = TableBuilder.buildTable(studentsString, new String[]{"Номер студента",
                "Имя", "Фамилия", "Отчество", "Пол", "Курс", "Группа"}, studentsTableMouseListener);
        add(scrollPane);
        repaint();
        revalidate();
    }

}
