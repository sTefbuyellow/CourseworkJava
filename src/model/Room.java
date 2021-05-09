package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Room {
    private int id;
    private int flore;
    private int bedsCount;
    private int students;

    public Room(int id, int flore, int bedsCount) {
        this.id = id;
        this.flore = flore;
        this.bedsCount = bedsCount;
    }

    @Override
    public String toString(){
        return id+","+flore+","+bedsCount;
    }

    public int getId() {
        return id;
    }

    public int getFlore() {
        return flore;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }
}
