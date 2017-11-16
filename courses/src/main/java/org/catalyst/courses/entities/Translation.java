package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="translation")
@PrimaryKeyJoinColumn(name="detail_id")
public class Translation extends  Detail {
    private static Logger logger = LogManager.getLogger(Translation.class);

    public Translation() {
        // no-arg constructor for hibernate
    }

    /**
     * Creates a new translation and sets it to active
     * @param translationName
     */
    public Translation(String translationName, String abbreviation) {
        super(translationName);
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Translation that = (Translation) o;

        return abbreviation.equals(that.abbreviation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + abbreviation.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id='" + id + '\'' +
                ", courseId=" + courseId +
                ", abbreviation='" + abbreviation + '\'' +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
