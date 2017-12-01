package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="courseDetails")
public class CourseDetails {
    private final static Logger logger = LogManager.getLogger(CourseDetails.class);

    private final UUID uuid = UUID.randomUUID();

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table-generator")
    @TableGenerator(name = "table-generator", pkColumnValue = "courseDetails_id")
    @Column(name = "courseDetails_id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    protected Set<Course> courses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    protected Set<Sponsor> sponsors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    protected Set<Translation> translations = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    protected Set<Audience> audiences = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    protected Set<Competency> competencies = new HashSet<>();

    protected CourseDetails() {
        //no-op arg constructor for Hibernate
    }

    public Integer getId() {
        return id;
    }

    public boolean addCourse(Course course) {
        return (courses.add(course) && course.addCourseDetails(this));
    }

    public boolean removeCourse(Course course) {
        return (course.removeCourseDetails(this) && courses.remove(this));
    }

    public boolean addSponsor(Sponsor sponsor) {
        return (sponsors.add(sponsor) && sponsor.addCourseDetails(this));
    }

    public boolean removeSponsor(Sponsor sponsor) {
        return (sponsor.removeCourseDetails(this) && sponsors.remove(sponsor));
    }

    public boolean addTranslation(Translation translation) {
        return (translations.add(translation) && translation.addCourseDetails(this));
    }

    public boolean removeTranslation(Translation translation) {
        return (translation.removeCourseDetails(this) &&translations.remove(translation));
    }

    public boolean addAudience(Audience audience) {
        return (audiences.add(audience) && audience.addCourseDetails(this));
    }

    public boolean removeAudience(Audience audience) {
        return (audience.removeCourseDetails(this) && audiences.remove(audience));
    }

    public boolean addCompetency(Competency competency) {
        return (competencies.add(competency) && competency.addCourseDetails(this));
    }

    public boolean removeCompetency(Competency competency) {
        return (competency.removeCourseDetails(this) && competencies.remove(competency));
    }

    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }

    public Set<Audience> getAudiences() {
        return audiences;
    }

    public void setAudiences(Set<Audience> audiences) {
        this.audiences = audiences;
    }

    public Set<Competency> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(Set<Competency> competencies) {
        this.competencies = competencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDetails that = (CourseDetails) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
