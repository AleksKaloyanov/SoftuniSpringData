package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User {
    private float avgGrade;
    private short attendance;
    private Set<Course> courses;

    public Student() {
    }

    @Column(name = "average_grade")
    public float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(float avgGrade) {
        this.avgGrade = avgGrade;
    }

    @Column(name = "attendance")
    public short getAttendance() {
        return attendance;
    }

    public void setAttendance(short attendance) {
        this.attendance = attendance;
    }
}
