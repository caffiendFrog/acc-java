package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="audienceDetail")
@PrimaryKeyJoinColumn(name="detail_id")
public class AudienceDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(AudienceDetail.class);

    public AudienceDetail() {
        // no-arg constructor for Hibernate
    }

    /**
     * Creates a new audience and sets it as active
     * @param audienceName
     */
    public AudienceDetail(String audienceName) {
        super(audienceName);
        this.abbreviation = null;
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        // Audiences don't have abbreviations, override to avoid accidentally setting
        this.abbreviation = null;
    }

    @Override
    public String toString() {
        return "AudienceDetail{" +
                "id=" + id +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
