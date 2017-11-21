package org.catalyst.courses.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name="institutionDetail_new")
public class InstitutionDetail_new {
    private final static Logger logger = LogManager.getLogger(InstitutionDetail_new.class);

    @Id
    @GeneratedValue
    @Column(name="institutionalDetail_id")
    private int institutionalDetail_id;

    @Column(name="sponsor")
    private boolean sponsor;

    @OneToOne
    @JoinColumn(name = "detail_id")
    private Detail_new details;

    public int getInstitutionalDetail_id() {
        return institutionalDetail_id;
    }

    public void setInstitutionalDetail_id(int institutionalDetail_id) {
        this.institutionalDetail_id = institutionalDetail_id;
    }

    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor = sponsor;
    }

    public Detail_new getDetails() {
        return details;
    }

    public void setDetails(Detail_new details) {
        this.details = details;
    }
}
