package model;


/**
 * <code>Student</code> class model.
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
    private int group;
    private int course;
    private int roomId;

    public Student(int id, String name, String secondName, String fathersName,
                   Sex sex, int group, int course, int roomId) {
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
    public String toString() {
        return "Номер студента: " + id + ", ФИО: " + secondName + " " + name + " " + fathersName + "," +
                 "пол " + sexToString() + ", номер группы " + group + ", курс " + course + ", комната " + roomId;
    }

    public String sexToString(){
        if(sex.equals(Sex.M))
            return "Мужской";
        if(sex.equals(Sex.F))
            return "Женский";
        return "Не указан";
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

    public int getGroup() {
        return group;
    }

    public int getCourse() {
        return course;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setCourse(int course) {
        this.course = course;
    }
}



