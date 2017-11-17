package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Extending translation because translation already has checks for requiring abbreviations. Just adding to that
 */
@Entity
@Table(name = "institutionDetail")
@PrimaryKeyJoinColumn(name="detail_id")
public class InstitutionDetail extends TranslationDetail {
    private final static Logger logger = LogManager.getLogger(InstitutionDetail.class);

    @Column(name = "sponsor")
    private boolean sponsor;

    public InstitutionDetail() {
        //no-arg constructor for hibernate
    }

    /**
     * Creates a new institution, sets it to active and assumes it is a sponsor
     * @param institutionName
     * @param abbreviation
     */
    public InstitutionDetail(String institutionName, String abbreviation) {
        super(institutionName, abbreviation);
        this.sponsor = true;
    }

    public InstitutionDetail(String institutionName, String abbreviation, boolean isSponsor) {
        super(institutionName, abbreviation);
        this.sponsor = isSponsor;
    }

    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) { this.sponsor = sponsor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InstitutionDetail that = (InstitutionDetail) o;

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
        return "InstitutionDetail{" +
                "id='" + id + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", sponsor=" + sponsor +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
