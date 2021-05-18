package model;

/**
 * <code>Room</code> class model.
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
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
        return "Комната №" + id+", этаж "+flore+", количество мест: "+bedsCount;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setFlore(int flore) {
        this.flore = flore;
    }

    public void setBedsCount(int bedsCount) {
        this.bedsCount = bedsCount;
    }
}
