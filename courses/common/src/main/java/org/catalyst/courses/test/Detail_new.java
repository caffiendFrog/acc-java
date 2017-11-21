package org.catalyst.courses.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name="detail")
public class Detail_new{
    private final static Logger logger = LogManager.getLogger(Detail_new.class);

    @Id @GeneratedValue
    @Column(name = "detail_id")
    protected int id;

    @Column(name = "isActive")
    protected boolean active;

    @Column(name = "name")
    protected String name;

    @Column(name= " abbreviation")
    protected String abbreviation;

    @Column(name = "note")
    protected String note;

    public Detail_new() {
        // no-arg constructor for Hibernate
    }

    public Detail_new(String name) {
        this.name = name;
        this.active = true;
    }

    public Detail_new(String name, String note){
        this.name = name;
        this.note = note;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Cannot set entity name to null.");
        }

        if (name.isEmpty()) {
            logger.warn("Trying to change entity name from ["+this.name+"] to ["+name+"]. This is likely to cause" +
                    " errors downstream.");
        }
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Returns a copy of this object that is safe to mutate without unintended side effects on the original object
     * @return
     */
    public Detail_new deepCopy() {
        Detail_new copy = new Detail_new(this.name);
        copy.id = this.id;
        copy.active = this.active;
        copy.abbreviation = this.abbreviation;
        copy.note = this.note;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detail_new detail = (Detail_new) o;

        if (id != detail.id) return false;
        if (active != detail.active) return false;
        if (!name.equals(detail.name)) return false;
        return note != null ? note.equals(detail.note) : detail.note == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
