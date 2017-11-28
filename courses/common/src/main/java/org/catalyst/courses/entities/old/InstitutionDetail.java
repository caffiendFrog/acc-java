package org.catalyst.courses.entities.old;

/**
 * Extending translation because translation already has checks for requiring abbreviations. Just adding to that
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name="institutionDetail")
public class InstitutionDetail {
    private final static Logger logger = LogManager.getLogger(InstitutionDetail.class);

//    @AttributeOverride(name="institutionDetail_id", column=@Column(name="detail_id"))
    @Id
    @GeneratedValue
    @Column(name="institutionDetail_id")
    private int id;

    @OneToOne
    @JoinColumn(name="detail_id")
    private Detail detail;

    @Column(name="sponsor")
    private boolean sponsor;


    public InstitutionDetail(String name, String abbreviation) {
        detail = new Detail(name);
        setAbbreviation(abbreviation);
    }

    public InstitutionDetail(Detail detail) {
        this.detail = detail;
    }
    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor = sponsor;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public void setAbbreviation(String abbreviation) {
        if (abbreviation == null) {
            throw new NullPointerException("Cannot set entity name to null.");
        }

        if (abbreviation.isEmpty()) {
            logger.warn("Trying to change entity name from ["+detail.abbreviation+"] to ["+abbreviation+"]. This is likely to cause" +
                    " errors downstream.");
        }

        detail.setAbbreviation(abbreviation);
    }


    /**
     ** Convenience getters that pass through to the detail
     */
    public String getName() {
        return detail.getName();
    }

    public String getNote() {
        return detail.getNote();
    }

    public String getAbbreviation() {
        return detail.getAbbreviation();
    }



    //    protected void setName(String name) {
//        detail.setName(name);
//    }
//
//    protected void setNote(String note) {
//        detail.setNote(note);
//    }
//
//    protected void setAbbreviation(String abbreviation) {
//        detail.setAbbreviation(abbreviation);
//    }
}
//@Entity
//@Table(name = "institutionDetail")
////@PrimaryKeyJoinColumn(name="detail_id")
//public class InstitutionDetail extends TranslationDetail {
//    private final static Logger logger = LogManager.getLogger(InstitutionDetail.class);
//
//    public int getInstitutionDetail_id() {
//        return institutionDetail_id;
//    }
//
//    public void setInstitutionDetail_id(int institutionDetail_id) {
//        this.institutionDetail_id = institutionDetail_id;
//    }
//
//    @Id
//    @GeneratedValue
//    @Column(name="institutionDetail_id")
//    private int institutionDetail_id;
//
//    @Column(name = "sponsor")
//    private boolean sponsor;
//
//    public InstitutionDetail() {
//        //no-arg constructor for hibernate
//    }
//
//    /**
//     * Creates a new institution, sets it to active and assumes it is a sponsor
//     * @param institutionName
//     * @param abbreviation
//     */
//    public InstitutionDetail(String institutionName, String abbreviation) {
//        super(institutionName, abbreviation);
//        this.sponsor = false;
//    }
//
//    public InstitutionDetail(String institutionName, String abbreviation, String note) {
//        super(institutionName, abbreviation);
//        this.note = note;
//        this.sponsor = false;
//    }
//
//    public InstitutionDetail(String institutionName, String abbreviation, boolean isSponsor) {
//        super(institutionName, abbreviation);
//        this.sponsor = isSponsor;
//    }
//
//    public boolean isSponsor() {
//        return sponsor;
//    }
//
//    public void setSponsor(boolean sponsor) { this.sponsor = sponsor; }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        InstitutionDetail that = (InstitutionDetail) o;
//
//        if (sponsor != that.sponsor) return false;
//        return abbreviation.equals(that.abbreviation);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + abbreviation.hashCode();
//        result = 31 * result + (sponsor ? 1 : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "InstitutionDetail{" +
//                "id='" + id + '\'' +
//                ", abbreviation='" + abbreviation + '\'' +
//                ", sponsor=" + sponsor +
//                ", active=" + active +
//                ", name='" + name + '\'' +
//                ", note='" + note + '\'' +
//                '}';
//    }
//}
