package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "competencyDetail")
@PrimaryKeyJoinColumn(name="detail_id")
public class CompetencyDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(CompetencyDetail.class);

    public CompetencyDetail() {
        //no-arg constructor for Hibernate
    }

    /**
     * Creates a new keyword and sets it to active
     * @param competencyName
     */
    public CompetencyDetail(String competencyName) {
        super(competencyName);
        this.abbreviation = null;
    }

    public CompetencyDetail(String competencyName, String note) {
        super(competencyName, note);
        this.abbreviation = null;
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        // Competencies don't have abbreviations, override to avoid accidentally setting
        this.abbreviation = null;
    }

    @Override
    public String toString() {
        return "CompetencyDetail{" +
                "id=" + id +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
