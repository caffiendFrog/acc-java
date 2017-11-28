package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "audience")
public class AudienceDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(AudienceDetail.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "audience_id")
    private int id;

//    @OneToOne
//    @Cascade({CascadeType.SAVE_UPDATE})
//    @JoinColumn(name = "detail_id")
//    private Detail detail;

    @ManyToMany(mappedBy = "audiences")
    protected Set<CourseDetail> courses = new HashSet<>();

    protected AudienceDetail() {
        // no-op constructor for hibernate, cannot be private
    }

    public AudienceDetail(String name) {
        setName(name);
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
//
//    public void setAbbreviation(String abbreviation) {
//        detail.setAbbreviation(abbreviation);
//    }

    public void addCourse(CourseDetail course) {
        courses.add(course);
    }

    public void removeCourse(CourseDetail course) {
        courses.remove(course);
    }
}
