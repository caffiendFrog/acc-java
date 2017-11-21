package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

/**
 * This entity is a join table to fully describe a course.
 */
@Entity
@Table(name = "course")
public class Course {
    private final static Logger logger = LogManager.getLogger(Course.class);

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    protected int courseId;

//    @ManyToOne
    @Column(name = "courseDetail_id")
    protected Integer courseDetailId;

    @Column(name = "translationDetail_id")
    protected Integer translationDetailId;

    @Column(name = "audienceDetail_id")
    protected Integer audienceDetailId;

    @Column(name = "sponsorDetail_v_id")
    protected Integer institutionDetailId;

    @Column(name = "competencyDetail_id")
    protected Integer competencyDetailId;

    public Course() {
        // no-arg constructor for Hibernate
    }

    public Course(int courseDetailId, int translationDetailId, int audienceDetailId, int institutionDetailId, int competencyDetailId) {
        this.courseDetailId = courseDetailId;
        this.translationDetailId = translationDetailId;
        this.audienceDetailId = audienceDetailId;
        this.institutionDetailId = institutionDetailId;
        this.competencyDetailId = competencyDetailId;
    }

    public Course(int courseDetailId) {
        this.courseDetailId = courseDetailId;
    }

    public Integer getCourseDetailId() {
        return courseDetailId;
    }

    public Integer getTranslationDetailId() {
        return translationDetailId;
    }

    public Integer getAudienceDetailId() {
        return audienceDetailId;
    }

    public Integer getInstitutionDetailId() {
        return institutionDetailId;
    }

    public Integer getCompetencyDetailId() {
        return competencyDetailId;
    }

    public Integer getId() { return courseId; }

    // We don't actually need to use the setters, but hibernate needs these
    public void setCourseDetailId(Integer courseDetailId) {
        this.courseDetailId = courseDetailId;
    }

    public void setTranslationDetailId(Integer translationDetailId) {
        this.translationDetailId = translationDetailId;
    }

    public void setAudienceDetailId(Integer audienceDetailId) {
        this.audienceDetailId = audienceDetailId;
    }

    public void setInstitutionDetailId(Integer institutionDetailId) {
        this.institutionDetailId = institutionDetailId;
    }

    public void setCompetencyDetailId(Integer competencyDetailId) {
        this.competencyDetailId = competencyDetailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (courseId != course.courseId) return false;
        if (courseDetailId != course.courseDetailId) return false;
        if (translationDetailId != course.translationDetailId) return false;
        if (audienceDetailId != course.audienceDetailId) return false;
        if (institutionDetailId != course.institutionDetailId) return false;
        return competencyDetailId == course.competencyDetailId;
    }

    @Override
    public int hashCode() {
        int result = courseId;
        result = 31 * result + courseDetailId;
        result = 31 * result + translationDetailId;
        result = 31 * result + audienceDetailId;
        result = 31 * result + institutionDetailId;
        result = 31 * result + competencyDetailId;
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseDetailId=" + courseDetailId +
                ", translationDetailId=" + translationDetailId +
                ", audienceDetailId=" + audienceDetailId +
                ", institutionDetailId=" + institutionDetailId +
                ", competencyDetailId=" + competencyDetailId +
                '}';
    }
}
