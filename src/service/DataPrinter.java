package service;

import model.Room;
import model.Student;
import service.interfaces.RoomService;
import service.interfaces.StudentService;

import java.util.ArrayList;

/**
 * The <code>DataPrinter</code> transform data to writable format.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public class DataPrinter {

    private static StudentService studentService;
    private static RoomService roomService;


    /**
     * @param studentService A <code>StudentService</code> class object
     * @param roomService A <code>RoomService</code> class object
     */
    public static void setServices(StudentService studentService, RoomService roomService) {
        DataPrinter.studentService = studentService;
        DataPrinter.roomService = roomService;
    }

    /**
     * @return formatted rooms and students information
     */
    public static String[] getPrintedData() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<Room> rooms = (ArrayList<Room>) roomService.getAllRooms();
        for (Room room : rooms) {
            ArrayList<Student> students = (ArrayList<Student>) studentService.getAllByRoomId(room.getId());
            data.add(room + "\n");
            data.add("Список заселенных студентов\n");
            for (Student student : students)
                data.add(student + "\n");
            data.add("\n");
        }
        return data.toArray(new String[0]);
    }

    /**
     * @return formatted rooms information
     */
    public static String[] getPrintedRooms() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Список комнат общежития\n");
        ArrayList<Room> rooms = (ArrayList<Room>) roomService.getAllRooms();
        for (Room room : rooms) {
            data.add(room + "\n");
        }
        return data.toArray(new String[0]);
    }

    /**
     * @return formatted students information
     */
    public static String[] getPrintedStudents() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Список заселенных в общежитие студентов\n");
        ArrayList<Student> students = (ArrayList<Student>) studentService.getAll();
        for (Student student : students) {
            data.add(student + "\n");
        }
        return data.toArray(new String[0]);
    }
}
