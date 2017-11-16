package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name="detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class Detail {
    private final static Logger logger = LogManager.getLogger(Detail.class);

    @Id @GeneratedValue
    @Column(name = "detail_id")
    protected int id;

    @Column(name = "courseId")
    protected int courseId;

    @Column(name = "isActive")
    protected boolean active;

    @Column(name = "name")
    protected String name;

    @Column(name= " abbreviation")
    protected String abbreviation;

    @Column(name = "note")
    protected String note;

    public Detail() {
        // no-arg constructor for Hibernate
    }

    public Detail(String name) {
        this.name = name;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activte() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detail detail = (Detail) o;

        if (id != detail.id) return false;
        if (courseId != detail.courseId) return false;
        if (active != detail.active) return false;
        if (!name.equals(detail.name)) return false;
        return note != null ? note.equals(detail.note) : detail.note == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
