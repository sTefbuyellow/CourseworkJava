package repos.implementations;

import model.Room;
import model.Sex;
import model.Student;
import repos.interfaces.StudentRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class StudentRepositoryImpl implements StudentRepository {
    Connection connection;

    public StudentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Student> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student");
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Student getById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student where id = " + id);
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            if(students.size() == 0)
                return null;
            return students.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Student> getAllByRoomId(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student WHERE room_id = " + id);
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "DELETE * FROM studenthostel.student WHERE id = " + id);
            resultSet.close();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student update(Student student) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student WHERE room_id = ");
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            if(students.size() == 0)
                return null;
            return students.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Student> getStudents(ResultSet resultSet) {
        try {
            ArrayList<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                String fathersName = resultSet.getString("fathers_name");
                Sex sex = Sex.valueOf(resultSet.getString("sex"));
                long group = resultSet.getLong("group_number");
                int course = resultSet.getInt("course");
                int roomId = resultSet.getInt("room_id");
                students.add(new Student(id, name, secondName,
                        fathersName, sex, group, course, roomId));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
