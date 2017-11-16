package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "institution")
@PrimaryKeyJoinColumn(name="detail_id")
public class Institution extends Detail {
    private final static Logger logger = LogManager.getLogger(Institution.class);

    @Column(name = "sponsor")
    private boolean sponsor;

    public Institution() {
        //no-arg constructor for hibernate
    }

    /**
     * Creates a new institution, sets it to active and assumes it is a sponsor
     * @param institutionName
     * @param abbreviation
     */
    public Institution(String institutionName, String abbreviation) {
        super(institutionName);
        this.abbreviation = abbreviation;
        this.sponsor = true;
    }

    public Institution(String institutionName, String abbreviation, boolean isSponsor) {
        super(institutionName);
        this.abbreviation = abbreviation;
        this.sponsor = isSponsor;
    }

    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Institution that = (Institution) o;

        if (sponsor != that.sponsor) return false;
        return abbreviation.equals(that.abbreviation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + abbreviation.hashCode();
        result = 31 * result + (sponsor ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "id='" + id + '\'' +
                ", courseId=" + courseId +
                ", abbreviation='" + abbreviation + '\'' +
                ", sponsor=" + sponsor +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
