package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name = "competency")
@PrimaryKeyJoinColumn(name="detail_id")
public class Competency extends Detail {
    private final static Logger logger = LogManager.getLogger(Competency.class);

    public Competency() {
        //no-arg constructor for Hibernate
    }

    /**
     * Creates a new keyword and sets it to active
     * @param competencyName
     */
    public Competency(String competencyName) {
        super(competencyName);
        this.abbreviation = null;
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        // Competencies don't have abbreviations, override to avoid accidentally setting
        this.abbreviation = null;
    }

    @Override
    public String toString() {
        return "Competency{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
