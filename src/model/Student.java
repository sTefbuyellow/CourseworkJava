package model;


/**
 * Class Student
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class Student {

    private int id;
    private String name;
    private String secondName;
    private String fathersName;
    private Sex sex;
    private long group;
    private int course;
    private int roomId;

    public Student(int id, String name, String secondName, String fathersName,
                   Sex sex, long group, int course, int roomId){
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.fathersName = fathersName;
        this.sex = sex;
        this.group = group;
        this.course = course;
        this.roomId = roomId;
    }

    @Override
    public String toString(){
        return id+","+name+","+secondName+","+fathersName+","+sex.toString()+","+group+","+course+","+roomId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public Sex getSex() {
        return sex;
    }

    public long getGroup() {
        return group;
    }

    public int getCourse() {
        return course;
    }

    public int getRoomId() {
        return roomId;
    }
}



