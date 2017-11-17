package org.catalyst.courses.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

/**
 * This entity is a join table to fully describe a course.
 *
 * It is intentional that there are no setters for the various detailIds. If we need to change the relationship,
 * we should delete the out-of-date relationship and create a new relationship instead.
 */
@Entity
@Table(name = "course")
public class Course {
    private final static Logger logger = LogManager.getLogger(Course.class);

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    protected int courseId;

    @Column(name = "courseDetail_id")
    protected int courseDetailId;

    @Column(name = "translationDetail_id")
    protected int translationDetailId;

    @Column(name = "audienceDetail_id")
    protected int audienceDetailId;

    @Column(name = "institutionDetail_id")
    protected int institutionDetailId;

    @Column(name = "competencyDetail_id")
    protected int competencyDetailId;

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

    public int getCourseDetailId() {
        return courseDetailId;
    }

    public int getTranslationDetailId() {
        return translationDetailId;
    }

    public int getAudienceDetailId() {
        return audienceDetailId;
    }

    public int getInstitutionDetailId() {
        return institutionDetailId;
    }

    public int getCompetencyDetailId() {
        return competencyDetailId;
    }

    public int getId() { return courseId; }

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
