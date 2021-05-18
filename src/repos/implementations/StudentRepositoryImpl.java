package repos.implementations;

import model.Sex;
import model.Student;
import repos.interfaces.StudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A <code>StudentRepositoryImpl</code> class implements
 * <code>StudentRepository</code> interface and describes
 * methods for interacting with the student table.
 *
 * @author Kirichuk K.N.
 * @version 0.01 04.03.2021
 */
public class StudentRepositoryImpl implements StudentRepository {

    Connection connection;

    private static final Logger logger = Logger.getLogger(RoomRepositoryImpl.class.getName());

    public StudentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return <code>Collection</code> of all students
     */
    @Override
    public ArrayList<Student> getAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student");
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            return students;
        } catch (SQLException e) {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE,"Error during all students select", e);
            return null;
        }
    }

    /**
     * @param id student id
     * @return <code>Student</code> class object
     */
    @Override
    public Student getById(int id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student where id = " + id);
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            if(students.size() == 0)
                return null;
            return students.get(0);
        } catch (SQLException e) {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE,"Error during all student by id select", e);
            return null;
        }
    }

    /**
     * @param id room id
     * @return <code>Collection</code> of students in selected room
     */
    @Override
    public Collection<Student> getAllByRoomId(int id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM studenthostel.student WHERE room_id = " + id);
            ArrayList<Student> students = getStudents(resultSet);
            resultSet.close();
            statement.close();
            return students;
        } catch (SQLException e) {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE,"Error during all students by room id select", e);
            return null;
        }
    }

    /**
     * @param id of student witch will be deleted
     * @return <code>true</code> if student deleted successful, <code>false</code> otherwise
     */
    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "DELETE FROM studenthostel.student WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            try {
                if(statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement close");
            }
            logger.log(Level.SEVERE,"Error during student delete", e);
            return false;
        }
    }

    /**
     * @param student <code>Student</code> class object
     * @param oldId id of student  witch will be updated
     * @return <code>true</code> if student updated successful, <code>false</code> otherwise
     */
    @Override
    public boolean update(Student student, int oldId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement((
                    "UPDATE studenthostel.student SET id = ?, first_name = ?," +
                            " second_name = ?, fathers_name = ?, sex = ?," +
                            " group_number = ?, course = ?, room_id = ? WHERE id = ?"));
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSecondName());
            statement.setString(4, student.getFathersName());
            statement.setString(5, student.getSex().toString());
            statement.setInt(6, student.getGroup());
            statement.setInt(7, student.getCourse());
            statement.setInt(8, student.getRoomId());
            statement.setInt(9, oldId);
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            try {
                if(statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE,"Error during student update", e);
            return false;
        }
    }

    /**
     * @param student <code>Student</code> class object
     * @return <code>true</code> if student created successful, <code>false</code> otherwise
     */
    @Override
    public boolean create(Student student) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement((
                    "INSERT INTO studenthostel.student (id, first_name, second_name," +
                            " fathers_name, sex, group_number, course, room_id)" +
                            " values (?, ?, ?, ?, ?, ?, ?, ?)"));
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSecondName());
            statement.setString(4, student.getFathersName());
            statement.setString(5, student.getSex().toString());
            statement.setInt(6, student.getGroup());
            statement.setInt(7, student.getCourse());
            statement.setInt(8, student.getRoomId());
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            try {
                if(statement != null)
                    statement.close();
            } catch (SQLException throwable) {
                logger.info("Exception during statement or resultSet close");
            }
            logger.log(Level.SEVERE,"Error during student create", e);
            return false;
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
                int group = resultSet.getInt("group_number");
                int course = resultSet.getInt("course");
                int roomId = resultSet.getInt("room_id");
                students.add(new Student(id, name, secondName,
                        fathersName, sex, group, course, roomId));
            }
            return students;
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Error during student object create", e);
            return new ArrayList<>();
        }
    }
}
