package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="institution")
public class InstitutionDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(InstitutionDetail.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "institution_id")
    private int id;

//    @OneToOne
//    @Cascade({CascadeType.SAVE_UPDATE})
//    @JoinColumn(name = "detail_id")
//    private Detail detail;

    @Column(name = "sponsor")
    private boolean sponsor;

    @ManyToMany(mappedBy = "sponsors")
    protected Set<CourseDetail> courses = new HashSet<>();

    protected InstitutionDetail() {
        // no-op constructor for hibernate, cannot be private
    }

//    /**
//     * Convenience constructor for unit testing
//     * @param detail
//     */
//    protected InstitutionDetail(Detail detail) {
//        this.detail = detail;
//    }

    public InstitutionDetail(String name, String abbreviation) {
        setName(name);
        setAbbreviation(abbreviation);
    }

//    public void deactivate() {
//        detail.deactivate();
//    }
//
//
//    public void activate() {
//        detail.activate();
//    }
//
//
//    public boolean isActive() {
//        return detail.isActive();
//    }
//
//
//    public String getName() {
//        return detail.getName();
//    }
//
//
//    public void setName(String name) {
//        detail.setName(name);
//    }
//
//
//    public String getNote() {
//        return detail.getNote();
//    }
//
//
//    public void setNote(String note) {
//        detail.setNote(note);
//    }
//
//
//    public String getAbbreviation() {
//        return detail.getAbbreviation();
//    }

    /**
     * An abbreviation is required
     * @param abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        if (abbreviation == null) {
            throw new NullPointerException("Cannot set entity name to null.");
        }

        if (abbreviation.isEmpty()) {
            logger.warn("Trying to change entity name from ["+getAbbreviation()+"] to ["+abbreviation+"]. This is likely to cause" +
                    " errors downstream.");
        }
        super.setAbbreviation(abbreviation);
    }

    public boolean isSponsor() {
        return sponsor;
    }

    public void setSponsor(boolean sponsor) {
        this.sponsor = sponsor;
    }

    public void addCourse(CourseDetail course) {
        courses.add(course);
    }

    public void removeCourse(CourseDetail course) {
        courses.remove(course);
    }

    public Integer getId() {
        return this.id;
    }

//    @Override
//    public String toString() {
//        return "InstitutionDetail{" +
//                "id=" + id +
//                ", detail=" + detail +
//                ", sponsor=" + sponsor +
//                '}';
//    }
}
