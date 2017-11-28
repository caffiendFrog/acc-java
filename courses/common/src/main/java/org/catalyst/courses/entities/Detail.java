package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

/**
 * Entity has details about each type of item that is common across all of them.
 * - Entities are defaulted to being active.
 * - id and active are intentionally primitives because they cannot be null
 */
@MappedSuperclass
public abstract class Detail {
    private final static Logger logger = LogManager.getLogger(Detail.class);

    @Column(name = "isActive")
    private boolean active;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    protected Detail() {
        // no-op constructor for hibernate, cannot be private
    }

    /**
     * Creates a new <code>Detail</code> initialized with the given name.
     * <code>Detail</code> defaults to being active
     * @param name
     */
    public Detail(String name) {
        setName(name);
        this.active = true;
    }

    /**
     * Creates a new <code>Detail</code> initialized with the given name and note.
     * <code>Detail</code> defaults to being active
     * @param name
     * @param note
     */
    public Detail(String name, String note) {
        setName(name);
        this.note = note;
        this.active = true;
    }

    abstract public Integer getId();

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

    /**
     * A name is required
     * @param name
     */
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detail detail = (Detail) o;

        if (active != detail.active) return false;
        if (!name.equals(detail.name)) return false;
        return note != null ? note.equals(detail.note) : detail.note == null;
    }

    @Override
    public int hashCode() {
        int result = (active ? 1 : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
