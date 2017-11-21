package org.catalyst.courses.legacy.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="courses_institution")
public class LegacyInstitution implements LegacyDetailInterface {
    private final static Logger logger = LogManager.getLogger(LegacyInstitution.class);

    @Id
    @Column(name="id")
    private int id;

    @Column(name="abbreviation")
    private String abbreviation;

    @Column(name="name")
    private String name;

    @Column(name="note")
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LegacyInstitution that = (LegacyInstitution) o;

        if (id != that.id) return false;
        if (abbreviation != null ? !abbreviation.equals(that.abbreviation) : that.abbreviation != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return note != null ? note.equals(that.note) : that.note == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (abbreviation != null ? abbreviation.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LegacyInstitution{" +
                "id=" + id +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
