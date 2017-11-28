package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class CourseDetail extends Detail {
    private final static Logger logger = LogManager.getLogger(CourseDetail.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "course_id")
    private int id;

//    @OneToOne
//    @Cascade({CascadeType.SAVE_UPDATE})
//    @JoinColumn(name = "detail_id")
//    private Detail detail;

    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "institution_id")
    private InstitutionDetail institution;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<InstitutionDetail> sponsors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<TranslationDetail> translations = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<AudienceDetail> audiences = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private Set<CompetencyDetail> competencies = new HashSet<>();

    @Column(name = "archived")
    private boolean archived;

    @Column(name = "webcast")
    private boolean webcast;

    @Column(name = "contactEmail")
    private String contactEmail;

    @Column(name = "contactUrl")
    private String contactUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "hours")
    private String hours;

    @Column(name = "maxEnroll")
    private String maxEnroll;

    @Column(name = "searchBlob")
    private String searchBlob;

    @Temporal(TemporalType.DATE)
    @Column(name = "courseDate")
    private Date date;

    @Temporal(TemporalType.DATE)
    @Column(name = "sortDate")
    private Date sortDate;

    @Column(name = "dateInfo")
    private String dateInfo;

    protected CourseDetail() {
        // no-op constructor for hibernate, cannot be private
    }

    public CourseDetail(String courseName) {
        setName(courseName);
    }

    public Integer getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addSponsor(InstitutionDetail institutionDetail) {
        // check if instituiton is valid sponsor
        sponsors.add(institutionDetail);
        institutionDetail.addCourse(this);
    }

    public void removeSponsor(InstitutionDetail institutionDetail) {
        sponsors.remove(institutionDetail);
        institutionDetail.removeCourse(this);
    }

    public void addTranslation(TranslationDetail translationDetail) {
        translations.add(translationDetail);
        translationDetail.addCourse(this);
    }

    public void removeTranslation(TranslationDetail translationDetail) {
        translations.remove(translationDetail);
        translationDetail.removeCourse(this);
    }

    public void addAudience(AudienceDetail audienceDetail) {
        audiences.add(audienceDetail);
        audienceDetail.addCourse(this);
    }

    public void removeAudience(AudienceDetail audienceDetail) {
        audiences.remove(audienceDetail);
        audienceDetail.removeCourse(this);
    }

    public void addCompetency(CompetencyDetail competencyDetail) {
        competencies.add(competencyDetail);
        competencyDetail.addCourse(this);
    }

    public void removeCompetency(CompetencyDetail competencyDetail) {
        competencies.remove(competencyDetail);
        competencyDetail.removeCourse(this);
    }

    public Set<InstitutionDetail> getSponsors() {
        return sponsors;
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
//        throw new NullPointerException("Courses do not have an abbreviation");
//    }
//
//    public void setAbbreviation(String abbreviation) {
//        //TODO is this the best way to handle? SKC
//        throw new NullPointerException("Courses do not have an abbreviation");
//    }

//    @Override
//    public String toString() {
//        return "CourseDetail{" +
//                "id=" + id +
//                ", detail=" + detail +
//                ", description='" + description + '\'' +
//                ", sponsors=" + sponsors +
//                '}';
//    }


}
