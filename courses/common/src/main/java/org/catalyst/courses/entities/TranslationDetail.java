package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="translation")
public class TranslationDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(TranslationDetail.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "translation_id")
    private int id;

//    @OneToOne
//    @Cascade({CascadeType.SAVE_UPDATE})
//    @JoinColumn(name = "detail_id")
//    private Detail detail;

    @ManyToMany(mappedBy = "translations")
    protected Set<CourseDetail> courses = new HashSet<>();

    protected TranslationDetail() {
        // no-op constructor for hibernate, cannot be private
    }

    protected TranslationDetail(String name, String abbreviation) {
        setName(name);
        setAbbreviation(abbreviation);
    }

    public Integer getId() {
        return this.id;
    }
//
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

    public void addCourse(CourseDetail course) {
        courses.add(course);
    }

    public void removeCourse(CourseDetail course) {
        courses.remove(course);
    }
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

}
